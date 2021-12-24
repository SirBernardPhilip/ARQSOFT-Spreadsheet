package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.postfix;

import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;

public class PrecedenceManager {

    private final static int MAX_PRECEDENCE = 4;
    private final static int MIN_PRECEDENCE = 0;

    private static int precedenceNumber(ISpreadsheetToken token) {
        if (token.isOpenPar() || token.isFunction()) {
            return PrecedenceManager.MIN_PRECEDENCE;
        } else if (token.isSemicolon()) {
            return 1;
        } else if (token.isPlusMinus()) {
            return 2;
        } else if (token.isMultDiv()) {
            return 3;
        } else {
            return PrecedenceManager.MAX_PRECEDENCE;
        }
    }

    public static final EPRecedenceActionCode getStackAction(ISpreadsheetToken candidate,
            Optional<ISpreadsheetToken> stackHead) {
        if (!stackHead.isPresent()) {
            return EPRecedenceActionCode.PUSH_TO_STACK;
        } else if (PrecedenceManager.precedenceNumber(candidate) == PrecedenceManager.MAX_PRECEDENCE) {
            return EPRecedenceActionCode.FIND_NEXT_OPEN_PAR;
        } else if ((PrecedenceManager.precedenceNumber(candidate) == 0)
                || (PrecedenceManager.precedenceNumber(stackHead.get()) == 0)
                || (precedenceNumber(candidate) > precedenceNumber(stackHead.get()))) {
            return EPRecedenceActionCode.PUSH_TO_STACK;
        } else {
            return EPRecedenceActionCode.POP_FROM_STACK;
        }
    }
}
