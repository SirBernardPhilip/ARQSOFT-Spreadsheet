package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class InvalidNumberCoordinatesException extends MultiSpreadsheetException {
    public InvalidNumberCoordinatesException(Integer number) {
        super(String.format("Number must be greater than or equal to 1 but got %2d", number));
    }
}