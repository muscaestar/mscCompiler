package syntax.parse;

import codegen.CodeGenUtil;
import codegen.table.VarTable;
import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.*;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class Subroutine implements ParseElement {
    public static final String[] SUBROUTINE_KEYWORD = new String[] {"constructor", "method", "function"};
    public static final Set<String> SUBROUTINE_KEYWORD_SET = new HashSet<>(Arrays.asList(SUBROUTINE_KEYWORD));

    private final Keyword subrAttr;

    private Identifier subrName;

    private final List<Identifier> paramList = new LinkedList<>();

    private SubroutineBody subrBody;

    public Subroutine(Keyword currKeyword) {
        this.subrAttr = currKeyword;
    }

    public void compileASubroutine(ListIterator<JackToken> iterator) {
        JackType type = new JackType(iterator.next());
        Identifier identifier = (Identifier) iterator.next();
        identifier.setType(type);
        subrName = identifier;

        iterator.next(); // (

        compileParamList(iterator); // )
        subrBody = new SubroutineBody(subrName, subrAttr);
        subrBody.compileSubrBody(iterator);

        // recycle local level var table
        VarTable varTable = VarTable.getInstance();
        varTable.recycleLocalLvTable();
        CodeGenUtil.resetLabelCnts();
    }

    private void compileParamList(ListIterator<JackToken> iterator) {
        JackToken token = iterator.next();
        while (!token.getTkv().equals(")")) {
            if (token.getTkv().equals(",")) {
                token = iterator.next();
            }
            JackType type = new JackType(token);
            Identifier identifier = (Identifier) iterator.next();
            identifier.setType(type);
            paramList.add(identifier);
            token = iterator.next();
        }
        // )
        registerInVarTable();
    }

    private void registerInVarTable() {
        // add argument to var table
        VarTable varTable = VarTable.getInstance();
        if (subrAttr.getTkv().equals("method")) {
            varTable.add("this", null, "argument");
        }
        paramList.forEach(i -> varTable.add(i.getTkv(), i.getType().getName(), "argument"));
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<subroutineDec>\n");
        sb.append(subrAttr.toXml()).append("\n");
        sb.append(subrName.toXml()).append("\n");
        sb.append(Symbol.of("(").toXml()).append("\n");
        sb.append("<parameterList>\n");
        for (Identifier param : paramList) {
            sb.append(param.toXml()).append("\n");
            sb.append(Symbol.of(",").toXml()).append("\n");
        }
        if (paramList.size() > 0) {
            sb.delete(sb.lastIndexOf("<symbol>"), sb.lastIndexOf("\n") + 1);
        }
        sb.append("</parameterList>\n");
        sb.append(Symbol.of(")").toXml()).append("\n");
        sb.append(subrBody.toXml()).append("\n");
        sb.append("</subroutineDec>");
        return sb.toString();
    }
}
