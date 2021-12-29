package edu.upc.etsetb.arqsoft.multispreadsheet.usecases;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.CellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions.InvalidCellContentGenerationTypeException;

public abstract class AMultiCellContentFactory {
    public static AMultiCellContentFactory getInstance(String generationType)
            throws InvalidCellContentGenerationTypeException {
        switch (generationType.toLowerCase()) {
            case "default":
                return new CellContentFactory();
            default:
                throw new InvalidCellContentGenerationTypeException(generationType);
        }
    }

    public abstract ICellContent getCellContent(String cellContentString);

}
