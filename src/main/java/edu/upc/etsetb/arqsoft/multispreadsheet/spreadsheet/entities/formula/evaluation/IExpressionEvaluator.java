package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Interface for the expression evaluator.
 */
public interface IExpressionEvaluator {
    /**
     * Evaluates the expression.
     * 
     * @param spreadsheet
     * @param expression
     * @return Double
     * @throws MultiSpreadsheetException
     */
    public Double evaluate(List<IFormulaElement> elements, ISpreadsheet spreadsheet) throws MultiSpreadsheetException;

    /**
     * Reset the evaluator state. Needed for every different evaluation.
     */
    public void reset();
}
