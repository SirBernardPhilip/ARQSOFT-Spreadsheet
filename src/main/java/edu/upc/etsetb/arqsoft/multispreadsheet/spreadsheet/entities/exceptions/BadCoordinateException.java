package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Superclass for the exception for bad coordinate input.
 */
public class BadCoordinateException extends MultiSpreadsheetException {
    /**
     * Creates new BadCoordinateException with the given message.
     * @param s
     */
    public BadCoordinateException(String s) {
        super(s);
    }
}