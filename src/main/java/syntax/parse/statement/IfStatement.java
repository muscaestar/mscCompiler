package syntax.parse.statement;

import codegen.CodeGenUtil;
import syntax.parse.expression.JackExpression;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class IfStatement implements JackStatement {
    private JackExpression condition;
    private final List<JackStatement> trueStatements = new LinkedList<>();
    private final List<JackStatement> elseStatements = new LinkedList<>();

    @Override
    public JackToken compileStatement(ListIterator<JackToken> iterator) {
        int ifCnt = CodeGenUtil.getIfLabelCnt();
        CodeGenUtil.genComment("if " + ifCnt);
        CodeGenUtil.ifLabelPlusOne();
        iterator.next(); // (
        condition = new JackExpression();
        condition.compileExpression(iterator, ")");
        iterator.next(); // {
        CodeGenUtil.genExpr(condition);
        CodeGenUtil.genIfGoto("IF_TRUE" + ifCnt);
        CodeGenUtil.genGoto("IF_FALSE" + ifCnt);
        CodeGenUtil.genLabel("IF_TRUE" + ifCnt);

        JackToken currToken = iterator.next();
        while (JackStatement.STATE_KEYWORDS_SET.contains(currToken.getTkv())) {
            JackStatement s;
            switch (currToken.getTkv()) {
                case "let": s = new LetStatement();break;
                case "if": s = new IfStatement();break;
                case "while": s = new WhileStatement();break;
                case "do": s = new DoStatement();break;
                case "return": s = new ReturnStatement();break;
                default:
                    throw new IllegalStateException("Unexpected value: " + currToken.getTkv());
            }
            trueStatements.add(s);
            currToken = s.compileStatement(iterator);
        }


        currToken = iterator.next();
        if (currToken.getTkv().equals("else")) {
            CodeGenUtil.genGoto("IF_END" + ifCnt);
            CodeGenUtil.genLabel("IF_FALSE" + ifCnt);
            iterator.next(); // {
            currToken = iterator.next();
            while (JackStatement.STATE_KEYWORDS_SET.contains(currToken.getTkv())) {
                JackStatement s;
                switch (currToken.getTkv()) {
                    case "let": s = new LetStatement();break;
                    case "if": s = new IfStatement();break;
                    case "while": s = new WhileStatement();break;
                    case "do": s = new DoStatement();break;
                    case "return": s = new ReturnStatement();break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + currToken.getTkv());
                }
                elseStatements.add(s);
                currToken = s.compileStatement(iterator);
            }
            CodeGenUtil.genLabel("IF_END" + ifCnt);
            currToken = iterator.next();
        } else {
            CodeGenUtil.genLabel("IF_FALSE" + ifCnt);
        }

        return currToken;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ifStatement>\n");
        sb.append(Keyword.of("if").toXml()).append("\n");
        sb.append(Symbol.of("(").toXml()).append("\n");
        sb.append(condition.toXml()).append("\n");
        sb.append(Symbol.of(")").toXml()).append("\n");
        sb.append(Symbol.of("{").toXml()).append("\n");
        sb.append("<statements>").append("\n");
        for (JackStatement statement : trueStatements) {
            sb.append(statement.toXml()).append("\n");
        }
        sb.append("</statements>").append("\n");
        sb.append(Symbol.of("}").toXml()).append("\n");
        if (elseStatements.size() > 0) {
            sb.append(Keyword.of("else").toXml()).append("\n");
            sb.append(Symbol.of("{").toXml()).append("\n");
            sb.append("<statements>").append("\n");
            for (JackStatement statement : elseStatements) {
                sb.append(statement.toXml()).append("\n");
            }
            sb.append("</statements>").append("\n");
            sb.append(Symbol.of("}").toXml()).append("\n");
        }
        sb.append("</ifStatement>");
        return sb.toString();
    }
}
