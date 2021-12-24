package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens;

import java.util.List;

public interface ISpreadsheetTokenizer {

    /**
     * Method that analizes the input String str and gets all the tokens that are
     * present in it.
     * 
     * @param str
     * @throws UnexpectedCharacterException
     */
    public void tokenize(String str) throws SpreadsheetTokenizerException;

    public List<ISpreadsheetToken> getTokens();
}