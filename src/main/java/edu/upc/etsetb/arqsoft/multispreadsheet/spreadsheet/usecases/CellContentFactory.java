package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.ACellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.FormulaContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.NumericalContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.TextualContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidFormulaTypeException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class CellContentFactory extends AMultiCellContentFactory {

    private ISpreadsheetFormulaFactory formulaFactory;
    private ISpreadsheetExpressionGenerator expressionGenerator;

    public CellContentFactory(String generationType, AMultiSpreadsheetFactory spreadsheetFactory)
            throws InvalidFormulaTypeException {
        this.formulaFactory = ISpreadsheetFormulaFactory.getInstance(generationType, spreadsheetFactory);
        this.expressionGenerator = this.formulaFactory.getSpreadsheetExpressionGenerator(formulaFactory);
    }

    /**
     * Method that returns the needed CellContent for the given content
     * 
     * @param content
     * @return CellContent
     * @throws InvalidFormulaTypeException
     * @throws MultiSpreadsheetFormulaException
     */
    public ACellContent getCellContent(String content)
            throws InvalidFormulaTypeException, SpreadsheetFormulaException {
        if (content.length() >= 2 && content.charAt(0) == '=') {
            return FormulaContent.getInstance(content, this.expressionGenerator);
        } else {
            try {
                return NumericalContent.getInstance(content);
            } catch (NumberFormatException e) {
                return TextualContent.getInstance(content);
            }
        }
    }
}