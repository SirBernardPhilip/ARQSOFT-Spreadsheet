package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula;

import java.util.regex.Pattern;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidFormulaTypeException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.ISpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ESpreadsheetTokenType;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenInfo;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenizer;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.DefaultFormulaFactory;

public interface ISpreadsheetFormulaFactory{
    public static ISpreadsheetFormulaFactory getInstance(String formulaType) throws InvalidFormulaTypeException {
        switch (formulaType.toLowerCase()) {
            case "default":
                return new DefaultFormulaFactory();
            default:
                throw new InvalidFormulaTypeException(formulaType);
        }
    }

    public ISpreadsheetToken getSpreadsheetToken(ESpreadsheetTokenType tokenType, String tokenString);

    public ISpreadsheetTokenInfo getSpreadsheetTokenInfo(Pattern tokenString, ESpreadsheetTokenType tokenType);

    public ISpreadsheetTokenizer getSpreadsheetTokenizer(ISpreadsheetFormulaFactory formulaFactory);

    public ISpreadsheetSyntaxChecker getSpreadsheetSyntaxChecker();

    public ISpreadsheetExpressionGenerator getSpreadsheetExpressionGenerator(ISpreadsheetFormulaFactory formulaFactory);
}
