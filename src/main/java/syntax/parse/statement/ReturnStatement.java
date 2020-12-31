package syntax.parse.statement;

import codegen.CodeGenUtil;
import syntax.parse.expression.JackExpression;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.ListIterator;
import java.util.Optional;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class ReturnStatement implements JackStatement {
    private JackExpression expression;

    @Override
    public JackToken compileStatement(ListIterator<JackToken> iterator) {
        JackToken currToken = iterator.next();
        if (!currToken.getTkv().equals(";")) {
            expression = new JackExpression();
            iterator.previous();
            expression.compileExpression(iterator, ";");
        }
        CodeGenUtil.genComment("return");
        if (expression == null) {
            CodeGenUtil.genPush("constant", 0);
        } else {
            CodeGenUtil.genExpr(expression);
        }
        CodeGenUtil.genReturn();
        return iterator.next();
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<returnStatement>\n");
        sb.append(Keyword.of("return").toXml()).append("\n");
        if (Optional.ofNullable(expression).isPresent()) {
            sb.append(expression.toXml()).append("\n");
        }
        sb.append(Symbol.of(";").toXml()).append("\n");
        sb.append("</returnStatement>");
        return sb.toString();
    }
}
