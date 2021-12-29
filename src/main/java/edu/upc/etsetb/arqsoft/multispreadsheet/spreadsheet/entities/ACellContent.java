package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidValueException;

/**
 * Abstract cell content class
 * 
 */
public abstract class ACellContent implements ICellContent{
    protected String content;

    /**
     * Create cell content.
     * 
     * @param content
     */
    protected ACellContent(String content) {
        this.content = content;
    }

    /**
     * Obtain the cell content
     * 
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * Obtain the value as a number
     * 
     * @return Double
     * @throws InvalidValueException
     */
    public abstract Double getNumericalValue() throws InvalidValueException;

    /**
     * Obtain the value as a string
     * 
     * @return String
     */
    public abstract String getStringValue();
}
