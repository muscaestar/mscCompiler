package syntax.parse.expression;

import syntax.parse.ParseElement;
import syntax.token.JackToken;
import syntax.token.Symbol;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class JackExpression implements JackTerm, ParseElement {
    private JackTerm term;
    private List<OpTerm> opTerms = new LinkedList<>();

    public void compileExpression(ListIterator<JackToken> iterator, String symbolOfEnd) {
        JackTerm currTerm = JackTerm.compileTerm(iterator, symbolOfEnd);
        this.term = currTerm;
        JackToken currToken = iterator.next();
        while (OpTerm.OP_SET.contains(currToken.getTkv())) {
            OpTerm opTerm = new OpTerm();
            opTerms.add(opTerm);
            opTerm.compileOpTerm(iterator, symbolOfEnd, (Symbol) currToken);
            currToken = iterator.next();
        }
    }

    public JackTerm getTerm() {
        return term;
    }

    public void setTerm(JackTerm term) {
        this.term = term;
    }

    public List<OpTerm> getOpTerms() {
        return opTerms;
    }

    public void setOpTerms(List<OpTerm> opTerms) {
        this.opTerms = opTerms;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<expression>").append("\n");
        sb.append("<term>").append("\n");
        sb.append(term.toXmlTerm()).append("\n");
        sb.append("</term>").append("\n");
        for (OpTerm opTerm : opTerms) {
            sb.append(opTerm.toXml()).append("\n");
        }
        sb.append("</expression>");
        return sb.toString();
    }

    @Override
    public String toXmlTerm() {
        StringBuilder sb = new StringBuilder();
        sb.append(Symbol.of("(").toXml()).append("\n");
        sb.append(toXml()).append("\n");
        sb.append(Symbol.of(")").toXml());
        return sb.toString();
    }
}
