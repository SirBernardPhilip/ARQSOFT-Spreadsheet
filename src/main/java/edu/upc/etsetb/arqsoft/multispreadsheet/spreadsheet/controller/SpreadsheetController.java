package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map.Entry;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoReadAccessException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoWriteAccessException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ICellDependencyManager;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaCellReference;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.UserPrompter;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetController;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

/**
 * Controller for the spreadsheet application.
 * 
 */
public class SpreadsheetController extends AMultiSpreadsheetController {

    private ISpreadsheetExpressionGenerator expressionGenerator;
    private IExpressionEvaluator expressionEvaluator;
    private ICellDependencyManager dependencyManager;
    private UserPrompter userPrompter;

    /**
     * Create a controller with it's needed dependencies.
     * 
     * @param spreadsheetFactory
     * @param cellContentFactory
     */

    protected SpreadsheetController(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        super(spreadsheetFactory, cellContentFactory);
        ISpreadsheetFormulaFactory formulaFactory = ISpreadsheetFormulaFactory.getInstance(spreadsheetFactory);
        this.expressionGenerator = formulaFactory.getSpreadsheetExpressionGenerator(formulaFactory);
        this.expressionEvaluator = formulaFactory.getExpressionEvaluator(formulaFactory);
        this.dependencyManager = formulaFactory.getDependencyManager();
        userPrompter = UserPrompter.getInstance();

    }

    /**
     * Set the input for prompting the user.
     */
    public void setScanner(Scanner scanner) {
        this.userPrompter.setScanner(scanner);
    }

    /**
     * Static method for creating a SpreadsheetController object for using the
     * Spreadsheet
     * 
     * @param spreadsheet
     * @return SpreadsheetController
     */
    public static SpreadsheetController getInstance(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return new SpreadsheetController(spreadsheetFactory, cellContentFactory);
    }

    /**
     * Method for prompting the user if they want to save the currently open
     * spreadsheet
     */
    private void optionallySaveCurrent() {
        if (!spreadsheet.isEmpty()) {
            boolean save = this.userPrompter
                    .promptYesOrNo("There already is a spreadsheet present, do you wish to save it?");
            if (save) {
                String savePath = this.userPrompter.promptInfo("Input the path to save the current spreadsheet:");
                this.saveSpreadsheet(savePath);
            }
        }
    }

    /**
     * Method that calls the appropiate method in the domain to create a new
     * spreadsheet (and optionally save the current one).
     *
     * @param savePath
     */
    public void createSpreadsheet() {
        this.optionallySaveCurrent();
        this.spreadsheet = this.spreadsheetFactory.getSpreadsheet(this.spreadsheetFactory);
        this.dependencyManager.reset();
    }

    /**
     * Method that calls the appropiate method in the domain to view the current
     * spreadsheet.
     * 
     * @param savePath
     */
    public void viewSpreadsheet() {
        try {
            this.spreadsheetPrinter.printSpreadsheet(this.spreadsheet);
        } catch (MultiSpreadsheetException e) {
            System.out.println("Oh no! Something went wrong, this should not have happened!");
        }
    }

    /**
     * Method that calls the appropiate method in the domain to export the
     * spreadsheet to a file.
     *
     * @param savePath
     */
    public void saveSpreadsheet(String savePath) {
        try {
            this.spreadsheetExporter.exportSpreadsheet(this.spreadsheet, savePath);
        } catch (MultiSpreadsheetException | NoWriteAccessException |

                IOException e) {
            System.out.println(
                    String.format("The program could not write into %s. Make sure it is correct.", savePath));
        }
        System.out.println(String.format("Spreadsheet saved at %s", savePath));

    }

