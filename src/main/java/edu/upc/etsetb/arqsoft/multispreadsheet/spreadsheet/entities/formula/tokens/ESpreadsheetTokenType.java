package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens;

public enum ESpreadsheetTokenType {
    OPERATOR("[+\\-*/]"),
    RANGE("[A-Z]+[1-9]+:[A-Z]+[1-9]+"),
    CELL("[A-Z]+[1-9]+"),
    NUM("([0-9]*[.])?[0-9]+"),
    OPENING_PAR("\\("),
    CLOSING_PAR("\\)"),
    SEMICOLON(";"),
    FUNCTION("SUMA|MIN|MAX|PROMEDIO");

    private String regex;

    private ESpreadsheetTokenType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
