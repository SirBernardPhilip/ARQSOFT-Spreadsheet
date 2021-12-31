package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;


public class InvalidNumberCoordinatesException extends BadCoordinateException {
    public InvalidNumberCoordinatesException(Integer number) {
        super(String.format("Number must be greater than or equal to 1 but got %2d", number));
    }
}