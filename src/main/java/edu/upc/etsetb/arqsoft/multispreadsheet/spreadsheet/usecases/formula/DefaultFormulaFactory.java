package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula;

import java.util.regex.Pattern;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.ISpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ESpreadsheetTokenType;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenInfo;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenizer;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaDivideOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaMultiplyOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaSumOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.postfix.SpreadsheetPostfixGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.SpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.SpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.SpreadsheetTokenInfo;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.SpreadsheetTokenizer;

public class DefaultFormulaFactory implements ISpreadsheetFormulaFactory {
    public ISpreadsheetToken getSpreadsheetToken(ESpreadsheetTokenType tokenType, String tokenString) {
        return SpreadsheetToken.getInstance(tokenType, tokenString);
    }

    public ISpreadsheetTokenInfo getSpreadsheetTokenInfo(Pattern tokenString, ESpreadsheetTokenType tokenType) {
        return SpreadsheetTokenInfo.getInstance(tokenString, tokenType);
    }

    public ISpreadsheetTokenizer getSpreadsheetTokenizer(ISpreadsheetFormulaFactory formulaFactory) {
        return SpreadsheetTokenizer.getInstance(formulaFactory);
    }

    public ISpreadsheetSyntaxChecker getSpreadsheetSyntaxChecker() {
        return SpreadsheetSyntaxChecker.getInstance();
    }

    @Override
    public ISpreadsheetExpressionGenerator getSpreadsheetExpressionGenerator(ISpreadsheetFormulaFactory formulaFactory) {
        return SpreadsheetPostfixGenerator.getInstance(formulaFactory);

    }

    @Override
    public IFormulaElement getFormulaElement(ISpreadsheetToken token) {
        if(token.isMult()){
            return FormulaMultiplyOperator.getInstance();
        } else if (token.isDiv()){
            return FormulaDivideOperator.getInstance();
        } else if(token.isPlus()){
            return FormulaSumOperator.getInstance();
        } else if
    }

    @Override
    public IFormulaElementVisitor getFormulaElementVisitor() {
        return null;
    }

}
