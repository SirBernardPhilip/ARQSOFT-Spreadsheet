package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;

public interface ISpreadsheetSyntaxChecker {

    public void check(List<ISpreadsheetToken> tokens) throws SpreadsheetSyntaxException;

    public void reset();


    public List<ISpreadsheetToken> getTokens();
}