package edu.upc.etsetb.arqsoft.multispreadsheet.entities;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;

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

    /**
     * Return true if the cell content is a formula content
     * 
     * @return boolean
     */
    public boolean isFormulaContent();

    public void setElements(List<IFormulaElement> list);

    public void setError(String string);

    public Boolean isUnset();

    public List<IFormulaElement> getElements();

    public void setValue(Double evaluate);
}
