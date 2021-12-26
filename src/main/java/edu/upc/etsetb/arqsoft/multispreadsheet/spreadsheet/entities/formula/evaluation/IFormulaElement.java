package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface IFormulaElement {
    public void accept(IFormulaElementVisitor visitor) throws MultiSpreadsheetException;
}
