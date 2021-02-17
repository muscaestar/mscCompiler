import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by muscaestar on 12/18/20
 *
 * @author muscaestar
 */
public class VMTranslator {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Pls add target file path as argument");
            System.exit(1);
        }
        String targetPath = args[0];
        File inputFile = new File(targetPath);
        if (inputFile.isFile()) {
            String filename = getNameWithoutExt(inputFile);
            File outputFile = new File(inputFile.getAbsoluteFile().getParentFile().getAbsolutePath()
                    + "/" + filename + ".asm");
            if (!outputFile.createNewFile()) {
                System.out.println("Assembly file already exists. Overwriting...");
            }
            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(outputFile);) {
                VMTranslator vmTranslator = new VMTranslator(filename);
                vmTranslator.translate(fis, fos);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

        } else {
            List<File> vmFileList = Arrays.stream(inputFile.listFiles())
                    .filter(file -> file.getName().endsWith(".vm"))
                    .collect(Collectors.toList());

            String outFilename = getDirectoryName(inputFile);
            File outputFile = new File(inputFile.getAbsolutePath() + "/" + outFilename + ".asm");
            if (!outputFile.createNewFile()) {
                System.out.println("Assembly file already exists. Overwriting...");
            }
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                for (File in : vmFileList) {
                    String filename = getNameWithoutExt(in);
                    try (FileInputStream fis = new FileInputStream(in)) {
                        VMTranslator vmTranslator = new VMTranslator(filename);
                        vmTranslator.translate(fis, fos);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        System.exit(0);
    }

    private static String getDirectoryName(File inputFile) {
        return inputFile.getName();
    }

    private static String getNameWithoutExt(File inputFile) {
        String filename = inputFile.getName();
        int dotIdx = filename.lastIndexOf('.');
        if (dotIdx != -1) {
            return filename.substring(0, dotIdx);
        }
        return filename;
    }

    public String filenameWithoutExt;

    public VMTranslator(String filenameWithoutExt) {
        this.filenameWithoutExt = filenameWithoutExt;
    }

    private void translate(FileInputStream fis, FileOutputStream fos) throws IOException {
        VMParser parser = new VMParser(fis, filenameWithoutExt);
        parser.parse();
        VMWriter writer = new VMWriter(fos, parser);
        writer.write();
    }

}
