package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class InvalidStringCoordinatesException extends MultiSpreadsheetException {
    public InvalidStringCoordinatesException(String sequence) {
        super(String.format("String must be a sequence of more than one capital letters but got %s", sequence));
    }
}