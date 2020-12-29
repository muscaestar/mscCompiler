package syntax.parse.expression;

import syntax.token.Keyword;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class KeywordConstant implements JackTerm {
    public static final String[] KEYWORD_CONSTS = new String[] {"true", "false", "null", "this"};
    public static final Set<String> KEYWORD_CONST_SET = new HashSet<>(Arrays.asList(KEYWORD_CONSTS));

    private Keyword keyword;

    public KeywordConstant(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toXmlTerm() {
        return keyword.toXml();
    }
}
