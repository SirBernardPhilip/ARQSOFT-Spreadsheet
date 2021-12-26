package edu.upc.etsetb.arqsoft.multispreadsheet.usecases;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.CellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions.InvalidCellContentGenerationTypeException;

public abstract class AMultiCellContentFactory {
    public static AMultiCellContentFactory getInstance(String generationType,
            AMultiSpreadsheetFactory spreadsheetFactory)
            throws MultiSpreadsheetException, InvalidCellContentGenerationTypeException {
        switch (generationType.toLowerCase()) {
            case "default":
                return new CellContentFactory(generationType, spreadsheetFactory);
            default:
                throw new InvalidCellContentGenerationTypeException(generationType);
        }
    }

    public abstract ICellContent getCellContent(String cellContentString) throws MultiSpreadsheetException;

}
