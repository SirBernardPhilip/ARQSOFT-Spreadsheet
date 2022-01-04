package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

/**
 * Exception thrown when a cell coordinate alphabetical part is invalid.
 */

public class InvalidStringCoordinatesException extends BadCoordinateException {
    /**
     * Creates the exception with the given alphabetical part.
     * @param sequence
     */
    public InvalidStringCoordinatesException(String sequence) {
        super(String.format("String must be a sequence of more than one capital letters but got %s", sequence));
    }
}