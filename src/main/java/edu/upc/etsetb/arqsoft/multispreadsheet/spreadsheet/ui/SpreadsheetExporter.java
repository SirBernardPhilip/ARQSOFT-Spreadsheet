package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui;

import java.io.File;
import java.io.IOException;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.FileSystemUtils;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoWriteAccessException;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetExporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class SpreadsheetExporter implements ISpreadsheetExporter {

    AMultiSpreadsheetFactory spreadsheetFactory;

    protected SpreadsheetExporter(AMultiSpreadsheetFactory spreadsheetFactory) {
        this.spreadsheetFactory = spreadsheetFactory;
    }

    public static SpreadsheetExporter getInstance(AMultiSpreadsheetFactory spreadsheetFactory) {
        return new SpreadsheetExporter(spreadsheetFactory);
    }

    /**
     * Method that gets all the contents of a spreadsheet into a matrix
     * 
     * @param spreadsheet
     * @return String[][]
     * @throws MultiSpreadsheetException
     */
    private String[][] getAllContentsMatrix(ISpreadsheet spreadsheet)
            throws MultiSpreadsheetException {
        ICellCoordinate borderCoordinate = spreadsheet.getBorderCoordinate();
        Integer borderRow = borderCoordinate.getRow();
        Integer borderCol = borderCoordinate.getColumnNumber();
        String contentTable[][] = new String[borderRow][borderCol];

        for (int i = 1; i < borderRow + 1; ++i) {
            for (int j = 1; j < borderCol + 1; ++j) {
                ICellCoordinate cellCoordinate = this.spreadsheetFactory.getCellCoordinate(i, j);
                contentTable[i - 1][j - 1] = spreadsheet.getCellContent(cellCoordinate);
            }
        }

        return contentTable;
    }

    /**
     * 
     * Method that exports a spreadsheet into a file
     * 
     * @param spreadsheet
     * @param savePath
     * @throws NoWriteAccessException
     * @throws IOException
     * @throws MultiSpreadsheetException
     */
    @Override
    public void exportSpreadsheet(ISpreadsheet spreadsheet, String savePath)
            throws NoWriteAccessException,
            IOException, MultiSpreadsheetException {
        File file = FileSystemUtils.ensurePathWrite(savePath);
        String cellContents[][] = this.getAllContentsMatrix(spreadsheet);
        StringBuilder exportString = new StringBuilder();
        for (int i = 0; i < cellContents.length; ++i) {
            for (int j = 0; j < cellContents[0].length; ++j) {
                // Just a print for the time being
                exportString.append(cellContents[i][j]);
                if (j != cellContents.length - 1) {
                    exportString.append(";");
                }
            }
            if (i != cellContents.length - 1) {
                exportString.append("\n");
            }
        }
        FileSystemUtils.writeInto(file, exportString.toString());
    }
}
