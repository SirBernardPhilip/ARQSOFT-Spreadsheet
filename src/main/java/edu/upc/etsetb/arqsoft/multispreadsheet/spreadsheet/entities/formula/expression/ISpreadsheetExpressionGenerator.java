package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression;



import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;

public interface ISpreadsheetExpressionGenerator {
    public void generate(String input) throws SpreadsheetFormulaException;
    public List<ISpreadsheetToken> getTokens();
}