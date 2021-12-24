package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax;


import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;

public class SpreadsheetSyntaxException extends SpreadsheetFormulaException {
    /**
     * Creates new SpreadsheetSyntaxException with the given message.
     * It is the superclass of all exceptions thrown by the formula entities.
     * 
     * @param message
     */
    public SpreadsheetSyntaxException(String message) {
        super(message);
    }
}