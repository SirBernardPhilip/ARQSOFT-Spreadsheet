package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class SpreadsheetFormulaException extends MultiSpreadsheetException {
    /**
     * Creates new SpreadsheetFormulaException with the given message.
     * It is the superclass of all exceptions thrown by the formula entities.
     * 
     * @param message
     */
    public SpreadsheetFormulaException(String message) {
        super(message);
    }
}