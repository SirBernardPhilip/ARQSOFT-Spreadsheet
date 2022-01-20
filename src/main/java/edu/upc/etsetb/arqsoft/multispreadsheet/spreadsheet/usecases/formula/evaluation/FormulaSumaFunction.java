package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;

public class FormulaSumaFunction implements IFormulaFunction {

    private FormulaSumaFunction() {

    }

    public static FormulaSumaFunction getInstance() {
        return new FormulaSumaFunction();
    }

    @Override
    public Double evaluate(List<Double> arguments) {
        Double sum = 0d;
        for (int i = 0; i < arguments.size(); i++)
            sum += arguments.get(i);

        return sum;
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
