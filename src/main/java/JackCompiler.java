import syntax.CompileEngine;
import syntax.JackTokenizer;
import syntax.token.JackToken;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by muscaestar on 12/26/20
 *
 * @author muscaestar
 */
public class JackCompiler {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Pls add target file path as argument");
            System.exit(1);
        }
        String targetPath = args[0];
        File inputFile = new File(targetPath);
        if (inputFile.isFile()) {
            compileJackToTokens(inputFile);
        } else {
            List<File> vmFileList = Arrays.stream(inputFile.listFiles())
                    .filter(file -> file.getName().endsWith(".jack"))
                    .collect(Collectors.toList());
            for (File f : vmFileList) {
                compileJackToTokens(f);
            }
        }
        System.exit(0);
    }

    private static void compileJackToTokens(File inputFile) throws IOException {
        String filename = getNameWithoutExt(inputFile);
        File outputFile = new File(inputFile.getAbsoluteFile().getParentFile().getAbsolutePath()
                + "/" + filename + "Test.xml");
        if (!outputFile.createNewFile()) {
            System.out.println("Xml file already exists. Overwriting...");
        }
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);) {
            JackTokenizer tokenizer = readInToTokenizer(fis);
            CompileEngine engine = new CompileEngine(tokenizer.getTokens());
            engine.compile();
//            writeTokensToOut(tokenizer, fos);
            writeClassToOut(engine, fos);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void writeClassToOut(CompileEngine engine, FileOutputStream fos) {
        try (PrintWriter writer = new PrintWriter(fos)) {
            writer.print(engine.getJackClass().toXml());
        }
    }

    private static void writeTokensToOut(JackTokenizer tokenizer, FileOutputStream fos) {
        try (PrintWriter writer = new PrintWriter(fos)) {
            writer.print(tokenizer.toXml());
        }
    }

    private static JackTokenizer readInToTokenizer(FileInputStream fis) throws IOException {
        JackTokenizer tokenizer = new JackTokenizer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            while (reader.ready()) {
                tokenizer.consumeOneLine(reader.readLine());
            }
            return tokenizer;
        } catch (IOException e) {
            throw e;
        }
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
}
