package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.SpreadsheetSyntaxException;

public class UnexpectedTokenException extends SpreadsheetSyntaxException {
    public UnexpectedTokenException(String token) {
        super(String.format("Unexpected token %s found", token));
    }
}