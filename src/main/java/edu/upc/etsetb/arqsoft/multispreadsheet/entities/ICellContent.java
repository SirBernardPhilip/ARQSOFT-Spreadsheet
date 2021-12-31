package edu.upc.etsetb.arqsoft.multispreadsheet.entities;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Interface for the cell contents of every kind of spreadsheet.
 * 
 */
public interface ICellContent {

    /**
     * Obtain the cell content
     * 
     * @return String
     */
    public String getContent();

    /**
     * Obtain the value as a number
     * 
     * @return Double
     * @throws MultiSpreadsheetException
     */
    public Double getNumericalValue() throws MultiSpreadsheetException;

    /**
     * Obtain the value as a string
     * 
     * @return String
     */
    public String getStringValue();
}
