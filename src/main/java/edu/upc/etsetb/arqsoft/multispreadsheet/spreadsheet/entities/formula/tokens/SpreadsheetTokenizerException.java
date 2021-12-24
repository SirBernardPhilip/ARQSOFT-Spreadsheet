package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens;



import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;

public class SpreadsheetTokenizerException extends SpreadsheetFormulaException {
    /**
     * Creates new SpreadsheetTokenizerException with the given message.
     * It is the superclass of all exceptions thrown by the formula entities.
     * 
     * @param message
     */
    public SpreadsheetTokenizerException(String message) {
        super(message);
    }
}