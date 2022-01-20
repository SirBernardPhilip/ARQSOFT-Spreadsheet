package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.NoNumberException;

public class FormulaContent extends ACellContent {

    /**
     * Value, optional to identify the "Err"
     */
    private Optional<Double> value;

    private Optional<String> error;

    private List<IFormulaElement> elements;

    /**
     * Creator of a formula content.
     * 
     * @param content
     */
    private FormulaContent(String content) {
        super(content);
        this.value = Optional.empty();
        this.error = Optional.empty();
        this.elements = new LinkedList<IFormulaElement>();
    }

    /**
     * Get instance
     * 
     * @param content
     * @param formulaFactory
     * @return FormulaContent
     * @throws MultiSpreadsheetFormulaException
     */
    public static FormulaContent getInstance(String content) {
        return new FormulaContent(content);
    }

    public void setElements(List<IFormulaElement> elements) {
        this.elements = elements;
    }

    public List<IFormulaElement> getElements() {
        return this.elements;
    }

    public Boolean isUnset() {
        return !this.error.isPresent() && !this.value.isPresent();
    }

    /**
     * Parse the content to the corresponding value
     * 
     * @param content
     * @throws MultiSpreadsheetException
     */
    public void setValue(Double value) {
        this.value = Optional.of(value);
        this.error = Optional.empty();
    }

    /**
     * Set the error message string and unset the value
     * 
     * @param error
     */
    public void setError(String error) {
        this.value = Optional.empty();
        this.error = Optional.of(error);
    }

    /**
     * 
     * Returns the string value parsed to a number
     * 
     * @return Double
     * @throws InvalidValueException
     */
    @Override
    public Double getNumericalValue() throws NoNumberException {
        if (this.value.isPresent()) {
            return this.value.get();
        } else if (this.error.isPresent()) {
            throw new NoNumberException(String.format("The formula is not valid because of %s", this.error.get()));
        } else {
            throw new NoNumberException("The formula has not been calculated yet!");
        }
    }

    /**
     * 
     * Returns the string value or "Err" if not computable
     * 
     * @return String
     */
    @Override
    public String getStringValue() {
        if (this.value.isPresent()) {
            return String.valueOf(this.value.get());
        } else if (this.error.isPresent()) {
            return this.error.get();
        } else {
            return "Uncalculated";
        }
    }

    /**
     * Return true if the cell content is a formula content
     * 
     * @return boolean
     */
    @Override
    public boolean isFormulaContent() {
        return true;
    }
}
