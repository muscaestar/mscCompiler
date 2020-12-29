package syntax.token;

import syntax.parse.expression.JackTerm;

/**
 * Created by muscaestar on 12/27/20
 *
 * @author muscaestar
 */
public class IntConstant extends JackToken implements JackTerm {

    public static IntConstant of(String tkv) {
        int intTkv = Integer.parseInt(tkv);
        if (intTkv >= 0 && intTkv <= 32767) {
            return new IntConstant(tkv);
        } else {
            throw new IllegalArgumentException("IntConstant invalid: " + tkv);
        }
    }

    protected IntConstant(String tkv) {
        super(tkv);
    }

    @Override
    public String toXml() {
        return String.format("<integerConstant> %s </integerConstant>", tkv);
    }

    @Override
    public boolean isValid() {
        int intTkv = Integer.parseInt(tkv);
        return (intTkv >= 0 && intTkv <= 32767);
    }

    @Override
    public String toXmlTerm() {
        return toXml();
    }
}
