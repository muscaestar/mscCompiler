package syntax.token;

import syntax.parse.expression.JackTerm;

/**
 * Created by muscaestar on 12/27/20
 *
 * @author muscaestar
 */
public class StringConstant extends JackToken implements JackTerm {
    public static StringConstant of(String tkv) {
        if (tkv.contains("\n") || tkv.contains("\"")) {
            throw new IllegalArgumentException("StringConstant invalid: " + tkv);
        }
        return new StringConstant(tkv);
    }

    public static boolean isCandidate(String s) {
        s = s.trim();
        int len = s.length();
        if (s.charAt(0) == '"' && s.charAt(len - 1) == '"') {
            s = s.substring(1, len - 1);
            return !s.contains("\"");
        }
        return false;
    }

    public static String extractConst(String s) {
        return s.trim().substring(1, s.length() - 1);
    }

    protected StringConstant(String tkv) {
        super(tkv);
    }

    @Override
    public String toXml() {
        tkv = tkv.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;");
        return String.format("<stringConstant> %s </stringConstant>", tkv);
    }

    @Override
    public boolean isValid() {
        return !(tkv.contains("\n") || tkv.contains("\""));
    }

    @Override
    public String toXmlTerm() {
        return toXml();
    }
}
