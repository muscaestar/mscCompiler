package syntax.parse.statement;

import codegen.CodeGenUtil;
import syntax.parse.expression.SubroutineCall;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class DoStatement implements JackStatement {
    SubroutineCall subrCall;

    @Override
    public JackToken compileStatement(ListIterator<JackToken> iterator) {
        CodeGenUtil.genComment("do");
        subrCall = new SubroutineCall();
        subrCall.compileSubroutineCall(iterator);
        CodeGenUtil.genPop("temp", 0);
        iterator.next();
        return iterator.next();
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<doStatement>\n");
        sb.append(Keyword.of("do").toXml()).append("\n");
        sb.append(subrCall.toXmlTerm()).append("\n");
        sb.append(Symbol.of(";").toXml()).append("\n");
        sb.append("</doStatement>");
        return sb.toString();
    }
}
