package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.CellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetPrinter;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class SpreadsheetPrinter implements ISpreadsheetPrinter {
    AMultiSpreadsheetFactory spreadsheetFactory;

    protected SpreadsheetPrinter(AMultiSpreadsheetFactory spreadsheetFactory) {
        this.spreadsheetFactory = spreadsheetFactory;
    }

    public static SpreadsheetPrinter getInstance(AMultiSpreadsheetFactory spreadsheetFactory) {
        return new SpreadsheetPrinter(spreadsheetFactory);
    }

    /**
     * Method that obtains all the values as a matrix
     * It also puts the names of the columns and rows
     * 
     * @param spreadsheet
     * @return String[][]
     * @throws MultiSpreadsheetException
     */
    private String[][] getAllValuesMatrix(ISpreadsheet spreadsheet)
            throws MultiSpreadsheetException {
        ICellCoordinate borderCoordinate = spreadsheet.getBorderCoordinate();
        Integer borderRow = borderCoordinate.getRow();
        Integer borderCol = borderCoordinate.getColumnNumber();
        String contentTable[][] = new String[borderRow + 1][borderCol + 1];
        contentTable[0][0] = " ";
        for (int i = 1; i < borderRow + 1; ++i) {
            contentTable[i][0] = String.format("%2d", i);
            for (int j = 1; j < borderCol + 1; ++j) {
                String columnName = CellCoordinate.getColumnName(j);
                contentTable[0][j] = columnName;
                ICellCoordinate cellCoordinate = this.spreadsheetFactory.getCellCoordinate(i, j);
                contentTable[i][j] = spreadsheet.getCellStringValue(cellCoordinate);
            }
        }

        return contentTable;
    }

    /**
     ** Method that prints the spreadsheet
     * 
     * @param spreadsheet
     * @throws MultiSpreadsheetException
     */
    @Override
    public void printSpreadsheet(ISpreadsheet spreadsheet)
            throws MultiSpreadsheetException {

        // work with.
        String cellContents[][] = this.getAllValuesMatrix(spreadsheet);
        for (int i = 0; i < cellContents.length; ++i) {
            for (int j = 0; j < cellContents[0].length; ++j) {
                System.out.print(String.format("%20.20s", cellContents[i][j]));
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}
