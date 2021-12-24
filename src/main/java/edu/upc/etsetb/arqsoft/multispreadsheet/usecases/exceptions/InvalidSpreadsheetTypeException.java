package edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions;

public class InvalidSpreadsheetTypeException extends Exception {
    public InvalidSpreadsheetTypeException(String spreadsheetType) {
        super(String.format("The spreadsheet type %s is not valid", spreadsheetType));
    }
}