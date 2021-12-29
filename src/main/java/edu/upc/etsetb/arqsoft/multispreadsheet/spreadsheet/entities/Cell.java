package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICell;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/**
 * Cell implementation
 */
public class Cell implements ICell {

    /**
     * Cell content
     */
    private ICellContent content;

    /**
     * Create cell with content
     * 
     * @param stringContent
     */
    private Cell(ICellContent cellContent) {
        this.content = cellContent;
    }

    public static Cell getInstance(ICellContent cellContent) {
        return new Cell(cellContent);
    }

    /**
     * Obtain the content of the cell
     * 
     * @return String
     */
    @Override
    public String getContent() {
        return this.content.getContent();
    }

    @Override
    public ICellContent getContentClass() {
        return this.content;
    }

    /**
     * Set the content of the cell
     * 
     * @param cellContent
     */
    @Override
    public void setContent(ICellContent cellContent) {
        this.content = cellContent;
    }

    /**
     * 
     * Get the value of the cell as a string
     * 
     * @return String
     */
    @Override
    public String getStringValue() {
        return this.content.getStringValue();
    }

    /**
     *
     * Get the value of the cell as a double
     * 
     * @return Double
     * @throws MultiSpreadsheetException
     */
    @Override
    public Double getNumericalValue() throws MultiSpreadsheetException {
        return this.content.getNumericalValue();
    }

}
