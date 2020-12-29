package syntax.parse.statement;

import syntax.parse.expression.JackExpression;
import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.Iterator;
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
        varName = (Identifier) iterator.next();
        Symbol symbol = (Symbol) iterator.next();
        if (symbol.getTkv().equals("[")) {
            JackExpression expression = new JackExpression();
            varName.setArrayExpr(expression);
            expression.compileExpression(iterator, "]");
            symbol = (Symbol) iterator.next();
        }
        // symbol: =
        assignExpr = new JackExpression();
        assignExpr.compileExpression(iterator, ";");
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
