package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;


public class InvalidStringCoordinatesException extends BadCoordinateException {
    public InvalidStringCoordinatesException(String sequence) {
        super(String.format("String must be a sequence of more than one capital letters but got %s", sequence));
    }
}