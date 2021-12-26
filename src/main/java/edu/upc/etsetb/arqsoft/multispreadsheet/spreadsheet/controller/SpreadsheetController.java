package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoWriteAccessException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.FormulaContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidFormulaTypeException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
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

    protected SpreadsheetController(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) throws InvalidFormulaTypeException {
        super(spreadsheetFactory, cellContentFactory);
        ISpreadsheetFormulaFactory formulaFactory = ISpreadsheetFormulaFactory.getInstance("default",
                spreadsheetFactory);
        this.expressionGenerator = formulaFactory.getSpreadsheetExpressionGenerator(formulaFactory);
        this.expressionEvaluator = formulaFactory.getExpressionEvaluator(formulaFactory);

    }

    /**
     * Static method for creating a SpreadsheetController object for using the
     * Spreadsheet
     * 
     * @param spreadsheet
     * @return SpreadsheetController
     * @throws InvalidFormulaTypeException
     */
    public static SpreadsheetController getInstance(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) throws InvalidFormulaTypeException {
        return new SpreadsheetController(spreadsheetFactory, cellContentFactory);
    }

    /**
     * Method for prompting the user if they want to save the currently open
     * spreadsheet
     */
    private void optionallySaveCurrent() {
        if (!spreadsheet.isEmpty()) {
            boolean save = UserPrompter
                    .promptYesOrNo("There already is a spreadsheet present, do you wish to save it?");
            if (save) {
                String savePath = UserPrompter.promptInfo("Input the path to save the current spreadsheet:");
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

        throw new UnsupportedOperationException("SpreadsheetController::loadSpreadsheet. Not implemented yet");
        // System.out.println(String.format("Spreadsheet loaded from %s", loadPath));
    }

    /**
     * Method that calls the appropiate method in the domain to edit the contents of
     * a cell.
     * 
     * @param cellCoordString
     * @param cellContentString
     * @throws MultiSpreadsheetException
     */
    public void editCell(String cellCoordString, String cellContentString) throws MultiSpreadsheetException {
        Optional<ICellCoordinate> cellCoord = Optional.empty();
        try {
            cellCoord = Optional.of(this.spreadsheetFactory.getCellCoordinate(cellCoordString));
        } catch (MultiSpreadsheetException e) {
            System.out.println("Invalid coordinate.");
        }
        if (cellCoord.isPresent()) {
            ICellContent cellContent = this.spreadsheetFactory.getCellContent(cellContentString,
                    this.cellContentFactory);

            if (cellContent instanceof FormulaContent) {
                this.expressionGenerator.generate(cellContentString.substring(1).replaceAll("\\s+", ""));
                List<IFormulaElement> elements = this.expressionGenerator.getElements(this.spreadsheet);
                Optional<Double> value = this.expressionEvaluator.evaluate(elements);
                ((FormulaContent) cellContent).setValue(elements, value);
            }
            this.spreadsheet.setCellContent(cellCoord.get(), cellContent);

            System.out.println(String.format("Cell %s edited with content %s", cellCoordString, cellContentString));

        }

    }
}
