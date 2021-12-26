package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperand;

public class FormulaCellReference implements IFormulaOperand {

    private ICellCoordinate cellCoordinate;

    private FormulaCellReference(ICellCoordinate cellCoordinate) {
        this.cellCoordinate = cellCoordinate;
    }

    public static FormulaCellReference getInstance(ICellCoordinate cellCoordinate) {
        return new FormulaCellReference(cellCoordinate);
    }


    @Override
    public void accept(IFormulaElementVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Double getValue() {
        // TODO Auto-generated method stub
        return null;
    }

}
