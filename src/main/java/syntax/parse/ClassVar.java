package syntax.parse;

import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.*;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class ClassVar implements ParseElement {
    public static final String[] CLASS_VAR_KEYWORD = new String[] {"static", "field"};
    public static final Set<String> CLASS_VAR_KEYWORD_SET = new HashSet<>(Arrays.asList(CLASS_VAR_KEYWORD));

    private Keyword attr;
    private final List<Identifier> varNames = new LinkedList<>();

    public ClassVar(Keyword token) {
        this.attr = token;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<classVarDec>\n");
        sb.append(attr.toXml()).append("\n");
        for (Identifier identifier : varNames) {
            sb.append(identifier.toXml()).append("\n");
            sb.append(Symbol.of(",").toXml()).append("\n");
        }
        if (varNames.size() > 0) {
            sb.delete(sb.lastIndexOf("<symbol>"), sb.lastIndexOf("\n") + 1);
        }

        sb.append(Symbol.of(";").toXml()).append("\n");
        sb.append("</classVarDec>");
        return sb.toString();
    }

    public Keyword getAttr() {
        return attr;
    }

    public void setAttr(Keyword attr) {
        this.attr = attr;
    }

    public List<Identifier> getVarNames() {
        return varNames;
    }

    public void compileAVarName(Iterator<JackToken> iterator) {
        JackType type = new JackType(iterator.next());
        Identifier identifier = (Identifier) iterator.next();
        identifier.setType(type);
        varNames.add(identifier);

        Symbol symbol = (Symbol) iterator.next();
        while (symbol.getTkv().equals(",")) {
            Identifier another = (Identifier) iterator.next();
            another.setType(type);
            varNames.add(another);
            symbol = (Symbol) iterator.next();
        }
    }
}