    /**
     * Method that calls the appropiate method in the domain to create a new
     * spreadsheet from a file (and optionally save the current one).
     * 
     * @param loadPath
     */
    public void loadSpreadsheet(String loadPath) {
        this.optionallySaveCurrent();
        Optional<Map<ICellCoordinate, String>> contentMap = Optional.empty();
        try {
            contentMap = Optional.of(this.spreadsheetImporter.importSpreadsheet(loadPath));
        } catch (MultiSpreadsheetException | IOException | NoReadAccessException e) {
            System.out.println(
                    String.format("The program could not read from %s. Make sure it is correct.", loadPath));
        }
        if (contentMap.isPresent()) {
            this.spreadsheet = this.spreadsheetFactory.getSpreadsheet(this.spreadsheetFactory);
            this.dependencyManager.reset();
            List<ICellCoordinate> uncalculatedFormulaCoordinates = new LinkedList<ICellCoordinate>();
            // Set all the contents that and generate formulas
            for (Entry<ICellCoordinate, String> entry : contentMap.get().entrySet()) {
                ICellContent cellContent = this.spreadsheetFactory.getCellContent(entry.getValue(),
                        this.cellContentFactory);
                if (cellContent.isFormulaContent()) {
                    Optional<List<IFormulaElement>> elements;
                    try {
                        this.expressionGenerator.reset();
                        this.expressionGenerator.generate(entry.getValue().substring(1).replaceAll("\\s+", ""));
                        elements = Optional.of(this.expressionGenerator.getElements());
                        List<ICellCoordinate> cellCoordinates = new LinkedList<ICellCoordinate>();
                        for (IFormulaElement element : elements.get()) {
                            if (element.isCellReference()) {
                                cellCoordinates.add(((IFormulaCellReference) element).getCellCoordinate());
                            }
                        }
                        this.dependencyManager.addDependantCell(entry.getKey(), cellCoordinates);
                        cellContent.setElements(elements.get());
                        uncalculatedFormulaCoordinates.add(entry.getKey());
                    } catch (MultiSpreadsheetException e) {
                        cellContent.setError("Expr. Err.");
                    }
                }
                this.spreadsheet.setCellContent(entry.getKey(), cellContent);
            }
            while (uncalculatedFormulaCoordinates.size() != 0) {
                List<ICellCoordinate> toRemove = new LinkedList<ICellCoordinate>();
                for (ICellCoordinate formulaCoord : uncalculatedFormulaCoordinates) {
                    ICellContent formulaContent = this.spreadsheet.getCell(formulaCoord).get().getContentClass();
                    if (!this.dependencyManager.findCircularReferences(formulaCoord)) {
                        List<ICellCoordinate> dependantCells = this.dependencyManager.getDependantOnCells(formulaCoord);
                        Boolean allDependantsComputed = true;
                        for (ICellCoordinate dependantCell : dependantCells) {
                            ICellContent cellContent = this.spreadsheet.getCell(dependantCell).get().getContentClass();
                            if ((cellContent.isFormulaContent()) && cellContent.isUnset()) {
                                allDependantsComputed = false;
                            }
                        }
                        if (allDependantsComputed) {
                            toRemove.add(formulaCoord);
                            try {
                                this.expressionEvaluator.reset();
                                formulaContent
                                        .setValue(this.expressionEvaluator.evaluate(formulaContent.getElements(),
                                                this.spreadsheet));
                            } catch (MultiSpreadsheetException | NumberFormatException e) {
                                formulaContent.setError("Eval. Err.");
                            }
                        }
                    } else {
                        toRemove.add(formulaCoord);
                        formulaContent.setError("Circ. Ref. Err.");
                    }
                }
                for (ICellCoordinate removeCoord : toRemove) {
                    uncalculatedFormulaCoordinates.remove(removeCoord);
                }
            }
            System.out.println(String.format("Spreadsheet loaded from %s", loadPath));
        }
    }

    /**
     * Update the cells that depend on the given cell.
     * 
     * @param originalCellCoordinate
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

    /**
     * Method that calls the appropiate method in the domain to edit the contents of
     * a cell.
     * 
     * @param cellCoordString
     * @param cellContentString
     */
    public void editCell(String cellCoordString, String cellContentString) {
        Optional<ICellCoordinate> cellCoord = Optional.empty();
        try {
            cellCoord = Optional.of(this.spreadsheetFactory.getCellCoordinate(cellCoordString));
        } catch (MultiSpreadsheetException e) {
            System.out.println("Invalid coordinate.");
        }
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
                    elements = Optional.of(this.expressionGenerator.getElements());
                    List<ICellCoordinate> cellCoordinates = new LinkedList<ICellCoordinate>();
                    for (IFormulaElement element : elements.get()) {
                        if (element.isCellReference()) {
                            cellCoordinates.add(((IFormulaCellReference) element).getCellCoordinate());
                        }
                    }
                    this.dependencyManager.addDependantCell(cellCoord.get(), cellCoordinates);
                } catch (MultiSpreadsheetException e) {
                    elements = Optional.empty();
                    cellContent.setError("Expr. Err.");
                }

                if (elements.isPresent()) {
                    cellContent.setElements(elements.get());

                    if (!this.dependencyManager.findCircularReferences(cellCoord.get())) {
                        try {
                            cellContent
                                    .setValue(this.expressionEvaluator.evaluate(elements.get(), this.spreadsheet));

                        } catch (MultiSpreadsheetException | NumberFormatException e) {
                            cellContent.setError("Eval. Err.");
                        }
                    } else {
                        cellContent.setError("Circ. Ref. Err.");

                        System.out.println("A circular reference was found");
                    }
                }

            }
            this.spreadsheet.setCellContent(cellCoord.get(), cellContent);
            this.updateDependantCells(cellCoord.get());
            System.out.println(String.format("Cell %s edited with content %s", cellCoordString, cellContentString));

        }
    }

    @Override
    public void viewCellContent(String cellCoordString) {
        Optional<ICellCoordinate> cellCoord = Optional.empty();
        try {
            cellCoord = Optional.of(this.spreadsheetFactory.getCellCoordinate(cellCoordString));
        } catch (MultiSpreadsheetException e) {
            System.out.println("Invalid coordinate.");
        }
        if (cellCoord.isPresent()) {
            System.out.print(cellCoordString + ": ");
            String content = this.spreadsheet.getCellContent(cellCoord.get());
            if (content.equals("")) {
                System.out.println("<Empty>");
            } else {
                System.out.println(this.spreadsheet.getCellContent(cellCoord.get()));
            }
        }
    }
}
