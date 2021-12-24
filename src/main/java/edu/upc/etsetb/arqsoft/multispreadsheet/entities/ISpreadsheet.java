package edu.upc.etsetb.arqsoft.multispreadsheet.entities;

import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;


/**
 * Interface for the every kind of spreadsheet.
 * 
 */
public interface ISpreadsheet {

        /**
         * Checks if the spreadsheet is empty
         * 
         * @return boolean
         */
        public boolean isEmpty();

        /**
         * Obtain a cell in the given coordinate.
         * 
         * @param cellCoordinate
         * @return Optional<ICell>
         */
        public Optional<ICell> getCell(ICellCoordinate cellCoordinate);

        /**
         * Put a cell in the given coordinate.
         * 
         * @param cellCoordinate
         * @param cell
         * @return ICell
         * @throws MultiSpreadsheetException
         */
        public void putCell(ICellCoordinate cellCoordinate, ICell cell) throws MultiSpreadsheetException;

        /**
         * 
         * Get the content of the cell (or "" if the cell has never been edited)
         * 
         * @param cellCoordinate
         * @return String
         */
        public String getCellContent(ICellCoordinate cellCoordinate);

        /**
         * 
         * Gets the cell value as a string (or "" if not present)
         * 
         * @param cellCoordinate
         * @return String
         */
        public String getCellStringValue(ICellCoordinate cellCoordinate);

        /**
         * 
         * Gets the cell value as a number (or 0 if not present)
         * 
         * @param cellCoordinate
         * @return Double
         * @throws MultiSpreadsheetException
         */
        public Double getCellNumericalValue(ICellCoordinate cellCoordinate) throws MultiSpreadsheetException;

        /**
         * 
         * Get the right-most and bottom-most coordinates of the spreadsheet
         * 
         * @return ICellCoordinate
         * @throws MultiSpreadsheetException
         */
        public ICellCoordinate getBorderCoordinate()
                        throws MultiSpreadsheetException;

        /**
         * 
         * Set the content of the cell
         * 
         * @param cellCoordinate
         * @param cellContent
         * @throws MultiSpreadsheetException
         */
        public void setCellContent(ICellCoordinate cellCoordinate, ICellContent cellContent)
                        throws MultiSpreadsheetException;
}