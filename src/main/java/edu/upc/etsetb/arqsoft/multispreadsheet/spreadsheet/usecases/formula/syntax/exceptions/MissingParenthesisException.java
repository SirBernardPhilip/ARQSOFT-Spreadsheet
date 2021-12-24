package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.SpreadsheetSyntaxException;

public class MissingParenthesisException extends SpreadsheetSyntaxException {
    public MissingParenthesisException(String token) {
        super(String.format("Unexpected token %s found instead of closing parenthesis.", token));
    }
}