package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.List;
import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidValueException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;

public class FormulaContent extends ACellContent {

    /**
     * Value, optional to identify the "Err"
     */
    private Optional<Double> value;

    /**
     * Formula factory to get the postfix generatior
     */
    private ISpreadsheetExpressionGenerator expressionGenerator;
    private List<ISpreadsheetToken> tokens;

    /**
     * Creator of a formula content.
     * 
     * @param content
     * @throws SpreadsheetFormulaException
     */
    private FormulaContent(String content, ISpreadsheetExpressionGenerator expressionGenerator)
            throws SpreadsheetFormulaException {
        super(content);
        this.expressionGenerator = expressionGenerator;
        this.setValue(content);
    }

    /**
     * Get instance
     * 
     * @param content
     * @param formulaFactory
     * @return FormulaContent
     * @throws MultiSpreadsheetFormulaException
     */
    public static FormulaContent getInstance(String content, ISpreadsheetExpressionGenerator expressionGenerator)
            throws SpreadsheetFormulaException {
        return new FormulaContent(content, expressionGenerator);
    }

    /**
     * Parse the content to the corresponding value
     * 
     * @param content
     * @throws MultiSpreadsheetFormulaException
     */
    protected final void setValue(String content) throws SpreadsheetFormulaException {
        this.expressionGenerator.generate(content.substring(1).replaceAll("\\s+", ""));
        this.tokens = this.expressionGenerator.getTokens();
        System.out.println(this.tokens);
        this.value = Optional.empty();
        // throw new UnsupportedOperationException("FormulaContent::setValue. Not
        // implemented yet");
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
