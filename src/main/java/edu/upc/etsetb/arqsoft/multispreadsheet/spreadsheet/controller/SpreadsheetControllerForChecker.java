package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.controller;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaCellReference;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ICellDependencyManager;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.CircularDependencyException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;

/**
 * Controller for the spreadsheet tests.
 * It differs from the actual controller in that it actually throws errors
 * instead of catching them and acting accordingly.
 */
public class SpreadsheetControllerForChecker implements ISpreadsheetControllerForChecker {

    private ISpreadsheetExpressionGenerator expressionGenerator;
    private IExpressionEvaluator expressionEvaluator;
    private ICellDependencyManager dependencyManager;
    protected ISpreadsheet spreadsheet;
    protected AMultiSpreadsheetFactory spreadsheetFactory;
    protected AMultiCellContentFactory cellContentFactory;

    /**
     * Create the controller.
     * 
     * @param spreadsheetFactory
     * @param cellContentFactory
     */
    protected SpreadsheetControllerForChecker(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        ISpreadsheetFormulaFactory formulaFactory = ISpreadsheetFormulaFactory.getInstance(spreadsheetFactory);
        this.expressionGenerator = formulaFactory.getSpreadsheetExpressionGenerator(formulaFactory);
        this.expressionEvaluator = formulaFactory.getExpressionEvaluator(formulaFactory);
        this.dependencyManager = formulaFactory.getDependencyManager();
        this.spreadsheetFactory = spreadsheetFactory;
        this.cellContentFactory = cellContentFactory;
        this.spreadsheet = spreadsheetFactory.getSpreadsheet(spreadsheetFactory);

    }

    /**
     * Static method to get a controller instance.
     * 
     * @param spreadsheetFactory
     * @param cellContentFactory
     * @return SpreadsheetControllerForChecker
     */
    public static SpreadsheetControllerForChecker getInstance(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return new SpreadsheetControllerForChecker(spreadsheetFactory, cellContentFactory);
    }

    /**
     * Set the content of a cell
     * 
     * @throws MultiSpreadsheetException
     */
    public void setCellContent(String cellCoordString, String cellContentString)
            throws MultiSpreadsheetException {

        Optional<ICellCoordinate> cellCoord = Optional.empty();

        cellCoord = Optional.of(this.spreadsheetFactory.getCellCoordinate(cellCoordString));

        if (cellCoord.isPresent()) {
            this.dependencyManager.removeDependantCell(cellCoord.get());

            ICellContent cellContent = this.spreadsheetFactory.getCellContent(cellContentString,
                    this.cellContentFactory);

            if (cellContent.isFormulaContent()) {
                this.expressionGenerator.reset();
                this.expressionEvaluator.reset();
                Optional<List<IFormulaElement>> elements;
                try {
                    this.expressionGenerator.generate(cellContentString.substring(1).replaceAll("\\s+", ""));
                } catch (SpreadsheetFormulaException e) {
                    cellContent.setError("Expr. Err.");
                    this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
                    this.updateDependantCells(cellCoord.get());
                    throw e;
                }
                elements = Optional.of(this.expressionGenerator.getElements());
                List<ICellCoordinate> cellCoordinates = new LinkedList<ICellCoordinate>();
                for (IFormulaElement element : elements.get()) {
                    if (element.isCellReference()) {
                        cellCoordinates.add(((FormulaCellReference) element).getCellCoordinate());
                    }
                }
                this.dependencyManager.addDependantCell(cellCoord.get(), cellCoordinates);

                if (elements.isPresent()) {
                    cellContent.setElements(elements.get());

                    if (!this.dependencyManager.findCircularReferences(cellCoord.get())) {
                        try {
                            cellContent
                                    .setValue(this.expressionEvaluator.evaluate(elements.get(), this.spreadsheet));
                        } catch (MultiSpreadsheetException e) {
                            cellContent.setError("Eval. Err.");
                            this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
                            this.updateDependantCells(cellCoord.get());
                            throw e;
                        }

                    } else {
                        cellContent.setError("Circ. Ref. Err.");
                        this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
                        this.updateDependantCells(cellCoord.get());
                        System.out.println("There was a circular reference.");
                        throw new CircularDependencyException();
                    }

                }

            }
            this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
            this.updateDependantCells(cellCoord.get());
            System.out.println(String.format("Cell %s edited with content %s", cellCoordString, cellContentString));
        }
    }

    /**
     * Update the dependant cells of a cell.
     * 
     * @param cellCoord
     */
    private void updateDependantCells(ICellCoordinate originalCellCoordinate) {
        Map<ICellCoordinate, Boolean> visitedCells = new HashMap<ICellCoordinate, Boolean>();
        visitedCells.put(originalCellCoordinate, true);

        Queue<ICellCoordinate> queue = new LinkedList<ICellCoordinate>();
        List<ICellCoordinate> neighborCellCoordinates = this.dependencyManager
                .getDependantCells(originalCellCoordinate);

        for (ICellCoordinate neighbor : neighborCellCoordinates) {
            if (!visitedCells.containsKey(neighbor)) {
                visitedCells.put(neighbor, true);
                queue.add(neighbor);
            }
        }
        while (queue.size() != 0) {
            ICellCoordinate top = queue.poll();

            ICellContent cellContent = this.spreadsheet.getCell(top).get().getContentClass();
            if (this.dependencyManager.findCircularReferences(top)) {
                cellContent.setError("Circ. Ref. Err.");
            } else {
                try {
                    this.expressionGenerator.reset();
                    this.expressionEvaluator.reset();
                    cellContent
                            .setValue(this.expressionEvaluator.evaluate(cellContent.getElements(), this.spreadsheet));

                } catch (MultiSpreadsheetException | NumberFormatException e) {
                    cellContent.setError("Eval. Err.");
                }
            }

            neighborCellCoordinates = this.dependencyManager.getDependantCells(top);

            for (ICellCoordinate neighbor : neighborCellCoordinates) {
                if (!visitedCells.containsKey(neighbor)) {
                    visitedCells.put(neighbor, true);
                    queue.add(neighbor);
                }
            }

        }
    }

    public double getCellContentAsDouble(String coord) throws MultiSpreadsheetException {
        ICellCoordinate cellCoord = this.spreadsheetFactory.getCellCoordinate(coord);
        return this.spreadsheet.getCellNumericalValue(cellCoord);
    }

    public String getCellContentAsString(String coord) throws MultiSpreadsheetException {
        ICellCoordinate cellCoord = this.spreadsheetFactory.getCellCoordinate(coord);
        return this.spreadsheet.getCellStringValue(cellCoord);
    }

}
