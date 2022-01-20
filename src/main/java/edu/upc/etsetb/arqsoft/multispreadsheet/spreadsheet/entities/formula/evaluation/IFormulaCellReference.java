package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;

public interface IFormulaCellReference extends IFormulaOperand {

    public ICellCoordinate getCellCoordinate();

}
