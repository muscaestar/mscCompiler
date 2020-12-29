package syntax.parse.expression;

import syntax.token.*;

import java.util.ListIterator;

/**
 * Created by muscaestar on 12/28/20
 *
 * @author muscaestar
 */
public interface JackTerm {
    String toXmlTerm();
    static JackTerm compileTerm(ListIterator<JackToken> iterator, String symbolOfEnd) {
        JackToken currToken = iterator.next();
        if (currToken instanceof IntConstant || currToken instanceof StringConstant) {
            return (JackTerm) currToken;
        } else if (UnaryOpTerm.UNARY_OP_SET.contains(currToken.getTkv())) {
            Symbol unaryOp = (Symbol) currToken;
            JackTerm currTerm = compileTerm(iterator, symbolOfEnd);
            return new UnaryOpTerm(unaryOp, currTerm);
        } else if (currToken.getTkv().equals("(")) {
            JackExpression expression = new JackExpression();
            expression.compileExpression(iterator, ")");
            return expression;
        } else if (KeywordConstant.KEYWORD_CONST_SET.contains(currToken.getTkv())) {
            return new KeywordConstant((Keyword) currToken);
        } else {
            Identifier anIdentifier = (Identifier) currToken;
            currToken = iterator.next();
            if (currToken.getTkv().equals("(") || currToken.getTkv().equals(".")) {
                SubroutineCall subroutineCall = new SubroutineCall();
                subroutineCall.compileSubroutineCall(iterator, anIdentifier, (Symbol) currToken);
                return subroutineCall;
            } else if (currToken.getTkv().equals("[")) {
                JackExpression arrayExpr = new JackExpression();
                arrayExpr.compileExpression(iterator, "]");
                anIdentifier.setArrayExpr(arrayExpr);
                return anIdentifier;
            } else {
                currToken = iterator.previous();
                return anIdentifier;
            }
        }
    }
}
