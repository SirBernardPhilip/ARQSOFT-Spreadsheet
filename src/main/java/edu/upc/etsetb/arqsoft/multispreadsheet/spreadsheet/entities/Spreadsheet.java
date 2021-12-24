package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICell;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.ForbiddenCellSubstitutionException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidNumberCoordinatesException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidStringCoordinatesException;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class Spreadsheet implements ISpreadsheet {

    private AMultiSpreadsheetFactory spreadsheetFactory;

    private HashMap<ICellCoordinate, ICell> cells;

    protected Spreadsheet(AMultiSpreadsheetFactory spreadsheetFactory) {
        this.spreadsheetFactory = spreadsheetFactory;
        this.cells = new HashMap<ICellCoordinate, ICell>();
    }

    public static Spreadsheet getInstance(AMultiSpreadsheetFactory spreadsheetFactory) {

        return new Spreadsheet(spreadsheetFactory);
    }

    /**
     * Checks if the spreadsheet is empty
     * 
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return this.cells.size() == 0;
    }

    /**
     * Obtain a cell in the given coordinate.
     * 
     * @param cellCoordinate
     * @return Cell
     */
    @Override
    public Optional<ICell> getCell(ICellCoordinate cellCoordinate) {
        if (!this.cells.containsKey(cellCoordinate)) {

            return Optional.empty();
        }
        return Optional.of(this.cells.get(cellCoordinate));
    }

    /**
     * Obtain a cell in the given coordinate.
     * 
     * @param cellCoordinate
     * @return Cell
     * @throws ForbiddenCellSubstitutionException
     */
    @Override
    public void putCell(ICellCoordinate cellCoordinate, ICell cell) throws ForbiddenCellSubstitutionException {
        if (this.cells.containsKey(cellCoordinate)) {

            throw new ForbiddenCellSubstitutionException(cellCoordinate);
        }
        this.cells.put(cellCoordinate, cell);
    }

    /**
     * 
     * Get the content of the cell (or "" if the cell has never been edited)
     * 
     * @param cellCoordinate
     * @return String
     */
    @Override
    public String getCellContent(ICellCoordinate cellCoordinate) {
        if (this.cells.containsKey(cellCoordinate)) {

            return this.cells.get(cellCoordinate).getContent();
        }
        return "";
    }

    /**
     * 
     * Gets the cell value as a string (or "" if not present)
     * 
     * @param cellCoordinate
     * @return String
     */
    @Override
    public String getCellStringValue(ICellCoordinate cellCoordinate) {
        if (this.cells.containsKey(cellCoordinate)) {
            return this.cells.get(cellCoordinate).getStringValue();
        }
        return "";
    }

    /**
     * 
     * Gets the cell value as a number (or 0 if not present)
     * 
     * @param cellCoordinate
     * @return Double
     * @throws MultiSpreadsheetException
     */
    @Override
    public Double getCellNumericalValue(ICellCoordinate cellCoordinate) throws MultiSpreadsheetException {
        if (this.cells.containsKey(cellCoordinate)) {
            return this.cells.get(cellCoordinate).getNumericalValue();
        }
        return 0d;
    }

    /**
     * 
     * Get the right-most and bottom-most coordinates of the spreadsheet
     * 
     * @return ICellCoordinate
     * @throws MultiSpreadsheetException
     */
    @Override
    public ICellCoordinate getBorderCoordinate()
            throws MultiSpreadsheetException {

        Set<ICellCoordinate> allCoordinates = this.cells.keySet();
        if (allCoordinates.size() == 0) {
            return spreadsheetFactory.getCellCoordinate(1, "A");
        }
        List<Integer> rows = allCoordinates.stream().map(c -> {
            return c.getRow();
        }).collect(Collectors.toList());
        List<Integer> columns = allCoordinates.stream().map(c -> {
            try {
                return c.getColumnNumber();
            } catch (MultiSpreadsheetException e) {
                // This should not happen is there any way to avoid it?
                return -1;
            }
        }).collect(Collectors.toList());
        Integer maxRow = Collections.max(rows);
        Integer maxColumn = Collections.max(columns);

        return spreadsheetFactory.getCellCoordinate(maxRow, CellCoordinate.getColumnName(maxColumn));
    }

    /**
     * 
     * Edit the content of the cell
     * 
     * @param cellCoordinateString
     * @param cellContent
     * @throws NumberFormatException
     * @throws InvalidStringCoordinatesException
     * @throws InvalidNumberCoordinatesException
     * @throws ForbiddenCellSubstitutionException
     * @throws ForbiddenCellEditionException
     */
    @Override
    public void setCellContent(ICellCoordinate cellCoordinate, ICellContent cellContent) throws ForbiddenCellSubstitutionException {
        Optional<ICell> cell = this.getCell(cellCoordinate);
        if (!cell.isPresent()) {
            ICell newCell = this.spreadsheetFactory.getCell(cellContent);
            this.putCell(cellCoordinate, newCell);
        } else {
            cell.get().setContent(cellContent);
        }
    }

}
