package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface IFormulaOperator extends IFormulaElement {
    public Double operate(Double leftOperand, Double rightOperand) throws MultiSpreadsheetException;
}
