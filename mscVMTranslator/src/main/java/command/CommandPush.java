package command;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public interface CommandPush extends VMCommand{
    String PSP_EQ_PADDR =
            "@SP\n" +
            "A=M\n" +
            "M=D\n";
    String SP_PLUS_ONE =
            "@SP\n" +
            "M=M+1\n";
}
