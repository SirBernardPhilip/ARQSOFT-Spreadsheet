package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaCellReference;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;

public class FormulaCellReference implements IFormulaCellReference {

    private ICellCoordinate cellCoordinate;
    private Optional<Double> value;

    private FormulaCellReference(ICellCoordinate cellCoordinate) {
        this.cellCoordinate = cellCoordinate;
        this.value = Optional.empty();
    }

    public static FormulaCellReference getInstance(ICellCoordinate cellCoordinate) {
        return new FormulaCellReference(cellCoordinate);
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

    public void setValue(Double value) {
        this.value = Optional.of(value);
    }

    @Override
    public Boolean isCellReference() {
        return true;
    }

}
