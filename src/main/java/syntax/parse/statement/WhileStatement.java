package syntax.parse.statement;

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
public class WhileStatement implements JackStatement {
    private JackExpression condition;
    private final List<JackStatement> statements = new LinkedList<>();

    @Override
    public JackToken compileStatement(ListIterator<JackToken> iterator) {
        iterator.next(); // (
        condition = new JackExpression();
        condition.compileExpression(iterator, ")");
        iterator.next(); // {
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
            statements.add(s);
            currToken = s.compileStatement(iterator);
        }
        return iterator.next();
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<whileStatement>\n");
        sb.append(Keyword.of("while").toXml()).append("\n");
        sb.append(Symbol.of("(").toXml()).append("\n");
        sb.append(condition.toXml()).append("\n");
        sb.append(Symbol.of(")").toXml()).append("\n");
        sb.append(Symbol.of("{").toXml()).append("\n");
        sb.append("<statements>").append("\n");
        for (JackStatement statement : statements) {
            sb.append(statement.toXml()).append("\n");
        }
        sb.append("</statements>").append("\n");
        sb.append(Symbol.of("}").toXml()).append("\n");
        sb.append("</whileStatement>");
        return sb.toString();
    }
}
