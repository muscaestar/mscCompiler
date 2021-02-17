package command.impl.math;

import command.CommandMath;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdMathArithmetic implements CommandMath {
    String finalAsm;
    String cmd;

    public CmdMathArithmetic(String cmd) {
        this.cmd = cmd;
        String spAsm;
        switch (cmd) {
            case "add":
                spAsm = "A=M\nM=M+D\n";
                finalAsm = SP_MINUS_ONE + D_EQ_PSP + SP_MINUS_ONE + spAsm + SP_PLUS_ONE;
                break;
            case "sub":
                spAsm = "A=M\nM=M-D\n";
                finalAsm = SP_MINUS_ONE + D_EQ_PSP + SP_MINUS_ONE + spAsm + SP_PLUS_ONE;
                break;
            case "neg":
                spAsm = "M=-D\n";
                finalAsm = SP_MINUS_ONE + D_EQ_PSP + spAsm + SP_PLUS_ONE;
                break;
        }
    }

    @Override
    public String toAssembly() throws IOException {
        return "// " + cmd + "\n" + finalAsm;
    }
}
