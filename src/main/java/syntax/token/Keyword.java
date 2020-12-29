package syntax.token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by muscaestar on 12/26/20
 *
 * @author muscaestar
 */
public class Keyword extends JackToken {
    public static final String[] KEYWORDS = new String[]{"class", "constructor", "function",
            "method", "field", "static", "var", "int", "char", "boolean", "void", "true", "false",
            "null", "this", "let", "do", "if", "else", "while", "return"};
    public static final Set<String> KEY_SET = new HashSet<>(Arrays.asList(KEYWORDS));

    public static Keyword of(String tkv) {
        if (KEY_SET.contains(tkv)) {
            return new Keyword(tkv);
        } else {
            throw new IllegalArgumentException("Keyword invalid: " + tkv);
        }
    }

    protected Keyword(String tkv) {
        super(tkv);
    }

    @Override
    public String toXml() {
        return String.format("<keyword> %s </keyword>", super.tkv);
    }

    @Override
    public boolean isValid() {
        return KEY_SET.contains(super.tkv);
    }
}
