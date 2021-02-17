package command;

/**
 * Created by muscaestar on 12/22/20
 *
 * @author muscaestar
 */
public interface CommandPop extends VMCommand{
    String SP_MINUS_ONE = "@SP\nM=M-1\n";
    String D_EQ_PSP = "A=M\nD=M\n";
    String PADDR_EQ_PSP = "A=M\nM=D\n";
}
