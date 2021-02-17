import command.VMCommand;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by muscaestar on 12/18/20
 *
 * @author muscaestar
 */
public class VMWriter {
    private static PrintWriter writer;

    FileOutputStream fos;

    VMParser vmParser;

    public VMWriter(FileOutputStream fos, VMParser vmParser) {
        this.fos = fos;
        this.vmParser = vmParser;
    }

    public void write() throws IOException {
        try {
            if (writer == null) {
                writer = new PrintWriter(fos);
            }
            writer.println("// file " + vmParser.getFilename());
            List<VMCommand> commands = vmParser.getCommandList();
            for (VMCommand c : commands) {
                writer.println(c.toAssembly());
            }
            writer.flush();
        } catch (IOException e) {
            throw e;
        }
    }
}
