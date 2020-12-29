package syntax.parse.statement;

import syntax.parse.ParseElement;
import syntax.token.JackToken;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public interface JackStatement extends ParseElement {
    String[] STATE_KEYWORDS = new String[] {"let", "if", "while", "do", "return"};
    Set<String> STATE_KEYWORDS_SET = new HashSet<>(Arrays.asList(STATE_KEYWORDS));

    /**
     *
     * @param iterator
     * @return the next token after the statement
     */
    JackToken compileStatement(ListIterator<JackToken> iterator);
}
