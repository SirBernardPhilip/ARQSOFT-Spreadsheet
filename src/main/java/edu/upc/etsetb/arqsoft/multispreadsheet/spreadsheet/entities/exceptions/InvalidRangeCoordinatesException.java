package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

/**
 * Exception thrown when a cell coordinate range is invalid.
 */
public class InvalidRangeCoordinatesException extends BadCoordinateException {
    /**
     * Creates the exception with the given range.
     * @param range
     */
    public InvalidRangeCoordinatesException(String coord1, String coord2) {
        super(String.format("The pair of coordinates %s and %s do not represent a valid range.", coord1, coord2));
    }
}