package command.impl.math;

import command.CommandMath;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public class CmdMathCmp implements CommandMath {

    static int count = 0;

    String cmd;

    String filename;

    String finalAsm;

    public CmdMathCmp(String cmd, String filename) {
        this.cmd = cmd;
        this.filename = filename;
        String labelBranch = filename+"_BRANCH_"+count;
        String labelJump = filename+"_JUMP_"+count;
        String spAsm = "A=M\nD=M-D\n@" + labelBranch + "\n" +
                "D;J"+cmd.toUpperCase()+"\n@SP\nA=M\nM=0\n" +
                "@"+labelJump + "\n0;JMP\n" +
                "(" + labelBranch + ")\n" +
                "@SP\nA=M\nM=-1\n" +
                "(" + labelJump + ")\n";
        finalAsm = CommandMath.SP_MINUS_ONE + CommandMath.D_EQ_PSP + CommandMath.SP_MINUS_ONE + spAsm + CommandMath.SP_PLUS_ONE;
        count++;
    }

    @Override
    public String toAssembly() throws IOException {
        return "// " + cmd + "\n" + finalAsm;
    }
}
