package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.ContentException;

public class UnexpectedTokenException extends ContentException {
    public UnexpectedTokenException(String token) {
        super(String.format("Unexpected token %s found", token));
    }
}