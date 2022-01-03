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
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.FormulaContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ICellDependencyManager;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.CircularDependencyException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;

public class SpreadsheetControllerForChecker implements ISpreadsheetControllerForChecker {

    private ISpreadsheetExpressionGenerator expressionGenerator;
    private IExpressionEvaluator expressionEvaluator;
    private ICellDependencyManager dependencyManager;
    protected ISpreadsheet spreadsheet;
    protected AMultiSpreadsheetFactory spreadsheetFactory;
    protected AMultiCellContentFactory cellContentFactory;

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

    public static SpreadsheetControllerForChecker getInstance(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return new SpreadsheetControllerForChecker(spreadsheetFactory, cellContentFactory);
    }

    public void setCellContent(String cellCoordString, String cellContentString)
            throws MultiSpreadsheetException {

        Boolean foundCircular = false;
        Optional<ICellCoordinate> cellCoord = Optional.empty();

        cellCoord = Optional.of(this.spreadsheetFactory.getCellCoordinate(cellCoordString));

        if (cellCoord.isPresent()) {
            this.dependencyManager.removeDependantCell(cellCoord.get());

            ICellContent cellContent = this.spreadsheetFactory.getCellContent(cellContentString,
                    this.cellContentFactory);

            if (cellContent instanceof FormulaContent) {
                FormulaContent formulaContent = (FormulaContent) cellContent;

                this.expressionGenerator.reset();
                this.expressionEvaluator.reset();
                Optional<List<IFormulaElement>> elements;
                try {
                    this.expressionGenerator.generate(cellContentString.substring(1).replaceAll("\\s+", ""));
                } catch (SpreadsheetFormulaException e) {
                    formulaContent.setError("Expr. Err.");
                    throw e;
                }
                elements = Optional.of(this.expressionGenerator.getElements());
                List<ICellCoordinate> cellCoordinates = new LinkedList<ICellCoordinate>();
                for (IFormulaElement element : elements.get()) {
                    if (element instanceof FormulaCellReference) {
                        cellCoordinates.add(((FormulaCellReference) element).getCellCoordinate());
                    }
                }
                this.dependencyManager.addDependantCell(cellCoord.get(), cellCoordinates);

                if (elements.isPresent()) {
                    formulaContent.setElements(elements.get());

                    if (!this.dependencyManager.findCircularReferences(cellCoord.get())) {
                        try {
                            formulaContent
                                    .setValue(this.expressionEvaluator.evaluate(elements.get(), this.spreadsheet));
                        } catch (MultiSpreadsheetException e) {
                            formulaContent.setError("Eval. Err.");
                            throw e;
                        }

                    } else {
                        formulaContent.setError("Eval. Err.");
                        foundCircular = true;
                        System.out.println("There was a circular reference.");
                    }

                }

            }
            this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
            this.updateDependantCells(cellCoord.get());
            System.out.println(String.format("Cell %s edited with content %s", cellCoordString, cellContentString));
            if (foundCircular) {
                throw new CircularDependencyException();
            }

        }
    }

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

            FormulaContent cellContent = (FormulaContent) this.spreadsheet.getCell(top).get()
                    .getContentClass();
            try {
                this.expressionGenerator.reset();
                this.expressionEvaluator.reset();
                cellContent.setValue(this.expressionEvaluator.evaluate(cellContent.getElements(), this.spreadsheet));
            } catch (MultiSpreadsheetException | NumberFormatException e) {
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
