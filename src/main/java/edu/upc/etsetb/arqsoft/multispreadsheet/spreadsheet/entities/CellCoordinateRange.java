package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinateRange;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidRangeCoordinatesException;

public class CellCoordinateRange implements ICellCoordinateRange {
    /**
     * Top left coordinate
     */
    private final ICellCoordinate topLeft;

    /**
     * Bottom right coordinate
     */
    private final ICellCoordinate bottomRight;

    /**
     * Creator for coordinate range
     * 
     * @param topLeft
     * @param bottomRight
     * @throws MultiSpreadsheetException
     */
    private CellCoordinateRange(ICellCoordinate topLeft, ICellCoordinate bottomRight)
            throws MultiSpreadsheetException {
        if (!CellCoordinateRange.isValidRange(topLeft, bottomRight)) {
            throw new InvalidRangeCoordinatesException(topLeft.toString(), bottomRight.toString());
        }
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * Get instance
     * 
     * @param topLeft
     * @param bottomRight
     * @return ICellCoordinateRange
     * @throws MultiSpreadsheetException
     */
    public static CellCoordinateRange getInstance(ICellCoordinate topLeft, ICellCoordinate bottomRight)
            throws MultiSpreadsheetException {
        return new CellCoordinateRange(topLeft, bottomRight);
    }

    /**
     * 
     * Check that the coordinate range is valid
     * 
     * @param topLeft
     * @param bottomRight
     * @return boolean
     * @throws MultiSpreadsheetException
     */
    private static boolean isValidRange(ICellCoordinate topLeft, ICellCoordinate bottomRight)
            throws MultiSpreadsheetException {
        return topLeft.getRow() <= bottomRight.getRow() && topLeft.getColumnNumber() <= bottomRight.getColumnNumber();
    }

    /**
     * 
     * Obtain the top left coordinate
     * 
     * @return ICellCoordinate
     */
    @Override
    public ICellCoordinate getTopLeft() {
        return topLeft;
    }

    /**
     * 
     * Obtain the bottom right coordinate
     * 
     * @return ICellCoordinate
     */
    @Override
    public ICellCoordinate getBottomRight() {
        return bottomRight;
    }
}
