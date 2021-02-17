package command.impl.memory.push;

import command.CommandPush;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdPushConst implements CommandPush {
    int index;

    public CmdPushConst(int index) {
        this.index = index;
    }

    @Override
    public String toAssembly() {
        return "// push constant " + index + "\n" +
                "@"+index+"\n" +
                "D=A\n" + PSP_EQ_PADDR + SP_PLUS_ONE;
    }
}
