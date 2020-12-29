package syntax.parse;

import syntax.token.Identifier;
import syntax.token.JackToken;
import syntax.token.Keyword;
import syntax.token.Symbol;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public class JackClass implements ParseElement {
    private Identifier className;
    private final List<ClassVar> classVars = new LinkedList<>();
    private final List<Subroutine> subroutines = new LinkedList<>();

    public Identifier getClassName() {
        return className;
    }

    public void setClassName(Identifier className) {
        this.className = className;
    }

    public List<ClassVar> getClassVars() {
        return classVars;
    }

    public List<Subroutine> getSubroutines() {
        return subroutines;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<class>").append("\n");
        sb.append(Keyword.of("class").toXml()).append("\n");
        sb.append(className.toXml()).append("\n");
        sb.append(Symbol.of("{").toXml()).append("\n");
        for (ClassVar classVar : classVars) {
            sb.append(classVar.toXml()).append("\n");
        }
        for (Subroutine subroutine : subroutines) {
            sb.append(subroutine.toXml()).append("\n");
        }
        sb.append(Symbol.of("}").toXml()).append("\n");
        sb.append("</class>").append("\n");
        return sb.toString();
    }

    public Keyword compileClassVars(ListIterator<JackToken> iterator) {
        JackToken token = iterator.next();
        while (ClassVar.CLASS_VAR_KEYWORD_SET.contains(token.getTkv())) {
            ClassVar classVar = new ClassVar((Keyword) token);
            classVars.add(classVar);
            classVar.compileAVarName(iterator);
            token = iterator.next();
        }
        return (Keyword) token;
    }

    public void compileSubroutines(ListIterator<JackToken> iterator, JackToken currKeyword) {
        while (Subroutine.SUBROUTINE_KEYWORD_SET.contains(currKeyword.getTkv())) {
            Subroutine subroutine = new Subroutine((Keyword) currKeyword);
            subroutines.add(subroutine);
            subroutine.compileASubroutine(iterator);
            currKeyword = iterator.next();
        }
    }
}
