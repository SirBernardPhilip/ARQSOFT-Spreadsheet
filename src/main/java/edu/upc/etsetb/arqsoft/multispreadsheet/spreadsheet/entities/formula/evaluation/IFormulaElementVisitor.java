package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;

public interface IFormulaElementVisitor {
    public void visit(IFormulaOperator operator) throws MultiSpreadsheetException;

    public void visit(IFormulaFunction operator);

    public void visit(IFormulaFunctionArgumentSeparator operator);

    public void visit(IFormulaOperand operator) throws MultiSpreadsheetException;

    public Optional<Double> getResult() throws SpreadsheetFormulaException;
}
