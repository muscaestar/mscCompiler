package syntax.parse.expression;

import syntax.token.Symbol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class UnaryOpTerm implements JackTerm {
    public static final String[] UNARY_OPS = new String[] {"~", "-"};
    public static final Set<String> UNARY_OP_SET = new HashSet<>(Arrays.asList(UNARY_OPS));

    private final Symbol unaryOp;
    private final JackTerm term;

    public UnaryOpTerm(Symbol unaryOp, JackTerm currTerm) {
        this.unaryOp = unaryOp;
        this.term = currTerm;
    }

    @Override
    public String toXmlTerm() {
        StringBuilder sb = new StringBuilder();
        sb.append(unaryOp.toXml()).append("\n");
        sb.append("<term>").append("\n");
        sb.append(term.toXmlTerm()).append("\n");
        sb.append("</term>");
        return sb.toString();
    }
}
