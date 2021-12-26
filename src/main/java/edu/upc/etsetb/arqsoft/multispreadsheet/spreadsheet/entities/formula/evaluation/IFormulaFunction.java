package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import java.util.List;

public interface IFormulaFunction extends IFormulaOperand {
    public Double evaluate(List<Double> arguments);
}
