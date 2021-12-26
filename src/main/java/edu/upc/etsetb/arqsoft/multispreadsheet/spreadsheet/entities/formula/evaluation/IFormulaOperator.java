package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

public interface IFormulaOperator extends IFormulaElement {
    public Double operate(Double leftOperand, Double rightOperand);
}
