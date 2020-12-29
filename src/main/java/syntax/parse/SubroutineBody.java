package syntax.parse;

import syntax.parse.statement.*;
import syntax.token.Identifier;
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
public class SubroutineBody implements ParseElement {
    private final List<Identifier> localVars = new LinkedList<>();

    private final List<JackStatement> statements = new LinkedList<>();

    public void compileSubrBody(ListIterator<JackToken> iterator) {
        iterator.next(); // {
        JackToken token = iterator.next(); // var
        while (token.getTkv().equals("var")) {
            JackType type = new JackType(iterator.next());
            Identifier identifier = (Identifier) iterator.next();
            identifier.setType(type);
            localVars.add(identifier);

            token = iterator.next(); // , or ;
            while (token.getTkv().equals(",")) {
                Identifier another = (Identifier) iterator.next();
                another.setType(type);
                localVars.add(another);
                token = iterator.next();
            }
            token = iterator.next();
        }

        compileStatements(iterator, token);
    }

    private void compileStatements(ListIterator<JackToken> iterator, JackToken currToken) {

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
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<subroutineBody>\n");
        sb.append(Symbol.of("{").toXml()).append("\n");
        for (Identifier var : localVars) {
            sb.append("<varDec>").append("\n");
            sb.append(Keyword.of("var").toXml()).append("\n");
            sb.append(var.toXml()).append("\n");
            sb.append(Symbol.of(";").toXml()).append("\n");
            sb.append("</varDec>").append("\n");
        }
        sb.append("<statements>").append("\n");
        for (JackStatement statement : statements) {
            sb.append(statement.toXml()).append("\n");
        }
        sb.append("</statements>").append("\n");
        sb.append(Symbol.of("}").toXml()).append("\n");
        sb.append("</subroutineBody>");
        return sb.toString();
    }
}
