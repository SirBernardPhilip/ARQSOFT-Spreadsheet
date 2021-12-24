package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class IncompleteCoordinatesException extends MultiSpreadsheetException {
    public IncompleteCoordinatesException(String sequence) {
        super(String.format("Coordinate must be a sequence of more than one capital letters and numbers but got %s",
                sequence));
    }
}