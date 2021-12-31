package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;


public class InvalidRangeCoordinatesException extends BadCoordinateException {
    public InvalidRangeCoordinatesException(String coord1, String coord2) {
        super(String.format("The pair of coordinates %s and %s do not represent a valid range.", coord1, coord2));
    }
}