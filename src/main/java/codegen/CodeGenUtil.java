package codegen;

import codegen.table.VarTable;
import syntax.parse.expression.*;
import syntax.token.Identifier;
import syntax.token.IntConstant;
import syntax.token.StringConstant;
import syntax.token.Symbol;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by muscaestar on 12/30/20
 *
 * @author muscaestar
 */
public class CodeGenUtil {
    private static CodeGenWriter codeGenWriter;

    public static void initCodeGen(FileOutputStream fos) {
        codeGenWriter = CodeGenWriter.getInstance(fos);
    }

    public static void genComment(String s) {
//        codeGenWriter.writer.printf("// %s\n", s);
    }

    public static void genComment(Identifier identifier) {
//        codeGenWriter.writer.printf("// %s\n", identifier.getTkv());
    }

    public static void genFunction(String subrName, int numOfLocal) {
        codeGenWriter.writer.printf("function %s.%s %d\n", codeGenWriter.className, subrName, numOfLocal);
    }

    public static void genFunction(Identifier subrName, int numOfLocal) {
        codeGenWriter.writer.printf("function %s.%s %d\n", codeGenWriter.className, subrName.getTkv(), numOfLocal);
    }

    public static void genConstructor(Identifier subrName, int numOfLocal) {
        codeGenWriter.writer.printf("function %s.%s %d\n", codeGenWriter.className, subrName.getTkv(), numOfLocal);
        genPush("constant", VarTable.getInstance().numOfField());
        genCall("Memory", "alloc", 1);
        genPop("pointer", 0);
    }

    public static void genMethod(Identifier subrName, int numOfLocal) {
        codeGenWriter.writer.printf("function %s.%s %d\n", codeGenWriter.className, subrName.getTkv(), numOfLocal);
        genPush("argument", 0);
        genPop("pointer", 0);
    }

    public static void genPush(String memory, int i) {
        codeGenWriter.writer.printf("push %s %d\n", memory, i);
    }

    public static void genPush(String memory, String i) {
        codeGenWriter.writer.printf("push %s %s\n", memory, i);
    }

    public static void genPush(Identifier identifier) {
        VarTable varTable = VarTable.getInstance();
        String name = identifier.getTkv();
        codeGenWriter.writer.printf("push %s %d\n", varTable.getFormattedKind(name), varTable.getIndex(name));
    }

    public static void genPop(String memory, int i) {
        codeGenWriter.writer.printf("pop %s %d\n", memory, i);
    }

    public static void genPop(Identifier identifier) {
        VarTable varTable = VarTable.getInstance();
        String name = identifier.getTkv();
        codeGenWriter.writer.printf("pop %s %d\n", varTable.getFormattedKind(name), varTable.getIndex(name));
    }

    public static void genCall(String funcName, int numOfParam) {
        codeGenWriter.writer.printf("call %s %d\n", funcName, numOfParam);
    }

    public static void genCall(String calleeParent, String callee, int numOfArgs) {
        VarTable varTable = VarTable.getInstance();
        if (calleeParent == null) {
            codeGenWriter.writer.printf("call %s.%s %d\n", codeGenWriter.className, callee, numOfArgs + 1);
        } else if (varTable.exist(calleeParent)) {
            codeGenWriter.writer.printf("call %s.%s %d\n", varTable.getType(calleeParent), callee, numOfArgs + 1);
        } else {
            codeGenWriter.writer.printf("call %s.%s %d\n", calleeParent, callee, numOfArgs);
        }
    }

    public static void genCall(Identifier calleeParent, Identifier callee, int numOfArgs) {
        genCall(calleeParent == null ? null : calleeParent.getTkv(), callee.getTkv(), numOfArgs);
    }

    public static void genReturn() {
        codeGenWriter.writer.println("return");
    }

    public static void recycleCodeGenWriter() {
        codeGenWriter.writer.close();
        codeGenWriter.writer = null;
        codeGenWriter.className = null;
        codeGenWriter.whileCnt = 0;
        codeGenWriter.ifCnt = 0;
    }

    public static void setWriterClassName(Identifier className) {
        codeGenWriter.className = className.getTkv();
        genComment(className);
    }

