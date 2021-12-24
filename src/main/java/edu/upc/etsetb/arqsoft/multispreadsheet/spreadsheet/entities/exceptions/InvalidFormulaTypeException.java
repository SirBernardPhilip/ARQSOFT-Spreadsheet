package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class InvalidFormulaTypeException extends MultiSpreadsheetException {
    public InvalidFormulaTypeException(String formulaType) {
        super(String.format("The formula type %s is not valid", formulaType));
    }
}