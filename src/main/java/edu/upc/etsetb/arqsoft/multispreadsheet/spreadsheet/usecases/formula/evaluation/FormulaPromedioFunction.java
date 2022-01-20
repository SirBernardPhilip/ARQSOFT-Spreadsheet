package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;

public class FormulaPromedioFunction implements IFormulaFunction {

    private FormulaPromedioFunction() {

    }

    public static FormulaPromedioFunction getInstance() {
        return new FormulaPromedioFunction();
    }

    @Override
    public Double evaluate(List<Double> arguments) {
        Double sum = 0d;
        for (int i = 0; i < arguments.size(); i++)
            sum += arguments.get(i);

        return sum / arguments.size();
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
