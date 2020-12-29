package syntax;

import syntax.parse.JackClass;
import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;

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

    public CompileEngine(List<JackToken> tokens) {
        this.tokens = tokens;
    }

    public JackClass getJackClass() {
        return jackClass;
    }

    public void compile() {
        ListIterator<JackToken> iterator = tokens.listIterator();
        while (iterator.hasNext()) {
            JackToken token = iterator.next();
            if (token.getTkv().equals("class")) {
                compileClass(iterator);
            }
        }
    }

    private void compileClass(ListIterator<JackToken> iterator) {
        jackClass = new JackClass();
        jackClass.setClassName((Identifier) iterator.next());
        iterator.next(); // {
        Keyword currKeyword = jackClass.compileClassVars(iterator);
        jackClass.compileSubroutines(iterator, currKeyword);
    }
}
