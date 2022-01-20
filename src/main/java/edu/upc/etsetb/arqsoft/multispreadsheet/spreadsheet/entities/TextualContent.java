package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.ArrayList;
import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.NoNumberException;

public class TextualContent extends ACellContent {

    /**
     * String value
     */
    private String value;

    /**
     * Constructor of textual content
     * 
     * @param content
     */
    private TextualContent(String content) {
        super(content);
        this.setValue(content);
    }

    /**
     * Get instance
     * 
     * @param content
     * @return TextualContent
     */
    public static TextualContent getInstance(String content) {
        return new TextualContent(content);
    }

    /**
     * 
     * Set the content to the value
     * 
     * @param content
     */
    protected final void setValue(String content) {
        this.value = content;
    }

    /**
     * 
     * Returns the value as a number
     * 
     * @return Double
     * @throws NumberFormatException
     */
    @Override
    public Double getNumericalValue() throws NoNumberException {
        if (this.value.trim() == "") {
            return 0d;
        } else {
            try {
                return Double.parseDouble(this.value);
            } catch (NumberFormatException e) {
                throw new NoNumberException(this.value);
            }
        }
    }

    /**
     * 
     * Returns the value as a string
     * 
     * @return String
     */
    @Override
    public String getStringValue() {
        return this.value;
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
