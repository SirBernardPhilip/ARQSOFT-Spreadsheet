package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ESpreadsheetTokenType;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenInfo;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenizer;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.exceptions.UnexpectedCharacterException;

public class SpreadsheetTokenizer implements ISpreadsheetTokenizer {

    private ISpreadsheetFormulaFactory formulaFactory;

    private LinkedList<ISpreadsheetTokenInfo> tokenInfos;
    private LinkedList<ISpreadsheetToken> tokens;

    private SpreadsheetTokenizer(ISpreadsheetFormulaFactory formulaFactory) {
        this.formulaFactory = formulaFactory;
        this.tokens = new LinkedList<ISpreadsheetToken>();
        this.initializeTokenInfos();
    }

    public static SpreadsheetTokenizer getInstance(ISpreadsheetFormulaFactory formulaFactory) {
        SpreadsheetTokenizer tokenizer = new SpreadsheetTokenizer(formulaFactory);
        return tokenizer;
    }

    private void initializeTokenInfos() {
        this.tokenInfos = new LinkedList<ISpreadsheetTokenInfo>();
        for (ESpreadsheetTokenType tokenType : ESpreadsheetTokenType.values()) {
            this.tokenInfos.add(this.formulaFactory
                    .getSpreadsheetTokenInfo(Pattern.compile("^(" + tokenType.getRegex() + ")"), tokenType));
        }
    }

    /**
     * Method that analizes the input String str and gets all the tokens that are
     * present in it.
     * 
     * @param str
     * @throws UnexpectedCharacterException
     */
    @Override
    public void tokenize(String str) throws UnexpectedCharacterException {
        String s = str.trim();
        tokens.clear();
        while (!s.equals("")) {
            boolean match = false;
            for (ISpreadsheetTokenInfo info : tokenInfos) {
                Matcher m = info.getPattern().matcher(s);
                if (m.find()) {
                    match = true;
                    String tokenString = m.group().trim();
                    s = m.replaceFirst("").trim();
                    tokens.add(this.formulaFactory.getSpreadsheetToken(info.getTokenType(), tokenString));
                    break;
                }
            }
            if (!match)
                throw new UnexpectedCharacterException(s);
        }
    }

    @Override
    public LinkedList<ISpreadsheetToken> getTokens() {
        return this.tokens;
    }

    @Override
    public void reset() {
        this.tokens = new LinkedList<ISpreadsheetToken>();        
    }
}
