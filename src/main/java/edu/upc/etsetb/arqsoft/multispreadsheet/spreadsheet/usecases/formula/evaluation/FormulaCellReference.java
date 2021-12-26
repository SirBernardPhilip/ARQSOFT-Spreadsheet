package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperand;

public class FormulaCellReference implements IFormulaOperand {

    private ICellCoordinate cellCoordinate;
    private Optional<Double> value;

    private FormulaCellReference(ICellCoordinate cellCoordinate, Optional<Double> value) {
        this.cellCoordinate = cellCoordinate;
        this.value = value;
    }

    public static FormulaCellReference getInstance(ICellCoordinate cellCoordinate, Optional<Double> value) {
        return new FormulaCellReference(cellCoordinate, value);
    }

    @Override
    public void accept(IFormulaElementVisitor visitor) throws MultiSpreadsheetException {
        visitor.visit(this);
    }

    public ICellCoordinate getCellCoordinate() {
        return this.cellCoordinate;
    }

    @Override
    public Double getValue() throws MultiSpreadsheetException {
        if (this.value.isPresent()) {
            return this.value.get();
        } else {
            throw new MultiSpreadsheetException("Value missing");
        }
    }

}
