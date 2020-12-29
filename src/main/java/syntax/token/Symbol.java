package syntax.token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by muscaestar on 12/26/20
 *
 * @author muscaestar
 */
public class Symbol extends JackToken {
    public static final String[] SYMBOLS = new String[]{"{", "}", "(", ")", "[", "]", ".",
            ",", ";", "+", "-", "*", "/", "&",
            "|", "<", ">", "=", "~"};
    public static final Set<String> SYM_SET = new HashSet<>(Arrays.asList(SYMBOLS));

    public static boolean isASymbol(char c) {
        return SYM_SET.contains(String.valueOf(c));
    }

    public static Symbol of (char tkvChar) {
        return of(String.valueOf(tkvChar));
    }
    public static Symbol of(String tkv) {
        if (SYM_SET.contains(tkv)) {
            return new Symbol(tkv);
        } else {
            throw new IllegalArgumentException("Symbol invalid: " + tkv);
        }
    }

    protected Symbol(String tkv) {
        super(tkv);
    }

    @Override
    public String toXml() {
        String xmlChar;
        switch (tkv) {
            case "<":
                xmlChar = "&lt;";break;
            case ">":
                xmlChar = "&gt;";break;
            case "&":
                xmlChar = "&amp;";break;
            default:
                xmlChar = tkv;break;
        }
        return String.format("<symbol> %s </symbol>", xmlChar);
    }

    @Override
    public boolean isValid() {
        return SYM_SET.contains(super.tkv);
    }
}
