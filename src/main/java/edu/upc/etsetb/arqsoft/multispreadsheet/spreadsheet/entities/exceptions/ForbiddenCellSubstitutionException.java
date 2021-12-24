package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public class ForbiddenCellSubstitutionException extends MultiSpreadsheetException {
    public ForbiddenCellSubstitutionException(ICellCoordinate cellCoordinate) {
        super(String.format("Tried to put a cell in coordinates %s but it already had a cell",
                cellCoordinate.toString()));
    }
}