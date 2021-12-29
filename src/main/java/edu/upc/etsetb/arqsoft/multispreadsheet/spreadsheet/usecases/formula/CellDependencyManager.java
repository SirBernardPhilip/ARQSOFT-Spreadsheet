package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ICellDependencyManager;

public class CellDependencyManager implements ICellDependencyManager {
    Map<ICellCoordinate, List<ICellCoordinate>> cellDependencies;

    private CellDependencyManager() {
        this.cellDependencies = new HashMap<ICellCoordinate, List<ICellCoordinate>>();
    }

    public static CellDependencyManager getInstance() {
        return new CellDependencyManager();
    }

    @Override
    public void reset(){
        this.cellDependencies = new HashMap<ICellCoordinate, List<ICellCoordinate>>();
    }

    @Override
    public void addDependantCell(ICellCoordinate dependantCellCoordinate, List<ICellCoordinate> cellCoordinates) {
        for (ICellCoordinate cellCoordinate : cellCoordinates) {
            if (this.cellDependencies.containsKey(cellCoordinate)) {
                List<ICellCoordinate> currentDependencies = this.cellDependencies.get(cellCoordinate);
                currentDependencies.add(dependantCellCoordinate);
                this.cellDependencies.put(cellCoordinate, currentDependencies);
            } else {
                List<ICellCoordinate> currentDependencies = new LinkedList<ICellCoordinate>();
                currentDependencies.add(dependantCellCoordinate);
                this.cellDependencies.put(cellCoordinate, currentDependencies);
            }
        }
    }

    @Override
    public void removeDependantCell(ICellCoordinate dependantCellCoordinate) {
        for (ICellCoordinate cellCoordinate : this.cellDependencies.keySet()) {
            List<ICellCoordinate> currentDependencies = this.cellDependencies.get(cellCoordinate);
            currentDependencies.remove(dependantCellCoordinate);
            this.cellDependencies.put(cellCoordinate, currentDependencies);
        }
    }

    @Override
    public List<ICellCoordinate> getDependantCells(ICellCoordinate cellCoordinate) {
        if (this.cellDependencies.containsKey(cellCoordinate)) {
            return this.cellDependencies.get(cellCoordinate);
        } else {
            return new LinkedList<ICellCoordinate>();
        }
    }

    private boolean auxFindCircularReferences(ICellCoordinate goal, ICellCoordinate initial) {
        if (this.cellDependencies.containsKey(initial)) {
            Boolean returnBoolean = false;
            for (ICellCoordinate cellCoordinate : this.cellDependencies.get(initial)) {
                if (cellCoordinate == goal) {
                    return true;
                } else {
                    returnBoolean = returnBoolean || auxFindCircularReferences(goal, cellCoordinate);
                }
            }
            return returnBoolean;
        } else {
            return false;
        }
    }

    @Override
    public boolean findCircularReferences(ICellCoordinate cellCoordinate) {
        return auxFindCircularReferences(cellCoordinate, cellCoordinate);
    }

}
