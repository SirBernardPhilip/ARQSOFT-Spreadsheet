package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;

public interface ISpreadsheetExpressionGenerator {
    public void generate(String input) throws SpreadsheetFormulaException;

    public void reset();

    public List<IFormulaElement> getElements() throws MultiSpreadsheetException;
}