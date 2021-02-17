package command;

import java.io.IOException;

/**
 * Created by muscaestar on 12/21/20
 *
 * @author muscaestar
 */
public interface VMCommand {
    String toAssembly() throws IOException;
}
