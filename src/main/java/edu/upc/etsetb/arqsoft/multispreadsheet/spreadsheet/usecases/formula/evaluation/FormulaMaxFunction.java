package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.Collections;
import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;

public class FormulaMaxFunction implements IFormulaFunction {

    private FormulaMaxFunction() {

    }

    public static FormulaMaxFunction getInstance() {
        return new FormulaMaxFunction();
    }

    @Override
    public Double evaluate(List<Double> arguments) {
        return Collections.max(arguments);
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
