package command.impl.memory.push;

import command.CommandPush;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdPushPointer implements CommandPush {

    int index;

    public CmdPushPointer(int idx) {
        this.index = idx;
    }

    @Override
    public String toAssembly() throws IOException {
        return "// push pointer " + index + "\n" +
                "@"+ (index == 0 ? "THIS" : "THAT") + "\n" +
                "D=M\n" + PSP_EQ_PADDR + SP_PLUS_ONE;

    }
}
