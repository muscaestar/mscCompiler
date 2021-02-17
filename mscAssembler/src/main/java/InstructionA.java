import java.io.IOException;
import java.util.HashMap;

/**
 * Created by muscaestar on 12/16/20
 *
 * @author muscaestar
 */
public class InstructionA extends HackInstruction {

    private static final String TYPE_CODE = "0";

    public static final HashMap<String, Integer> SYMBOL_MAP = new HashMap<>();

    private static final HashMap<String, Integer> VAR_MAP = new HashMap<>();
    private static int VAR_MAP_VALUE = 16;

    static {
        SYMBOL_MAP.put("R0", 0);
        SYMBOL_MAP.put("R1", 1);
        SYMBOL_MAP.put("R2", 2);
        SYMBOL_MAP.put("R3", 3);
        SYMBOL_MAP.put("R4", 4);
        SYMBOL_MAP.put("R5", 5);
        SYMBOL_MAP.put("R6", 6);
        SYMBOL_MAP.put("R7", 7);
        SYMBOL_MAP.put("R8", 8);
        SYMBOL_MAP.put("R9", 9);
        SYMBOL_MAP.put("R10", 10);
        SYMBOL_MAP.put("R11", 11);
        SYMBOL_MAP.put("R12", 12);
        SYMBOL_MAP.put("R13", 13);
        SYMBOL_MAP.put("R14", 14);
        SYMBOL_MAP.put("R15", 15);
        SYMBOL_MAP.put("SCREEN", 16384);
        SYMBOL_MAP.put("KBD", 24576);
        SYMBOL_MAP.put("SP", 0);
        SYMBOL_MAP.put("LCL", 1);
        SYMBOL_MAP.put("ARG", 2);
        SYMBOL_MAP.put("THIS", 3);
        SYMBOL_MAP.put("THAT", 4);
    }

    private int value;

    public InstructionA(String rawAsm) {
        super(TYPE_CODE, rawAsm);
    }

    @Override
    public String toMachineCode() {
        StringBuilder sb = new StringBuilder(TYPE_CODE);
        for (int i = 14; i >=0; i--) {
            int mask = 1 << i;
            sb.append((value & mask) != 0 ? 1 : 0);
        }
        return sb.toString();
    }

    @Override
    public boolean parse(HashMap<String, Integer> labelMap) {
        if (rawAsm.contains("//")) {
            super.rawAsm = rawAsm.substring(0, rawAsm.indexOf("//"))
                    .trim();
        }
        String rawValue = rawAsm.substring(1);
        if (SYMBOL_MAP.containsKey(rawValue)) {
            value = SYMBOL_MAP.get(rawValue);
        } else if (labelMap.containsKey(rawValue)) {
            value = labelMap.get(rawValue);
        } else {
            try {
                value = Integer.parseInt(rawValue);
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }

    public void parseVar() throws IOException {
        String rawValue = rawAsm.substring(1);
        if (!VAR_MAP.containsKey(rawValue)) {
            VAR_MAP.put(rawValue, VAR_MAP_VALUE++);
            if (VAR_MAP_VALUE >= SYMBOL_MAP.get("SCREEN")) {
                throw new IOException("Too many var. Memory overflow.");
            }
        }
        value = VAR_MAP.get(rawValue);
    }
}
