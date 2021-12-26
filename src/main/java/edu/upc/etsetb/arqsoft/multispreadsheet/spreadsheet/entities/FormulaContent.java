package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.List;
import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidValueException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;

public class FormulaContent extends ACellContent {

    /**
     * Value, optional to identify the "Err"
     */
    private Optional<Double> value;

    /**
     * Formula factory to get the postfix generatior
     */
    private List<IFormulaElement> elements;

    /**
     * Creator of a formula content.
     * 
     * @param content
     */
    private FormulaContent(String content) {
        super(content);
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

    /**
     * Parse the content to the corresponding value
     * 
     * @param content
     * @throws MultiSpreadsheetException
     */
    public void setValue(List<IFormulaElement> elements, Optional<Double> value) throws MultiSpreadsheetException {
        this.elements = elements;
        this.value = value;

    }

    public List<IFormulaElement> getElements() {
        return this.elements;
    }

    /**
     * 
     * Returns the string value parsed to a number
     * 
     * @return Double
     * @throws InvalidValueException
     */
    @Override
    public Double getNumericalValue() throws InvalidValueException {
        if (this.value.isPresent()) {
            return this.value.get();
        } else {
            throw new InvalidValueException();
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
            return String.valueOf(this.value);

        } else {
            return "Err";
        }
    }
}
