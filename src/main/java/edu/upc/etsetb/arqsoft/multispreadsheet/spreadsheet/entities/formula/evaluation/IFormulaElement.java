package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Interface for every formula element.
 */
public interface IFormulaElement {
    /**
     * Accept method for the visitor pattern.
     * @param visitor
     * @throws MultiSpreadsheetException
     */
    public void accept(IFormulaElementVisitor visitor) throws MultiSpreadsheetException;

    public Boolean isCellReference();
}
