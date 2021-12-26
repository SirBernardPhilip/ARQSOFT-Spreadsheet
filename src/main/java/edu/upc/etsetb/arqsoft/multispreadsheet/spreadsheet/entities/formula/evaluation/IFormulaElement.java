package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

public interface IFormulaElement {
    public void accept(IFormulaElementVisitor visitor);
}
