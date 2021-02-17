package command.impl.math;

import command.CommandMath;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdMathLogic implements CommandMath {

    String cmd;

    String finalAsm;

    public CmdMathLogic(String cmd) {
        this.cmd = cmd;
        String spAsm;
        switch (cmd) {
            case "and":
                spAsm = "A=M\nM=M&D\n";
                finalAsm = CommandMath.SP_MINUS_ONE + CommandMath.D_EQ_PSP + CommandMath.SP_MINUS_ONE + spAsm + CommandMath.SP_PLUS_ONE;
                break;
            case "or":
                spAsm = "A=M\nM=M|D\n";
                finalAsm = CommandMath.SP_MINUS_ONE + CommandMath.D_EQ_PSP + CommandMath.SP_MINUS_ONE + spAsm + CommandMath.SP_PLUS_ONE;
                break;
            case "not":
                spAsm = "M=!D\n";
                finalAsm = CommandMath.SP_MINUS_ONE + CommandMath.D_EQ_PSP + spAsm + CommandMath.SP_PLUS_ONE;
                break;
        }

    }

    @Override
    public String toAssembly() throws IOException {
        return "// " + cmd + "\n" + finalAsm;
    }
}
