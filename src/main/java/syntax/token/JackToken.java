package syntax.token;

/**
 * Created by muscaestar on 12/26/20
 *
 * @author muscaestar
 */
public abstract class JackToken {
    protected String tkv;

    protected JackToken(String tkv) {
        this.tkv = tkv;
    }

    public abstract String toXml();
    public abstract boolean isValid();

    public String getTkv() {
        return tkv;
    }
}
