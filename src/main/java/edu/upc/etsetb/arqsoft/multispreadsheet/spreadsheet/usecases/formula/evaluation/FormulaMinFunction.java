package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.Collections;
import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;

public class FormulaMinFunction implements IFormulaFunction {

    private FormulaMinFunction() {

    }

    public static FormulaMinFunction getInstance() {
        return new FormulaMinFunction();
    }

    @Override
    public Double evaluate(List<Double> arguments) {
        return Collections.min(arguments);
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
