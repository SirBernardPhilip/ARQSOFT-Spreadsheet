package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.ContentException;

public class UnexpectedStackTypeException extends ContentException {
    public UnexpectedStackTypeException() {
        super("The stack had an array of values while we wanted a single value");
    }
}