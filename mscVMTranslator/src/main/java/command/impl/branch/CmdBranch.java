package command.impl.branch;

import command.CommandBranch;

import java.io.IOException;

/**
 * Created by muscaestar on 12/22/20
 *
 * @author muscaestar
 */
public class CmdBranch implements CommandBranch {

    String cmd;
    String label;
    String finalAsm;

    public static CmdBranch of(String cmd, String label, String filename) throws IOException {
        if (!cmd.equals("goto") && !cmd.equals("if-goto") && !cmd.equals("label")) {
            throw new IOException("Can't parse branch command: " + cmd + " " + label);
        }
        if (label.isEmpty()) {
            throw new IOException("Can't parse branch command's label: " + label);
        }
        return new CmdBranch(cmd, label, filename);
    }

    private CmdBranch(String cmd, String label, String filename) {
        this.cmd = cmd;
        this.label = filename + "$" + label;
        if (cmd.equals("goto")) {
            finalAsm = "@" + this.label + "\n" + "0;JMP\n";
        } else if (cmd.equals("if-goto")) {
            finalAsm = "@SP\nM=M-1\nA=M\nD=M\n@"+this.label+"\nD;JNE\n";
        } else {
            finalAsm = "(" + this.label + ")\n";
        }
    }

    @Override
    public String toAssembly() throws IOException {
        return "// " + cmd + " " + label+ "\n" + finalAsm;
    }
}
