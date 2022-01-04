package edu.upc.etsetb.arqsoft.multispreadsheet.usecases;

import java.util.Scanner;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetExporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetImporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetPrinter;

/**
 * Abstract class for the controller of the application.
 */
public abstract class AMultiSpreadsheetController {

    protected ISpreadsheet spreadsheet;
    protected ISpreadsheetPrinter spreadsheetPrinter;
    protected ISpreadsheetExporter spreadsheetExporter;
    protected ISpreadsheetImporter spreadsheetImporter;
    protected AMultiSpreadsheetFactory spreadsheetFactory;
    protected AMultiCellContentFactory cellContentFactory;

    /**
     * Constructor for the AMultiSpreadsheetController class.
     * @param spreadsheetFactory
     * @param cellContentFactory
     */

    protected AMultiSpreadsheetController(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        this.spreadsheetFactory = spreadsheetFactory;
        this.cellContentFactory = cellContentFactory;
        this.spreadsheet = spreadsheetFactory.getSpreadsheet(spreadsheetFactory);
        this.spreadsheetPrinter = spreadsheetFactory.getSpreadsheetPrinter(spreadsheetFactory);
        this.spreadsheetExporter = spreadsheetFactory.getSpreadsheetExporter(spreadsheetFactory);
        this.spreadsheetImporter = spreadsheetFactory.getSpreadsheetImporter(spreadsheetFactory);
    }

    /**
     * Method that sets the scanner for the controller.
     * @param scanner
     */
    public abstract void setScanner(Scanner scanner);

    /**
     * Method that calls the appropiate method in the domain to create a new
     * spreadsheet (and optionally save the current one).
     *
     * @param savePath
     */
    public abstract void createSpreadsheet();

    /**
     * Method that calls the appropiate method in the domain to view the current
     * spreadsheet.
     * 
     * @param savePath
     */
    public abstract void viewSpreadsheet();

    /**
     * Method that calls the appropiate method in the domain to export the
     * spreadsheet to a file.
     *
     * @param savePath
     */
    public abstract void saveSpreadsheet(String savePath);

    /**
     * Method that calls the appropiate method in the domain to create a new
     * spreadsheet from a file (and optionally save the current one).
     * 
     * @param loadPath
     */
    public abstract void loadSpreadsheet(String loadPath);

    /**
     * Method that calls the appropiate method in the domain to edit the contents of
     * a cell.
     * 
     * @param cellCoord
     * @param cellContent
     */
    public abstract void editCell(String cellCoord, String cellContent);
}
