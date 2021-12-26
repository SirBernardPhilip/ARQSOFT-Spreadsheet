package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ESpreadsheetTokenType;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;

public class SpreadsheetToken implements ISpreadsheetToken {
    public final ESpreadsheetTokenType tokenType;
    public final String tokenString;

    private SpreadsheetToken(ESpreadsheetTokenType tokenType, String tokenString) {
        this.tokenType = tokenType;
        this.tokenString = tokenString;
    }

    public static SpreadsheetToken getInstance(ESpreadsheetTokenType tokenType, String tokenString) {
        return new SpreadsheetToken(tokenType, tokenString);
    }

    @Override
    public ESpreadsheetTokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String getTokenString() {
        return tokenString;
    }

    @Override
    public String toString() {
        return this.getTokenString();
    }

    @Override
    public boolean isPlusMinus() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR
                && (this.tokenString.equals("+") || this.tokenString.equals("-"));
    }

    @Override
    public boolean isMultDiv() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR
                && (this.tokenString.equals("*") || this.tokenString.equals("/"));
    }

    @Override
    public boolean isOperator() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR;
    }

    @Override
    public boolean isFunction() {
        return this.tokenType == ESpreadsheetTokenType.FUNCTION;
    }

    @Override
    public boolean isOpenPar() {
        return this.tokenType == ESpreadsheetTokenType.OPENING_PAR;
    }

    @Override
    public boolean isClosePar() {
        return this.tokenType == ESpreadsheetTokenType.CLOSING_PAR;

    }

    @Override
    public boolean isSemicolon() {
        return this.tokenType == ESpreadsheetTokenType.SEMICOLON;
    }

    @Override
    public boolean isNumber() {
        return this.tokenType == ESpreadsheetTokenType.NUM;
    }

    @Override
    public boolean isCell() {
        return this.tokenType == ESpreadsheetTokenType.CELL;
    }

    @Override
    public boolean isCellRange() {
        return this.tokenType == ESpreadsheetTokenType.RANGE;
    }

    @Override
    public boolean isPlus() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR
                && this.tokenString.equals("+");
    }

    @Override
    public boolean isMinus() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR
                && this.tokenString.equals("-");
    }

    @Override
    public boolean isMult() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR
                && this.tokenString.equals("*");
    }

    @Override
    public boolean isDiv() {
        return this.tokenType == ESpreadsheetTokenType.OPERATOR
                && this.tokenString.equals("/");
    }

}
