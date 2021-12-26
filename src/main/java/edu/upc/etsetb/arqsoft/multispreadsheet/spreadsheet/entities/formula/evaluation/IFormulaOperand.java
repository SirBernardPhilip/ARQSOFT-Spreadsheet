package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface IFormulaOperand extends IFormulaElement {

    public Double getValue() throws MultiSpreadsheetException;
}
