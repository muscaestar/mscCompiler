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
public class IfStatement implements JackStatement {
    private JackExpression condition;
    private final List<JackStatement> trueStatements = new LinkedList<>();
    private final List<JackStatement> elseStatements = new LinkedList<>();

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
            trueStatements.add(s);
            currToken = s.compileStatement(iterator);
        }

        currToken = iterator.next();
        if (currToken.getTkv().equals("else")) {
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
            currToken = iterator.next();
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
