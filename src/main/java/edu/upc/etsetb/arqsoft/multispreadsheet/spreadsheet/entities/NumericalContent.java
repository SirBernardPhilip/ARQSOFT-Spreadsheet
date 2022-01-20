package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.ArrayList;
import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;

public class NumericalContent extends ACellContent {

    /**
     * Double value
     */
    private Double value;

    /**
     * Constructor of numerical content
     * 
     * @param content
     */
    private NumericalContent(String content) {
        super(content);
        this.setValue(content);
    }

    /**
     * Get instance
     * 
     * @param content
     * @return NumericalContent
     */
    public static NumericalContent getInstance(String content) {
        return new NumericalContent(content);
    }

    /**
     * 
     * Parse the content to the corresponding value
     * If it is parseable has already been checked so it will not throw an error
     * 
     * @param content
     */
    protected final void setValue(String content) {
        this.value = Double.parseDouble(content.trim());
    }

    /**
     * Get the value as a number (as is)
     * 
     * @return Double
     * @throws NumberFormatException
     */
    @Override
    public Double getNumericalValue() throws NumberFormatException {
        return this.value;
    }

    /**
     * 
     * Parse the value to a string
     * 
     * @return String
     */
    @Override
    public String getStringValue() {
        return String.valueOf(this.value);
    }

    /**
     * Return true if the cell content is a formula content
     * 
     * @return boolean
     */
    @Override
    public boolean isFormulaContent() {
        return false;
    }

    @Override
    public void setElements(List<IFormulaElement> list) {
        return;
    }

    @Override
    public void setError(String string) {
        return;
    }

    @Override
    public Boolean isUnset() {
        return false;
    }

    @Override
    public List<IFormulaElement> getElements() {
        return new ArrayList<>();
    }

    @Override
    public void setValue(Double evaluate) {
        return;
    }
}
