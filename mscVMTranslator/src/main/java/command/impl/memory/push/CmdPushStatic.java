package command.impl.memory.push;

import command.CommandPush;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdPushStatic implements CommandPush {
    int index;

    String filename;

    public CmdPushStatic(int idx, String filename) {
        this.index = idx;
        this.filename = filename;
    }

    @Override
    public String toAssembly() throws IOException {
        return "// push static "+index+"\n" +
                "@"+filename+"."+index+"\n" +
                "D=M\n" + PSP_EQ_PADDR + SP_PLUS_ONE;
    }
}
