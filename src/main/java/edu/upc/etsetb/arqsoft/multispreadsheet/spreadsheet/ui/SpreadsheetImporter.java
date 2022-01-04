package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.FileSystemUtils;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoReadAccessException;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.ISpreadsheetImporter;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class SpreadsheetImporter implements ISpreadsheetImporter {

    AMultiSpreadsheetFactory spreadsheetFactory;

    protected SpreadsheetImporter(AMultiSpreadsheetFactory spreadsheetFactory) {
        this.spreadsheetFactory = spreadsheetFactory;
    }

    public static SpreadsheetImporter getInstance(AMultiSpreadsheetFactory spreadsheetFactory) {
        return new SpreadsheetImporter(spreadsheetFactory);
    }

    @Override
    public Map<ICellCoordinate, String> importSpreadsheet(String loadPath)
            throws MultiSpreadsheetException, NoReadAccessException, IOException {
        File file = FileSystemUtils.ensurePathRead(loadPath);
        List<String> rows = FileSystemUtils.readFrom(file);
        Map<ICellCoordinate, String> contentMap = new HashMap<ICellCoordinate, String>();
        int rowNumber = 1;
        for (String row : rows) {
            String[] columns = row.split(";", -1);
            int columnNumber = 1;
            for (String column : columns) {
                if (!column.equals("")) {
                    ICellCoordinate coordinate = this.spreadsheetFactory.getCellCoordinate(rowNumber, columnNumber);
                    contentMap.put(coordinate, column);
                }
                ++columnNumber;
            }
            ++rowNumber;
        }
        return contentMap;
    }
}
