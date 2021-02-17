package command;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public interface CommandMath extends VMCommand {
    String SP_MINUS_ONE = "@SP\nM=M-1\n";
    String D_EQ_PSP = "A=M\nD=M\n";
    String SP_PLUS_ONE = "@SP\nM=M+1\n";

}
