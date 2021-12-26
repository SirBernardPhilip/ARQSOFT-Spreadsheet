package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.SpreadsheetSyntaxException;

public class NoValueComputedException extends SpreadsheetSyntaxException {
    public NoValueComputedException() {
        super("The formula evaluation has not been able to achieve a result");
    }
}