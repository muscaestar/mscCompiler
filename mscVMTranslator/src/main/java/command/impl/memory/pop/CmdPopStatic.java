package command.impl.memory.pop;

import command.CommandPop;

import java.io.IOException;

/**
 * Created by muscaestar on 12/22/20
 *
 * @author muscaestar
 */
public class CmdPopStatic implements CommandPop {
    int index;
    String filename;

    public CmdPopStatic(int index, String filename) {
        this.index = index;
        this.filename = filename;
    }

    @Override
    public String toAssembly() throws IOException {
        String addr = "@"+filename+"."+index + "\n";
        return "// pop static " + index + "\n" +
                SP_MINUS_ONE + D_EQ_PSP + addr +
                "M=D\n";
    }
}
