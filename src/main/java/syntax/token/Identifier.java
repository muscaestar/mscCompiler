package syntax.token;

import syntax.parse.JackType;
import syntax.parse.expression.JackExpression;
import syntax.parse.expression.JackTerm;

import java.util.Optional;

/**
 * Created by muscaestar on 12/27/20
 *
 * @author muscaestar
 */
public class Identifier extends JackToken implements JackTerm {
    private JackType type;
    private JackExpression arrayExpr;

    public static Identifier of(String tkv) {
        if (isDigitChar(tkv.charAt(0))) {
            throw new IllegalArgumentException("Identifier cannot start with digit: " + tkv);
        }
        for (char c : tkv.toCharArray()) {
            if (!isLetterChar(c) && !isDigitChar(c) && c != '_') {
                throw new IllegalArgumentException("Identifier invalid: " + tkv);
            }
        }
        return new Identifier(tkv);
    }

    private static boolean isLetterChar(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    public static boolean isDigitChar(char c) {
        return (c >= 48 && c <= 57);
    }

    protected Identifier(String tkv) {
        super(tkv);
    }

    public JackType getType() {
        return type;
    }

    public void setType(JackType type) {
        this.type = type;
    }

    public JackExpression getArrayExpr() {
        return arrayExpr;
    }

    public void setArrayExpr(JackExpression arrayExpr) {
        this.arrayExpr = arrayExpr;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        if (Optional.ofNullable(type).isPresent()) {
            sb.append(type.toXml()).append("\n");
        }
        sb.append("<identifier> ").append(tkv).append(" </identifier>");
        if (Optional.ofNullable(arrayExpr).isPresent()) {
            sb.append("\n").append(Symbol.of("[").toXml()).append("\n");
            sb.append(arrayExpr.toXml()).append("\n")
                    .append(Symbol.of("]").toXml());
        }
        return sb.toString();
    }

    @Override
    public boolean isValid() {
        if (isDigitChar(tkv.charAt(0))) return false;
        for (char c : tkv.toCharArray()) {
            if (!isLetterChar(c) && !isDigitChar(c) && c != '_')
                return false;
        }
        return true;
    }

    @Override
    public String toXmlTerm() {
        return toXml();
    }

    public boolean hasArrayExpr() {
        return arrayExpr != null;
    }
}
