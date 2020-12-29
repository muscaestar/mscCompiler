package syntax.parse.expression;

import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Symbol;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class SubroutineCall implements JackTerm {
    private Identifier callee;
    private final List<JackExpression> args = new LinkedList<>();
    private Identifier calleeParent;

    public void compileSubroutineCall(ListIterator<JackToken> iterator) {
        Identifier name = (Identifier) iterator.next();
        Symbol symbol = (Symbol) iterator.next();
        compileSubroutineCall(iterator, name, symbol);
    }

    public void compileSubroutineCall(ListIterator<JackToken> iterator, Identifier anIdentifier, Symbol nextSymbol) {
        if (nextSymbol.getTkv().equals("(")) {
            callee = anIdentifier;
        } else { // .
            calleeParent = anIdentifier;
            callee = (Identifier) iterator.next();
            iterator.next(); // (
        }
        JackToken currToken = iterator.next();
        while (!currToken.getTkv().equals(")")) {
            JackExpression anExpr = new JackExpression();
            if (!currToken.getTkv().equals(",")) {
                currToken = iterator.previous();
            }
            anExpr.compileExpression(iterator, ")");
            args.add(anExpr);
            iterator.previous();
            currToken = iterator.next();
        }
    }

    @Override
    public String toXmlTerm() {
        StringBuilder sb = new StringBuilder();
        if (Optional.ofNullable(calleeParent).isPresent()) {
            sb.append(calleeParent.toXml()).append("\n");
            sb.append(Symbol.of(".").toXml()).append("\n");
        }
        sb.append(callee.toXml()).append("\n");
        sb.append(Symbol.of("(").toXml()).append("\n");
        sb.append("<expressionList>\n");
        for (JackExpression arg : args) {
            sb.append(arg.toXml()).append("\n");
            sb.append(Symbol.of(",").toXml()).append("\n");
        }
        if (args.size() > 0) {
            sb.delete(sb.lastIndexOf("<symbol>"), sb.lastIndexOf("\n") + 1);
        }
        sb.append("</expressionList>\n");
        sb.append(Symbol.of(")").toXml());
        return sb.toString();
    }

}
