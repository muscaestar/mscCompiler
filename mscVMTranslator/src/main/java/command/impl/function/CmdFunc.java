package command.impl.function;

import command.CommandFunc;

import java.io.IOException;

/**
 * Created by muscaestar on 12/24/20
 *
 * @author muscaestar
 */
public class CmdFunc implements CommandFunc {
    private static final String returnAsm = "// return \n" +
            "@LCL\nD=M\n@5\nM=D\nD=D-A\nA=D\nD=M\n" +
            "@6\nM=D\n@SP\nM=M-1\nA=M\nD=M\n" +
            "@ARG\nA=M\nM=D\n@ARG\nD=M+1\n@SP\nM=D\n" +
            "@5\nD=M-1\nA=D\nD=M\n@THAT\nM=D\n" +
            "@5\nD=M\n@2\nD=D-A\nA=D\nD=M\n@THIS\nM=D\n" +
            "@5\nD=M\n@3\nD=D-A\nA=D\nD=M\n@ARG\nM=D\n" +
            "@5\nD=M\n@4\nD=D-A\nA=D\nD=M\n@LCL\nM=D\n" +
            "@6\nA=M\n0;JMP\n";
    private static int callCnt = 0;

    String finalAsm;

    public static CmdFunc of(String[] arr) {
        StringBuilder sb;
        if (arr.length == 1) {
            // return
            sb = new StringBuilder(returnAsm);
        } else if (arr[0].equals("function")) {
            // function
            sb = new StringBuilder("// function ");
            sb.append(arr[1]).append(" ").append(arr[2]).append("\n");
            sb.append("(").append(arr[1]).append(")\n");
            for (int i = 0; i < Integer.parseInt(arr[2]); i++) {
                sb.append("@SP\nA=M\nM=0\n@SP\nM=M+1\n");
            }
        } else {
            // call
            String returnAddr = arr[1]+"$ret."+callCnt;
            sb = new StringBuilder("// call ");
            sb.append(arr[1]).append(" ").append(arr[2]).append("\n");
            sb.append("@").append(returnAddr).append("\n")
                    .append("D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n")
                    .append("@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n")
                    .append("@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n")
                    .append("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n")
                    .append("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n")
                    .append("D=M\n@5\nD=D-A\n@").append(arr[2]).append("\n")
                    .append("D=D-A\n@ARG\nM=D\n@SP\nD=M\n@LCL\nM=D\n")
                    .append("@").append(arr[1]).append("\n")
                    .append("0;JMP\n").append("(").append(returnAddr).append(")\n");
            callCnt++;
        }

        return new CmdFunc(sb.toString());
    }

    private CmdFunc(String finalAsm) {
        this.finalAsm = finalAsm;
    }

    @Override
    public String toAssembly() throws IOException {
        return finalAsm;
    }
}
