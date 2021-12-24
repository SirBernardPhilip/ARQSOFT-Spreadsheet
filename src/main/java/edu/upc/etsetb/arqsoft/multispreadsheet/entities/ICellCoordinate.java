package edu.upc.etsetb.arqsoft.multispreadsheet.entities;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;


/**
 * Interface for the cell coordinates of every kind of spreadsheet.
 * 
 */
public interface ICellCoordinate {

    /**
     * To string method, useful for debugging
     * 
     * @return String
     */
    public String toString();

    /**
     * Since we want to use this class as keys for a map we need to define our
     * equals and hashcode methods properly. I tried using the wizard but it just
     * calls super(), which is not what I want.
     * 
     * @return int
     */
    @Override
    public int hashCode();

    /**
     * Needed to use this class as a key in a hash map
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj);

    /**
     * @return Integer
     */
    public Integer getRow();

    /**
     * @return String
     */
    public String getColumn();

    /**
     * Non-static method of the column number conversion
     * 
     * @return Integer
     * @throws MultiSpreadsheetException
     */
    public Integer getColumnNumber() throws MultiSpreadsheetException;

}
