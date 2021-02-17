package command.impl.memory.pop;

import command.CommandPop;

import java.io.IOException;

/**
 * Created by muscaestar on 12/22/20
 *
 * @author muscaestar
 */
public class CmdPopTemp implements CommandPop {
    static int count = 0;

    int index;
    String filename;

    public CmdPopTemp(int index, String filename) {
        this.index = index;
        this.filename = filename;
    }

    @Override
    public String toAssembly() throws IOException {
        String addr = "@"+filename+"_temp_"+count+"\n";
        count++;
        return "// pop temp " + index + "\n" +
                "@5\n" +
                "D=A\n" +
                "@"+index+"\n" +
                "D=D+A\n" + addr +
                "M=D\n" + SP_MINUS_ONE + D_EQ_PSP + addr + PADDR_EQ_PSP;
    }
}
