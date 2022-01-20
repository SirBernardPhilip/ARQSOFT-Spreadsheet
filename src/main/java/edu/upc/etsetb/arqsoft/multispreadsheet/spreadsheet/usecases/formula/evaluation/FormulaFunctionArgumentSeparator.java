package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunctionArgumentSeparator;

public class FormulaFunctionArgumentSeparator implements IFormulaFunctionArgumentSeparator {

    private FormulaFunctionArgumentSeparator() {

    }

    public static FormulaFunctionArgumentSeparator getInstance() {
        return new FormulaFunctionArgumentSeparator();
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Boolean isCellReference() {
        return false;
    }
}
