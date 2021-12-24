package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.SpreadsheetTokenizerException;

public class UnexpectedCharacterException extends SpreadsheetTokenizerException {
    public UnexpectedCharacterException(String input) {
        super(String.format("Unexpected character in input: %s", input));
    }
}