package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Interface for the expression visitor.
 */
public interface IFormulaElementVisitor {
    /**
     * Visit method for the visitor pattern.
     * 
     * @param operator
     * @throws MultiSpreadsheetException
     */
    public void visit(IFormulaOperator operator) throws MultiSpreadsheetException;

    /**
     * Visit method for the visitor pattern.
     * 
     * @param function
     */
    public void visit(IFormulaFunction function);

    /**
     * Visit method for the visitor pattern.
     * 
     * @param separator
     */
    public void visit(IFormulaFunctionArgumentSeparator separator);

    /**
     * Visit method for the visitor pattern.
     * 
     * @param operand
     * @throws MultiSpreadsheetException
     */
    public void visit(IFormulaOperand operand) throws MultiSpreadsheetException;

    /**
     * Get the final result of the shunting yard algorithm.
     * 
     * @return Double
     * @throws MultiSpreadsheetException
     */
    public Double getResult() throws MultiSpreadsheetException;

    /**
     * Reset the stack, needed for every different evaluation.
     */
    public void reset();
}