    public static void genExpr(JackExpression expr) {
        if (expr.getOpTerms().size() == 0) {
            JackTerm term = expr.getTerm();
            if (term instanceof IntConstant) {
                genPush("constant", ((IntConstant) term).getTkv());
            } else if (term instanceof StringConstant) {
                String str = ((StringConstant) term).getTkv();
                genPush("constant", str.length());
                genCall("String", "new", 1);
                for (char c : str.toCharArray()) {
                    genPush("constant", c);
                    genCall("String", "appendChar", 2);
                }
            } else if (term instanceof Identifier) {
                Identifier identifier = (Identifier) term;
                genPush(identifier);
                if (identifier.hasArrayExpr()) {
                    genExpr(identifier.getArrayExpr());
                    genOp("+");
                    genPop("pointer", 1);
                    genPush("that", 0);
                }
            } else if (term instanceof KeywordConstant) {
                genKeywordConst((KeywordConstant) term);
            } else if (term instanceof UnaryOpTerm) {
                UnaryOpTerm unaryOpTerm = (UnaryOpTerm) term;
                genExpr(unaryOpTerm.getTerm());
                genUnaryOp(unaryOpTerm.getUnaryOp());
            } else if (term instanceof SubroutineCall) {
                SubroutineCall subrCall = (SubroutineCall) term;
                Identifier calleeParent = subrCall.getCalleeParent();

                if (calleeParent == null ) {
                    CodeGenUtil.genPush("pointer", 0);
                } else if (VarTable.getInstance().exist(calleeParent.getTkv())) {
                    CodeGenUtil.genPush(calleeParent);
                }

                for (JackExpression e : subrCall.getArgs()) {
                    genExpr(e);
                }
                genCall(calleeParent, subrCall.getCallee(), subrCall.getArgs().size());
            } else if (term instanceof JackExpression) {
                processLongExpr((JackExpression) term);
            }
        } else {
            processLongExpr(expr);
        }
    }

    public static void genKeywordConst(KeywordConstant term) {
        String value = term.getKeyword().getTkv();
        if (value.equals("false") || value.equals("null")) {
            genPush("constant", 0);
        } else if (value.equals("true")) {
            genPush("constant", 1);
            genUnaryOp("-");
        } else { // if (value.equals("this")) {
            genPush("pointer", 0);
        }
    }

    private static void processLongExpr(JackExpression expr) {
        genExpr(expr.getTerm());
        for (OpTerm opTerm : expr.getOpTerms()) {
            genExpr(opTerm.getTerm());
            genOp(opTerm.getOp());
        }
    }

    public static void genExpr(JackTerm term) {
        genExpr(new JackExpression(term));
    }

    public static void genUnaryOp(Symbol unaryOp) {
        genUnaryOp(unaryOp.getTkv());
    }

    public static void genUnaryOp(String s) {
        String res;
        switch (s) {
            case "~": res = "not";break;
            case "-": res = "neg";break;
            default: throw new IllegalArgumentException("Op invalid: " + s);
        }
        codeGenWriter.writer.println(res);

    }

    public static void genOp(String op) {
        String res;
        switch (op) {
            case "+": res = "add"; break;
            case "-": res = "sub"; break;
            case "*": res = "call Math.multiply 2"; break;
            case "/": res = "call Math.divide 2"; break;
            case "&": res = "and"; break;
            case "|": res = "or"; break;
            case "<": res = "lt"; break;
            case ">": res = "gt"; break;
            case "=": res = "eq"; break;
            default: throw new IllegalArgumentException("Op invalid: " + op);
        }
        codeGenWriter.writer.println(res);
    }

    public static void genOp(Symbol op) {
        genOp(op.getTkv());
    }

    public static void genIfGoto(String label) {
        genBranch("if-goto", label);
    }

    public static void genGoto(String label) {
        genBranch("goto", label);
    }

    public static void genLabel(String label) {
        genBranch("label", label);
    }

    public static void genBranch(String cmd, String label) {
        codeGenWriter.writer.printf("%s %s\n", cmd, label);
    }

    public static int getIfLabelCnt() {
        return codeGenWriter.ifCnt;
    }

    public static int getWhileLabelCnt() {
        return codeGenWriter.whileCnt;
    }

    public static void ifLabelPlusOne() {
        codeGenWriter.ifCnt++;
    }

    public static void whileLabelPlusOne() {
        codeGenWriter.whileCnt++;
    }

    public static void resetLabelCnts () {
        codeGenWriter.ifCnt = 0;
        codeGenWriter.whileCnt = 0;
    }

    private static class CodeGenWriter {
        private static CodeGenWriter instance;

        public static CodeGenWriter getInstance() {
            if (instance == null) {
                throw new IllegalStateException("CodeGenWriter is not init. Pls pass in a fos first");
            }
            return instance;
        }

        public static CodeGenWriter getInstance(FileOutputStream fos) {
            if (instance == null) {
                instance = new CodeGenWriter(fos);
            } else {
//                instance.writer.close();
                instance.className = null;
                instance.whileCnt = 0;
                instance.ifCnt = 0;
                instance.writer = new PrintWriter(fos);
            }
            return instance;
        }

        private String className;
        private int whileCnt;
        private int ifCnt;
        private PrintWriter writer;

        private CodeGenWriter(FileOutputStream fos) {
            this.writer = new PrintWriter(fos);
        }

    }
}
