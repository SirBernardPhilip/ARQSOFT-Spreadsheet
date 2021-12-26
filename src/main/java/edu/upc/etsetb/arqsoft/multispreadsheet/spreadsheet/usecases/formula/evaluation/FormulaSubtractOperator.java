package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperator;

public class FormulaSubtractOperator implements IFormulaOperator {

    private FormulaSubtractOperator() {

    }

    public static FormulaSubtractOperator getInstance() {
        return new FormulaSubtractOperator();
    }

    @Override
    public Double operate(Double leftOperand, Double rightOperand) {
        return leftOperand - rightOperand;
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) throws MultiSpreadsheetException {
        visitor.visit(this);
    }

}
