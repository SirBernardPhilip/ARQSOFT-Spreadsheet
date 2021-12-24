package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class InvalidValueException extends MultiSpreadsheetException {
    public InvalidValueException() {
        super("The formula did not yield a correct value");
    }
}
