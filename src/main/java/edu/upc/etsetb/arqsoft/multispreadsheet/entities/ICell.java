package edu.upc.etsetb.arqsoft.multispreadsheet.entities;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Interface for the cells of every kind of spreadsheet.
 * 
 */
public interface ICell {

    /**
     * Obtain the content of the cell
     * 
     * @return String
     */
    public String getContent();

    /**
     * Return the content object.
     * 
     * @return ICellContent
     */
    public ICellContent getContentClass();

    /**
     * Set the content of the cell
     * 
     * @param cellContent
     */
    public void setContent(ICellContent cellContent);

    /**
     * 
     * Get the value of the cell as a string
     * 
     * @return String
     */
    public String getStringValue();

    /**
     *
     * Get the value of the cell as a double
     * 
     * @return Double
     * @throws MultiSpreadsheetException
     */
    public Double getNumericalValue() throws MultiSpreadsheetException;
}
