package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;

public interface ICellDependencyManager {

    public void addDependantCell(ICellCoordinate dependantCellCoordinate, List<ICellCoordinate> cellCoordinates);

    public void removeDependantCell(ICellCoordinate dependantCellCoordinate);

    public boolean findCircularReferences(ICellCoordinate cellCoordinate);

    /**
     * Get the cells that depend on the given coordinate
     * @param cellCoordinate
     * @return
     */
    public List<ICellCoordinate> getDependantCells(ICellCoordinate cellCoordinate);

    /**
     * Get the cells that the given coordinate depends on
     * @param cellCoordinate
     * @return
     */
    public List<ICellCoordinate> getDependantOnCells(ICellCoordinate cellCoordinate);

    public void reset();
}
