import java.io.IOException;
import java.util.HashMap;

/**
 * Created by muscaestar on 12/16/20
 *
 * @author muscaestar
 */
public class InstructionC extends HackInstruction {

    private static final String EMPTY_PART = "null";

    private static final String TYPE_CODE = "1";

    private static final String UNUSE_BIT = "11";

    private static final HashMap<String, String> COMP_MAP = new HashMap<>();
    private static final HashMap<String, String> DEST_MAP = new HashMap<>();
    private static final HashMap<String, String> JUMP_MAP = new HashMap<>();

    static {
        COMP_MAP.put("0", "101010");
        COMP_MAP.put("1", "111111");
        COMP_MAP.put("-1", "111010");
        COMP_MAP.put("D", "001100");
        COMP_MAP.put("E", "110000");
        COMP_MAP.put("!D", "001101");
        COMP_MAP.put("!E", "110001");
        COMP_MAP.put("-D", "001111");
        COMP_MAP.put("-E", "110011");
        COMP_MAP.put("D+1", "011111");
        COMP_MAP.put("1+D", "011111");
        COMP_MAP.put("E+1", "110111");
        COMP_MAP.put("1+E", "110111");
        COMP_MAP.put("D-1", "001110");
        COMP_MAP.put("E-1", "110010");
        COMP_MAP.put("D+E", "000010");
        COMP_MAP.put("E+D", "000010");
        COMP_MAP.put("D-E", "010011");
        COMP_MAP.put("E-D", "000111");
        COMP_MAP.put("D&E", "000000");
        COMP_MAP.put("E&D", "000000");
        COMP_MAP.put("D|E", "010101");
        COMP_MAP.put("E|D", "010101");

        DEST_MAP.put("null", "000");
        DEST_MAP.put("M", "001");
        DEST_MAP.put("D", "010");
        DEST_MAP.put("MD", "011");
        DEST_MAP.put("A", "100");
        DEST_MAP.put("AM", "101");
        DEST_MAP.put("AD", "110");
        DEST_MAP.put("AMD", "111");

        JUMP_MAP.put("null", "000");
        JUMP_MAP.put("JGT", "001");
        JUMP_MAP.put("JEQ", "010");
        JUMP_MAP.put("JGE", "011");
        JUMP_MAP.put("JLT", "100");
        JUMP_MAP.put("JNE", "101");
        JUMP_MAP.put("JLE", "110");
        JUMP_MAP.put("JMP", "111");
    }

    private boolean isMForComp;
    private String dest;
    private String comp;
    private String jump;

    public InstructionC(String rawAsm) {
        super(TYPE_CODE, rawAsm);
    }

    @Override
    public String toMachineCode() throws IOException {
        StringBuilder sb = new StringBuilder(TYPE_CODE + UNUSE_BIT);
        sb.append(isMForComp ? 1 : 0);
        processCompPart(sb);
        processDestPart(sb);
        processJumpPart(sb);

        return sb.toString();
    }

    private void processCompPart(StringBuilder sb) throws IOException {
        comp = comp.replace(" ", "")
                .replace(isMForComp ? "M" : "A", "E");
        if (!COMP_MAP.containsKey(comp)) {
            throw new IOException("comp part syntax error");
        }
        sb.append(COMP_MAP.get(comp));
    }

    private void processDestPart(StringBuilder sb) throws IOException {
        dest = dest.replace(" ", "");
        if (!DEST_MAP.containsKey(dest)) {
            throw new IOException("dest part syntax error");
        }
        sb.append(DEST_MAP.get(dest));
    }

    private void processJumpPart(StringBuilder sb) throws IOException {
        jump = jump.replace(" ", "");
        if (!JUMP_MAP.containsKey(jump)) {
            throw new IOException("jump part syntax error");
        }
        sb.append(JUMP_MAP.get(jump));
    }

    @Override
    public boolean parse(HashMap<String, Integer> labelMap) throws IOException {
        if (rawAsm.contains("//")) {
            super.rawAsm = rawAsm.substring(0, rawAsm.indexOf("//"))
                    .trim();
        }
        int idxEq = rawAsm.indexOf("=");
        int idxSemicolon = rawAsm.indexOf(";");
        if (idxEq == -1 && idxSemicolon == -1) {
            dest = EMPTY_PART;
            comp = rawAsm;
            jump = EMPTY_PART;
        } else if (idxEq == -1) {
            dest = EMPTY_PART;
            comp = rawAsm.substring(0, idxSemicolon).trim();
            jump = rawAsm.substring(idxSemicolon+1).trim();
        } else if (idxSemicolon == -1) {
            dest = rawAsm.substring(0, idxEq).trim();
            comp = rawAsm.substring(idxEq+1).trim();
            jump = EMPTY_PART;
        } else {
            dest = rawAsm.substring(0, idxEq).trim();
            comp = rawAsm.substring(idxEq+1, idxSemicolon).trim();
            jump = rawAsm.substring(idxSemicolon+1).trim();
        }

        if (comp.contains("M") && !comp.contains("A")) {
            isMForComp = true;
        } else if (comp.contains("M") && comp.contains("A")) {
            throw new IOException("A instruction cannot compute A and M together");
        } else {
            isMForComp = false;
        }

        return true;
    }
}
