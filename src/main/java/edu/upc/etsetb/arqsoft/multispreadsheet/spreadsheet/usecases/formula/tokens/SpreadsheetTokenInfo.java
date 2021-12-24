package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens;

import java.util.regex.Pattern;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ESpreadsheetTokenType;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenInfo;

public class SpreadsheetTokenInfo implements ISpreadsheetTokenInfo {
    private final Pattern pattern;
    private final ESpreadsheetTokenType tokenType;

    private SpreadsheetTokenInfo(Pattern pattern, ESpreadsheetTokenType tokenType) {
        this.pattern = pattern;
        this.tokenType = tokenType;
    }

    public static SpreadsheetTokenInfo getInstance(Pattern pattern, ESpreadsheetTokenType tokenType) {
        return new SpreadsheetTokenInfo(pattern, tokenType);
    }

    @Override
    public Pattern getPattern() {
        return this.pattern;
    }

    @Override
    public ESpreadsheetTokenType getTokenType() {
        return this.tokenType;
    }
}
