package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.controller;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaCellReference;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        Optional<ICellCoordinate> cellCoord = Optional.empty();

        cellCoord = Optional.of(this.spreadsheetFactory.getCellCoordinate(cellCoordString));

        if (cellCoord.isPresent()) {
            this.dependencyManager.removeDependantCell(cellCoord.get());

            ICellContent cellContent = this.spreadsheetFactory.getCellContent(cellContentString,
                    this.cellContentFactory);

            if (cellContent instanceof FormulaContent) {
                this.expressionGenerator.reset();
                this.expressionEvaluator.reset();
                Optional<List<IFormulaElement>> elements;

                this.expressionGenerator.generate(cellContentString.substring(1).replaceAll("\\s+", ""));
                elements = Optional.of(this.expressionGenerator.getElements());
                List<ICellCoordinate> cellCoordinates = new LinkedList<ICellCoordinate>();
                for (IFormulaElement element : elements.get()) {
                    if (element instanceof FormulaCellReference) {
                        cellCoordinates.add(((FormulaCellReference) element).getCellCoordinate());
                    }
                }
                this.dependencyManager.addDependantCell(cellCoord.get(), cellCoordinates);

                if (elements.isPresent()) {
                    ((FormulaContent) cellContent).setElements(elements.get());

                    Optional<Double> value;

                    if (!this.dependencyManager.findCircularReferences(cellCoord.get())) {

                        value = Optional.of(this.expressionEvaluator.evaluate(elements.get(), this.spreadsheet));

                    } else {
                        throw new CircularDependencyException();
                    }

                    ((FormulaContent) cellContent).setValue(value);
                }

            }
            this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
            this.updateDependantCells(cellCoord.get());
            System.out.println(String.format("Cell %s edited with content %s", cellCoordString, cellContentString));
        }
    }

    private void updateDependantCells(ICellCoordinate originalCellCoordinate) {
        List<ICellCoordinate> cellCoordinates = this.dependencyManager.getDependantCells(originalCellCoordinate);
        for (ICellCoordinate cellCoordinate : cellCoordinates) {
            FormulaContent cellContent = (FormulaContent) this.spreadsheet.getCell(cellCoordinate).get()
                    .getContentClass();
            Optional<Double> value;
            try {
                this.expressionGenerator.reset();
                this.expressionEvaluator.reset();
                value = Optional.of(this.expressionEvaluator.evaluate(cellContent.getElements(), this.spreadsheet));
            } catch (MultiSpreadsheetException | NumberFormatException e) {
                value = Optional.empty();
            }
            cellContent.setValue(value);
            this.updateDependantCells(cellCoordinate);
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
