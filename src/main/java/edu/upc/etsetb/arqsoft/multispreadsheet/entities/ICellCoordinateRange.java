package edu.upc.etsetb.arqsoft.multispreadsheet.entities;

/**
 * Interface for the cell coordinate range of every kind of spreadsheet.
 * 
 */
public interface ICellCoordinateRange {

    /**
     * 
     * Obtain the top left coordinate
     * 
     * @return ICellCoordinate
     */
    public ICellCoordinate getTopLeft();

    /**
     * 
     * Obtain the bottom right coordinate
     * 
     * @return ICellCoordinate
     */
    public ICellCoordinate getBottomRight();
}
