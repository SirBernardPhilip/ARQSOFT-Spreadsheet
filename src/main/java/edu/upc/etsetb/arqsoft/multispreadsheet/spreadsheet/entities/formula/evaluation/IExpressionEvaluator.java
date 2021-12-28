package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface IExpressionEvaluator {
    public Double evaluate(List<IFormulaElement> elements) throws MultiSpreadsheetException;
}
