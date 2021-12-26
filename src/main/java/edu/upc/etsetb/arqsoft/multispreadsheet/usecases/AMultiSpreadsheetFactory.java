package edu.upc.etsetb.arqsoft.multispreadsheet.usecases;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICell;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinateRange;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.SpreadsheetFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.AUserInterface;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetExporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetPrinter;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions.InvalidSpreadsheetTypeException;

public abstract class AMultiSpreadsheetFactory {
        public static AMultiSpreadsheetFactory getInstance(String spreadsheetType)
                        throws InvalidSpreadsheetTypeException {
                switch (spreadsheetType.toLowerCase()) {
                        case "spreadsheet":
                                return new SpreadsheetFactory();
                        default:
                                throw new InvalidSpreadsheetTypeException(spreadsheetType);
                }
        }

        public abstract AUserInterface getUserInterface(AMultiSpreadsheetFactory spreadsheetFactory,
                        AMultiCellContentFactory cellContentFactory) throws MultiSpreadsheetException;

        public abstract AMultiSpreadsheetController getController(AMultiSpreadsheetFactory spreadsheetFactory,
                        AMultiCellContentFactory cellContentFactory) throws MultiSpreadsheetException;

        public abstract ICell getCell(ICellContent cellContent);

        public abstract ICellContent getCellContent(String cellContentString,
                        AMultiCellContentFactory cellContentFactory)
                        throws MultiSpreadsheetException;

        public abstract ICellCoordinateRange getCellCoordinateRange(ICellCoordinate topLeft, ICellCoordinate botRight)
                        throws MultiSpreadsheetException;

        public abstract ICellCoordinate getCellCoordinate(Integer row, String column) throws MultiSpreadsheetException;

        public abstract ICellCoordinate getCellCoordinate(String cellCoordinateString) throws MultiSpreadsheetException;

        public abstract ISpreadsheet getSpreadsheet(AMultiSpreadsheetFactory spreadsheetFactory);

        public abstract ISpreadsheetExporter getSpreadsheetExporter(AMultiSpreadsheetFactory spreadsheetFactory);

        public abstract ISpreadsheetPrinter getSpreadsheetPrinter(AMultiSpreadsheetFactory spreadsheetFactory);

}
