package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperand;

public class FormulaNumeric implements IFormulaOperand {

    private Double value;

    private FormulaNumeric(Double value) {
        this.value = value;
    }

    public static FormulaNumeric getInstance(Double value) {
        return new FormulaNumeric(value);
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) throws MultiSpreadsheetException {
        visitor.visit(this);
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public Boolean isCellReference() {
        return false;
    }

}
