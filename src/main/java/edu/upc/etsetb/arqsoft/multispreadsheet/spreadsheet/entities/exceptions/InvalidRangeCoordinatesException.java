package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class InvalidRangeCoordinatesException extends MultiSpreadsheetException {
    public InvalidRangeCoordinatesException(String coord1, String coord2) {
        super(String.format("The pair of coordinates %s and %s do not represent a valid range.", coord1, coord2));
    }
}