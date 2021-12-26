package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import java.util.List;
import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface IExpressionEvaluator {
    public Optional<Double> evaluate(List<IFormulaElement> elements) throws MultiSpreadsheetException;
}
