package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

public interface IFormulaFunction extends IFormulaOperand {
    public Double evaluate(IFormulaFunctionArgument[] arguments);
}
