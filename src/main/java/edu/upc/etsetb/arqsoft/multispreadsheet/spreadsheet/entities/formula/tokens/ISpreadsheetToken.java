package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens;


public interface ISpreadsheetToken {

    public ESpreadsheetTokenType getTokenType();

    public String getTokenString();

    public boolean isPlusMinus();

    public boolean isMultDiv();

    public boolean isOperator();

    public boolean isFunction();

    public boolean isOpenPar();

    public boolean isClosePar();

    public boolean isSemicolon();

    public boolean isNumber();

    public boolean isCell();

    public boolean isCellRange();

}