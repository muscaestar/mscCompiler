package syntax.parse.expression;

import syntax.parse.ParseElement;
import syntax.token.JackToken;
import syntax.token.Symbol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class OpTerm implements ParseElement {
    public static final String[] OP_SYMBOL = new String[] {"+", "-", "*", "/", "&", "|", "<", ">", "="};
    public static final Set<String> OP_SET = new HashSet<>(Arrays.asList(OP_SYMBOL));

    private Symbol op;
    private JackTerm term;

    public Symbol getOp() {
        return op;
    }

    public void setOp(Symbol op) {
        this.op = op;
    }

    public JackTerm getTerm() {
        return term;
    }

    public void setTerm(JackTerm term) {
        this.term = term;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append(op.toXml()).append("\n");
        sb.append("<term>").append("\n");
        sb.append(term.toXmlTerm()).append("\n");
        sb.append("</term>");
        return sb.toString();
    }

    public void compileOpTerm(ListIterator<JackToken> iterator, String symbolOfEnd, Symbol symbol) {
        this.op = symbol;
        this.term = JackTerm.compileTerm(iterator, symbolOfEnd);
    }
}
