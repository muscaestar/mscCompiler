package syntax.parse;

import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class JackType implements ParseElement {
    public static final String[] PRIMITYPES = new String[]{"int", "char", "boolean", "void"};
    public static final Set<String> PRIMI_SET = new HashSet<>(Arrays.asList(PRIMITYPES));
    boolean isObj;
    Identifier objName;
    Keyword primiName;

    public JackType(JackToken token) {
        if (token instanceof Keyword) {
            isObj = false;
            primiName = (Keyword) token;
        } else { // identifier
            isObj = true;
            objName = (Identifier) token;
        }
    }

    public String getName() {
        return isObj ? objName.getTkv() : primiName.getTkv();
    }

    @Override
    public String toXml() {
        return isObj ? String.format("<identifier> %s </identifier>", getName())
                : String.format("<keyword> %s </keyword>", getName());
    }
}
