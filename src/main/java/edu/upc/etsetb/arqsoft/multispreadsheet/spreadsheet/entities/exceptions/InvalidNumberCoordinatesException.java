package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

/**
 * Exception for bad number in a coordinate.
 */
public class InvalidNumberCoordinatesException extends BadCoordinateException {
    /**
     * Creates the exception with the given number.
     * @param number
     */
    public InvalidNumberCoordinatesException(Integer number) {
        super(String.format("Number must be greater than or equal to 1 but got %2d", number));
    }
}