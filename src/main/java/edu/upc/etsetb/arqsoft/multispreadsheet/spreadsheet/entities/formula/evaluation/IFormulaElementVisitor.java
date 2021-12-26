package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;

public interface IFormulaElementVisitor {
    public void visit(IFormulaOperator operator);
    public void visit(IFormulaOperand operator);

    public Double getResult() throws SpreadsheetFormulaException;
}
