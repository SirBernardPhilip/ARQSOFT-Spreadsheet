package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens;

import java.util.regex.Pattern;

public interface ISpreadsheetTokenInfo {

    public Pattern getPattern();

    public ESpreadsheetTokenType getTokenType();
}
