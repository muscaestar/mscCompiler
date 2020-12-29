package syntax;

import syntax.token.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by muscaestar on 12/26/20
 *
 * @author muscaestar
 */
public class JackTokenizer {

    private final List<JackToken> tokens = new LinkedList<>();

    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tokens>\n");
        for (JackToken tk : tokens) {
            sb.append(tk.toXml()).append("\n");
        }
        sb.append("</tokens>\n");
        return sb.toString();
    }

    public void consumeOneLine(String readLine) {
        // block comment
        readLine = readLine.trim();
        if (readLine.startsWith("/*") && readLine.endsWith("*/")) return;
        if (readLine.startsWith("/**") || readLine.startsWith("*") || readLine.startsWith("*/")) return;
        // in-line comment
        int idxCom = readLine.indexOf("//");
        if (idxCom > -1) {
            readLine = readLine.substring(0, idxCom).trim();
        } else {
            readLine = readLine.trim();
        }
        if (readLine.isEmpty()) return;

        int len = readLine.length();
        int offset = 0;
        while (offset < len) {
            char offsetChar = readLine.charAt(offset);
            if (offsetChar == '"') {
                int idxSecQuot = readLine.indexOf('"', offset + 1);
                tokens.add(StringConstant.of(readLine.substring(offset + 1, idxSecQuot)));
                offset = idxSecQuot + 1;
            } else if (offsetChar == ' ') {
                offset++;
            } else if (Symbol.isASymbol(offsetChar)) {
                tokens.add(Symbol.of(offsetChar));
                offset++;
            } else {
                int p = offset + 1;
                while (p < len) {
                    char pChar = readLine.charAt(p);
                    if (Symbol.isASymbol(pChar) || pChar == '"') {
                        processElement(readLine.substring(offset, p));
                        offset = p;
                        break;
                    } else if (pChar == ' ') {
                        processElement(readLine.substring(offset, p));
                        offset = p + 1;
                        break;
                    }
                    p++;
                }
                if (p >= len) {
                    processElement(readLine.substring(offset));
                    break;
                }
            }
        }
    }

    private void processElement(String s) {
//        if (Symbol.SYM_SET.contains(s)) {
//            tokens.add(Symbol.of(s));
//        } else
        if (Keyword.KEY_SET.contains(s)) {
            tokens.add(Keyword.of(s));
//        } else if (StringConstant.isCandidate(s)) {
//            tokens.add(StringConstant.of(StringConstant.extractConst(s)));
        } else if (Identifier.isDigitChar(s.charAt(0))) {
            tokens.add(IntConstant.of(s));
        } else {
            tokens.add(Identifier.of(s));
        }
    }

    public List<JackToken> getTokens() {
        return tokens;
    }
}
