package command.impl.memory.pop;

import command.CommandPop;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by muscaestar on 12/22/20
 *
 * @author muscaestar
 */
public class CmdPopNorm implements CommandPop {
    private static final HashMap<String, String> MRY_SYMBOL_MAP = new HashMap<>();

    static {
        MRY_SYMBOL_MAP.put("local", "LCL");
        MRY_SYMBOL_MAP.put("argument", "ARG");
        MRY_SYMBOL_MAP.put("this", "THIS");
        MRY_SYMBOL_MAP.put("that", "THAT");
    }

    static int count = 0;

    String memorySeg;

    int index;

    String filename;

    public CmdPopNorm(String memorySeg, int idx, String filename) {
        this.memorySeg = memorySeg;
        this.index = idx;
        this.filename = filename;
    }

    @Override
    public String toAssembly() throws IOException {
        if (!MRY_SYMBOL_MAP.containsKey(memorySeg)) {
            throw new IOException("Illegal memory segment: "+ memorySeg);
        }
        String symbol = MRY_SYMBOL_MAP.get(memorySeg);
        String addr = "@"+filename+"_addr_"+count+"\n";
        count++;
        return "// pop " + memorySeg + " " + index + "\n" +
                "@"+symbol+"\n" +
                "D=M\n" +
                "@"+index+"\n" +
                "D=D+A\n" + addr +
                "M=D\n" + SP_MINUS_ONE + D_EQ_PSP + addr + PADDR_EQ_PSP;



    }
}
