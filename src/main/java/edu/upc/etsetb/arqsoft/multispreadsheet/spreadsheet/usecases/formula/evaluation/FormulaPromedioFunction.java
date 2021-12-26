package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunctionArgument;

public class FormulaPromedioFunction implements IFormulaFunction{

    @Override
    public Double evaluate(IFormulaFunctionArgument[] arguments) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) {
        visitor.visit(this);
    }
    
}
