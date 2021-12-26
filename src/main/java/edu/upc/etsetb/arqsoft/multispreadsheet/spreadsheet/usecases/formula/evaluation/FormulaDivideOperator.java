package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperator;

public class FormulaDivideOperator implements IFormulaOperator {

    private FormulaDivideOperator() {

    }

    public static FormulaDivideOperator getInstance() {
        return new FormulaDivideOperator();
    }

    @Override
    public Double operate(Double leftOperand, Double rightOperand) {
        return leftOperand / rightOperand;
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) {
        visitor.visit(this);
    }

}
