import command.VMCommand;
import command.impl.branch.CmdBranch;
import command.impl.function.CmdFunc;
import command.impl.math.CmdMathArithmetic;
import command.impl.math.CmdMathCmp;
import command.impl.math.CmdMathLogic;
import command.impl.memory.pop.CmdPopNorm;
import command.impl.memory.pop.CmdPopPointer;
import command.impl.memory.pop.CmdPopStatic;
import command.impl.memory.pop.CmdPopTemp;
import command.impl.memory.push.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by muscaestar on 12/18/20
 *
 * @author muscaestar
 */
public class VMParser {
    FileInputStream fis;

    String filename;

    List<VMCommand> commandList = new ArrayList<>();

    public VMParser(FileInputStream fis, String filename) {
        this.fis = fis;
        this.filename = filename;
    }

    public void parse() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            while (reader.ready()) {
                String curr = reader.readLine().trim();
                if (curr.isEmpty() || curr.startsWith("//")) {
                    continue;
                }
                int idxOfComment = curr.indexOf("//");
                if (idxOfComment > 0) {
                    curr = curr.substring(0, idxOfComment).trim();
                }
                String[] arr = curr.split(" ");
                VMCommand cmd;
                if (arr[0].equals("return") || arr[0].equals("call") || arr[0].equals("function")) {
                    // function
                    cmd = createFuncCmd(arr);
                } else if (arr.length == 1) {
                    // arithmetic
                    cmd = createMathCmd(arr[0]);
                } else if (arr.length == 2) {
                    // branching
                    cmd = createBranchCmd(arr);
                } else if (arr.length == 3) {
                    // memory
                    cmd = createMryCmd(arr);
                } else {
                    throw new IOException("Can't parse current line: " + curr);
                }
                commandList.add(cmd);
            }

        } catch (IOException e) {
            throw e;
        }
    }

    private VMCommand createFuncCmd(String[] arr) {
        return CmdFunc.of(arr);
    }

    private VMCommand createBranchCmd(String[] arr) throws IOException {
        return CmdBranch.of(arr[0], arr[1], filename);
    }

    private VMCommand createMathCmd(String cmd) throws IOException {
        switch (cmd) {
            case "add":
            case "sub":
            case "neg":
                return new CmdMathArithmetic(cmd);
            case "and":
            case "or":
            case "not":
                return new CmdMathLogic(cmd);
            case "eq":
            case "gt":
            case "lt":
                return new CmdMathCmp(cmd, filename);
        }
        throw new IOException("Can't parse current math command: " + cmd );
    }

    private VMCommand createMryCmd(String[] arr) throws IOException {
        int idx = Integer.parseInt(arr[2]);
        if (arr[0].equals("push")) {
            switch (arr[1]) {
                case "constant":
                    return new CmdPushConst(idx);
                case "local":
                case "argument":
                case "this":
                case "that":
                    return new CmdPushNorm(arr[1], idx);
                case "temp":
                    return new CmdPushTemp(idx);
                case "static":
                    return new CmdPushStatic(idx, filename);
                case "pointer":
                    return new CmdPushPointer(idx);
            }
        } else if (arr[0].equals("pop")) {
            switch (arr[1]) {
                case "local":
                case "argument":
                case "this":
                case "that":
                    return new CmdPopNorm(arr[1], idx, filename);
                case "temp":
                    return new CmdPopTemp(idx, filename);
                case "static":
                    return new CmdPopStatic(idx, filename);
                case "pointer":
                    return new CmdPopPointer(idx);
            }

        }
        throw new IOException("Can't parse current memory command: " + Arrays.toString(arr));
    }

    public String getFilename() {
        return filename;
    }

    public List<VMCommand> getCommandList() {
        return commandList;
    }
}
