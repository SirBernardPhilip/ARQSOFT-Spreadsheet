package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

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

    public void setElements(List<IFormulaElement> elements) {
        this.elements = elements;
    }

    public List<IFormulaElement> getElements() {
        return this.elements;
    }

    /**
     * Parse the content to the corresponding value
     * 
     * @param content
     * @throws MultiSpreadsheetException
     */
    public void setValue(Optional<Double> value) {
        this.value = value;

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
        } else {
            throw new NoNumberException();
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

        } else {
            return "Err";
        }
    }
}
