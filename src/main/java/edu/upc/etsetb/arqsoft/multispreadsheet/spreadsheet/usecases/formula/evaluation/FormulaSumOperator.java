package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperator;

public class FormulaSumOperator implements IFormulaOperator {

    private FormulaSumOperator() {

    }

    public static FormulaSumOperator getInstance() {
        return new FormulaSumOperator();
    }

    @Override
    public Double operate(Double leftOperand, Double rightOperand) {
        return leftOperand + rightOperand;
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) throws MultiSpreadsheetException {
        visitor.visit(this);
    }

}
