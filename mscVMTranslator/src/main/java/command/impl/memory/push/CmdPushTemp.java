package command.impl.memory.push;

import command.CommandPush;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdPushTemp implements CommandPush {
    int index;

    public CmdPushTemp(int idx) {
        this.index = idx;
    }

    @Override
    public String toAssembly() throws IOException {
        return "// push temp " + index + "\n" +
                "@5\n" +
                "D=A\n" +
                "@"+index+"\n" +
                "A=D+A\n" +
                "D=M\n" + PSP_EQ_PADDR + SP_PLUS_ONE;
    }
}
