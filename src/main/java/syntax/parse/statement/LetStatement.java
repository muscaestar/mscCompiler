package syntax.parse.statement;

import codegen.CodeGenUtil;
import syntax.parse.expression.JackExpression;
import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class LetStatement implements JackStatement {
    private Identifier varName;
    private JackExpression assignExpr;

    @Override
    public JackToken compileStatement(ListIterator<JackToken> iterator) {
        boolean handleArray = false;
        CodeGenUtil.genComment("let");
        varName = (Identifier) iterator.next();
        Symbol symbol = (Symbol) iterator.next();
        if (symbol.getTkv().equals("[")) {
            handleArray = true;
            JackExpression expression = new JackExpression();
            varName.setArrayExpr(expression);
            expression.compileExpression(iterator, "]");
            symbol = (Symbol) iterator.next();
            CodeGenUtil.genPush(varName);
            CodeGenUtil.genExpr(expression);
            CodeGenUtil.genOp("+");
        }
        // symbol: =
        assignExpr = new JackExpression();
        assignExpr.compileExpression(iterator, ";");
        CodeGenUtil.genExpr(assignExpr);
        if (handleArray) {
            CodeGenUtil.genPop("temp", 0);
            CodeGenUtil.genPop("pointer", 1);
            CodeGenUtil.genPush("temp", 0);
            CodeGenUtil.genPop("that", 0);
        } else {
            CodeGenUtil.genPop(varName);
        }
        return iterator.next();
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<letStatement>\n");
        sb.append(Keyword.of("let").toXml()).append("\n");
        sb.append(varName.toXml()).append("\n");
        sb.append(Symbol.of("=").toXml()).append("\n");
        sb.append(assignExpr.toXml()).append("\n");
        sb.append(Symbol.of(";").toXml()).append("\n");
        sb.append("</letStatement>");
        return sb.toString();
    }
}
