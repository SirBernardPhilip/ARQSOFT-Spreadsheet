package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.ContentException;

public class MissingParenthesisException extends ContentException {
    public MissingParenthesisException(String token) {
        super(String.format("Unexpected token %s found instead of closing parenthesis.", token));
    }
}