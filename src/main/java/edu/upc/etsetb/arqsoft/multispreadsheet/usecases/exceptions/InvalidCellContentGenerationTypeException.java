package edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions;

public class InvalidCellContentGenerationTypeException extends Exception {
    public InvalidCellContentGenerationTypeException(String generationType) {
        super(String.format("The cell content generation of type %s is not valid", generationType));
    }
}