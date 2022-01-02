package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.controller.SpreadsheetController;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.Cell;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.CellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.CellCoordinateRange;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.Spreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidNumberCoordinatesException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidStringCoordinatesException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.SpreadsheetExporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.SpreadsheetImporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.SpreadsheetPrinter;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.UserInterface;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetImporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class SpreadsheetFactory extends AMultiSpreadsheetFactory {

    @Override
    public Cell getCell(ICellContent cellContent) {
        return Cell.getInstance(cellContent);
    }

    @Override
    public Spreadsheet getSpreadsheet(AMultiSpreadsheetFactory spreadsheetFactory) {
        return Spreadsheet.getInstance(spreadsheetFactory);
    }

    @Override
    public UserInterface getUserInterface(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return UserInterface.getInstance(spreadsheetFactory, cellContentFactory);
    }

    @Override
    public SpreadsheetController getController(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return SpreadsheetController.getInstance(spreadsheetFactory, cellContentFactory);
    }

    @Override
    public CellCoordinate getCellCoordinate(Integer row, Integer column)
            throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
        return CellCoordinate.getInstance(row, CellCoordinate.getColumnName(column));

    }

    @Override
    public CellCoordinate getCellCoordinate(String cellCoordinateString)
            throws NumberFormatException, InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
        return CellCoordinate.fromString(cellCoordinateString);
    }

    @Override
    public CellCoordinateRange getCellCoordinateRange(ICellCoordinate topLeft, ICellCoordinate bottomRight)
            throws MultiSpreadsheetException {
        return CellCoordinateRange.getInstance(topLeft, bottomRight);
    }

    @Override
    public SpreadsheetExporter getSpreadsheetExporter(AMultiSpreadsheetFactory spreadsheetFactory) {
        return SpreadsheetExporter.getInstance(spreadsheetFactory);
    }

    @Override
    public SpreadsheetPrinter getSpreadsheetPrinter(AMultiSpreadsheetFactory spreadsheetFactory) {
        return SpreadsheetPrinter.getInstance(spreadsheetFactory);
    }

    @Override
    public ICellContent getCellContent(String cellContentString, AMultiCellContentFactory cellContentFactory) {
        return cellContentFactory.getCellContent(cellContentString);

    }

    @Override
    public ISpreadsheetImporter getSpreadsheetImporter(AMultiSpreadsheetFactory spreadsheetFactory) {
        return SpreadsheetImporter.getInstance(spreadsheetFactory);
    }

}
