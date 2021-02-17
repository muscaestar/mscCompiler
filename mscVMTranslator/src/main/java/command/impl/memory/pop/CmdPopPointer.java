package command.impl.memory.pop;

import command.CommandPop;

import java.io.IOException;

/**
 * Created by muscaestar on 12/22/20
 *
 * @author muscaestar
 */
public class CmdPopPointer implements CommandPop {
    int index;

    public CmdPopPointer(int idx) {
        this.index = idx;
    }

    @Override
    public String toAssembly() throws IOException {
        return "// pop pointer " + index + "\n" +
                SP_MINUS_ONE + D_EQ_PSP +
                "@" + (index == 0 ? "THIS" : "THAT") + "\n" +
                "M=D\n";
    }
}
