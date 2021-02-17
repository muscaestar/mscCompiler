import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by muscaestar on 12/16/20
 *
 * @author muscaestar
 */
public class HackAssembler {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Pls add target file path as argument");
            System.exit(1);
        }
        String targetPath = args[0];
        File inputFile = new File(targetPath);
        File outputFile = new File(inputFile.getAbsoluteFile().getParentFile().getAbsolutePath()
                + "/" + getNameWithoutExt(inputFile) + ".hack");
        if (!outputFile.createNewFile()) {
            System.out.println("Binary file already exists. Overwriting...");
        }
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);) {
            HackAssembler hackAssembler = new HackAssembler();
            hackAssembler.assemble(fis, fos);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

    private List<HackInstruction> insList = new ArrayList<>();
    private List<Integer> waitLines = new ArrayList<>();

    private HashMap<String, Integer> labelMap = new HashMap<>();

    private static String getNameWithoutExt(File inputFile) {
        String filename = inputFile.getName();
        int dotIdx = filename.lastIndexOf('.');
        if (dotIdx != -1) {
            return filename.substring(0, dotIdx);
        }
        return filename;
    }

    public void assemble(FileInputStream fis, FileOutputStream fos) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
             PrintWriter writer = new PrintWriter(fos)) {
            int lineNum = 0;
            // read
            while (reader.ready()) {
                String curr = reader.readLine().trim();
                if (curr.isEmpty() || curr.startsWith("//")) {
                    continue;
                }
                if (curr.startsWith("(")) {
                    registerLabel(curr, lineNum);
                    continue;
                }

                // parse
                HackInstruction ins;
                if (curr.charAt(0) == '@') {
                    ins = new InstructionA(curr);
                } else {
                    ins = new InstructionC(curr);
                }
                insList.add(ins);
                boolean parseRes = ins.parse(labelMap);
                if (!parseRes) {
                    waitLines.add(lineNum);
                }

                lineNum++;
            }
            for (int n : waitLines) {
                InstructionA insA = (InstructionA) insList.get(n);
                if (!insA.parse(labelMap)) {
                    insA.parseVar();
                }
            }
            // write
            for (HackInstruction ins : insList) {
                writer.println(ins.toMachineCode());
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private void registerLabel(String curr, int lineNum) throws IOException {
        String label = curr.substring(curr.indexOf('(') + 1, curr.lastIndexOf(')'));
        if (!labelMap.containsKey(label)) {
            labelMap.put(label, lineNum);
        } else if (InstructionA.SYMBOL_MAP.containsKey(label)) {
            throw new IOException("Cannot use pre-defined symbol as label");
        } else {
            throw new IOException("There are duplicated label in the asm file");
        }

    }
}
