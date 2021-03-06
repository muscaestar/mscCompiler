package syntax;

import codegen.CodeGenUtil;
import codegen.table.VarTable;
import syntax.parse.JackClass;
import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;

import java.io.FileOutputStream;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class CompileEngine {
    List<JackToken> tokens;
    JackClass jackClass;
    VarTable varTable;

    public CompileEngine(List<JackToken> tokens) {
        this.tokens = tokens;
        this.varTable = VarTable.getInstance();
    }

    public JackClass getJackClass() {
        return jackClass;
    }

    private void compile() {
        ListIterator<JackToken> iterator = tokens.listIterator();
        while (iterator.hasNext()) {
            JackToken token = iterator.next();
            if (token.getTkv().equals("class")) {
                compileClass(iterator);
            }
        }
    }

    public void compile(FileOutputStream fos) {
        CodeGenUtil.initCodeGen(fos);
        compile();
        CodeGenUtil.recycleCodeGenWriter();
    }

    private void compileClass(ListIterator<JackToken> iterator) {
        jackClass = new JackClass();
        jackClass.setClassName((Identifier) iterator.next());
        CodeGenUtil.setWriterClassName(jackClass.getClassName());
        iterator.next(); // {
        Keyword currKeyword = jackClass.compileClassVars(iterator);
        jackClass.compileSubroutines(iterator, currKeyword);
    }


    public void recycleVarTable() {
        varTable.recycleLocalLvTable();
        varTable.recycleClassLvTable();
    }
}
