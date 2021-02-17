import java.io.IOException;
import java.util.HashMap;

/**
 * Created by muscaestar on 12/16/20
 *
 * @author muscaestar
 */
public abstract class HackInstruction {
    protected String typeCode;
    protected String rawAsm;

    public HackInstruction(String typeCode, String rawAsm) {
        this.typeCode = typeCode;
        this.rawAsm = rawAsm;
    }

    public abstract String toMachineCode() throws IOException;

    public abstract boolean parse(HashMap<String, Integer> labelMap) throws IOException;
}
