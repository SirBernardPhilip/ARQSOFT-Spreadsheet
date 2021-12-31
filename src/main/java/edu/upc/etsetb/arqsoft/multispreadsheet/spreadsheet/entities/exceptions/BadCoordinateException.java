package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class BadCoordinateException extends MultiSpreadsheetException {
    public BadCoordinateException(String s) {
        super(s);
    }
}