package command.impl.memory.push;

import command.CommandPush;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdPushNorm implements CommandPush {
    private static final HashMap<String, String> MRY_SYMBOL_MAP = new HashMap<>();

    static {
        MRY_SYMBOL_MAP.put("local", "LCL");
        MRY_SYMBOL_MAP.put("argument", "ARG");
        MRY_SYMBOL_MAP.put("this", "THIS");
        MRY_SYMBOL_MAP.put("that", "THAT");
    }

    int index;

    String memorySeg;

    public CmdPushNorm(String memorySeg, int idx) {
        this.index = idx;
        this.memorySeg = memorySeg;
    }

    @Override
    public String toAssembly() throws IOException {
        if (!MRY_SYMBOL_MAP.containsKey(memorySeg)) {
            throw new IOException("Illegal memory segment: "+ memorySeg);
        }
        String symbol = MRY_SYMBOL_MAP.get(memorySeg);
        return "// push " + memorySeg + " " + index + "\n" +
                "@"+symbol+"\n" +
                "D=M\n" +
                "@"+index+"\n" +
                "A=D+A\n" +
                "D=M\n" + PSP_EQ_PADDR + SP_PLUS_ONE;
    }
}
