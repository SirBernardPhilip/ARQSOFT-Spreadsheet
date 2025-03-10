package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ICellDependencyManager;

public class CellDependencyManager implements ICellDependencyManager {
    Map<ICellCoordinate, List<ICellCoordinate>> cellDependencies;
    Map<ICellCoordinate, List<ICellCoordinate>> onCellDependencies;

    private CellDependencyManager() {
        this.cellDependencies = new HashMap<ICellCoordinate, List<ICellCoordinate>>();
        this.onCellDependencies = new HashMap<ICellCoordinate, List<ICellCoordinate>>();
    }

    public static CellDependencyManager getInstance() {
        return new CellDependencyManager();
    }

    @Override
    public void reset() {
        this.cellDependencies = new HashMap<ICellCoordinate, List<ICellCoordinate>>();
        this.onCellDependencies = new HashMap<ICellCoordinate, List<ICellCoordinate>>();
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
        this.onCellDependencies.put(dependantCellCoordinate, cellCoordinates);
    }

    @Override
    public void removeDependantCell(ICellCoordinate dependantCellCoordinate) {
        for (ICellCoordinate cellCoordinate : this.cellDependencies.keySet()) {
            List<ICellCoordinate> currentDependencies = this.cellDependencies.get(cellCoordinate);
            currentDependencies.remove(dependantCellCoordinate);
            this.cellDependencies.put(cellCoordinate, currentDependencies);
        }
        this.onCellDependencies.put(dependantCellCoordinate, new LinkedList<ICellCoordinate>());
    }

    @Override
    public List<ICellCoordinate> getDependantCells(ICellCoordinate cellCoordinate) {
        if (this.cellDependencies.containsKey(cellCoordinate)) {
            return this.cellDependencies.get(cellCoordinate);
        } else {
            return new LinkedList<ICellCoordinate>();
        }
    }

    @Override
    public List<ICellCoordinate> getDependantOnCells(ICellCoordinate cellCoordinate) {
        if (this.onCellDependencies.containsKey(cellCoordinate)) {
            return this.onCellDependencies.get(cellCoordinate);
        } else {
            return new LinkedList<ICellCoordinate>();
        }
    }

    @Override
    public boolean findCircularReferences(ICellCoordinate cellCoordinate) {
        Map<ICellCoordinate, Boolean> visitedCells = new HashMap<ICellCoordinate, Boolean>();
        visitedCells.put(cellCoordinate, true);

        Queue<ICellCoordinate> queue = new LinkedList<ICellCoordinate>();
        List<ICellCoordinate> neighborCellCoordinates = this.getDependantCells(cellCoordinate);

        for (ICellCoordinate neighbor : neighborCellCoordinates) {
            if (!visitedCells.containsKey(neighbor)) {
                visitedCells.put(neighbor, true);
                queue.add(neighbor);
            } else {
                return true;
            }
        }
        while (queue.size() != 0) {
            ICellCoordinate top = queue.poll();

            neighborCellCoordinates = this.getDependantCells(top);

            for (ICellCoordinate neighbor : neighborCellCoordinates) {
                if (!visitedCells.containsKey(neighbor)) {
                    visitedCells.put(neighbor, true);
                    queue.add(neighbor);
                } else {
                    return true;
                }
            }

        }
        return false;
    }

}
