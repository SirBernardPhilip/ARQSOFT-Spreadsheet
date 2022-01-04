package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions;

public class NoNumberException extends SpreadsheetFormulaException {
    /**
     * Creates new ContentException with the given message.
     * 
     * @param message
     */
    public NoNumberException(String s) {
        super(String.format("The content %s is not a number.", s));
    }
}