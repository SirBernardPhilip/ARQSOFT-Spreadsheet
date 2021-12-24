package edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions;

public class MultiSpreadsheetException extends Exception {
    /**
     * Creates new MultiSpreadsheetException with the given message.
     * It is the superclass of all exceptions thrown by the MultiSpreadsheet entities.
     * 
     * @param message
     */
    public MultiSpreadsheetException(String message) {
        super(message);
    }
}