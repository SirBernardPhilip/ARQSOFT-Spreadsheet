package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.SpreadsheetSyntaxException;

public class UnexpectedStackTypeException extends SpreadsheetSyntaxException {
    public UnexpectedStackTypeException() {
        super("The stack had an array of values while we wanted a single value");
    }
}