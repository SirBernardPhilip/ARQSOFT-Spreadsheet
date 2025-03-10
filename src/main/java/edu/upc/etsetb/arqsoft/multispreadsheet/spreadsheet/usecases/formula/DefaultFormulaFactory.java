package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.CellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ICellDependencyManager;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.ISpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ESpreadsheetTokenType;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenInfo;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenizer;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaCellReference;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaDivideOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaFunctionArgumentSeparator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaMaxFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaMinFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaMultiplyOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaNumeric;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaPromedioFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaSubtractOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaSumOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.FormulaSumaFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.PostfixEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.PostfixEvaluatorVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.postfix.SpreadsheetPostfixGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.SpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions.UnexpectedTokenException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.SpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.SpreadsheetTokenInfo;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.tokens.SpreadsheetTokenizer;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

public class DefaultFormulaFactory implements ISpreadsheetFormulaFactory {

    private AMultiSpreadsheetFactory spreadsheetFactory;

    public DefaultFormulaFactory(AMultiSpreadsheetFactory spreadsheetFactory) {
        this.spreadsheetFactory = spreadsheetFactory;
    }

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
    public ISpreadsheetExpressionGenerator getSpreadsheetExpressionGenerator(
            ISpreadsheetFormulaFactory formulaFactory) {
        return SpreadsheetPostfixGenerator.getInstance(formulaFactory);

    }

    @Override
    public List<IFormulaElement> getFormulaElements(ISpreadsheetToken token)
            throws MultiSpreadsheetException {
        if (token.isNumber()) {
            return Arrays.asList(FormulaNumeric.getInstance(Double.parseDouble(token.getTokenString())));
        } else if (token.isOperator()) {
            return Arrays.asList(this.getOperator(token));
        } else if (token.isFunction()) {
            return Arrays.asList(this.getFunction(token));
        } else if (token.isCell()) {
            ICellCoordinate cellCoordinate = this.spreadsheetFactory.getCellCoordinate(token.getTokenString());

            return Arrays.asList(FormulaCellReference
                    .getInstance(cellCoordinate));
        } else if (token.isCellRange()) {
            ICellCoordinate topLeft = this.spreadsheetFactory
                    .getCellCoordinate(token.getTokenString().split(":", 0)[0]);
            ICellCoordinate bottomRight = this.spreadsheetFactory
                    .getCellCoordinate(token.getTokenString().split(":", 0)[1]);
            ArrayList<ICellCoordinate> allCoords = CellCoordinate.getAllCoordinates(topLeft, bottomRight);
            List<IFormulaElement> elements = new LinkedList<IFormulaElement>();
            Boolean first = true;
            for (ICellCoordinate coord : allCoords) {
                elements.add(FormulaCellReference.getInstance(coord));
                if (!first) {
                    elements.add(FormulaFunctionArgumentSeparator.getInstance());
                }
                first = false;
            }
            return elements;
        } else if (token.isSemicolon()) {
            return Arrays.asList(FormulaFunctionArgumentSeparator.getInstance());
        } else {
            throw new UnexpectedTokenException(token.getTokenString());
        }
    }

    private IFormulaOperator getOperator(ISpreadsheetToken token) throws UnexpectedTokenException {
        if (token.isMult()) {
            return FormulaMultiplyOperator.getInstance();
        } else if (token.isDiv()) {
            return FormulaDivideOperator.getInstance();
        } else if (token.isPlus()) {
            return FormulaSumOperator.getInstance();
        } else if (token.isMinus()) {
            return FormulaSubtractOperator.getInstance();
        } else {
            throw new UnexpectedTokenException(token.getTokenString());

        }
    }

    private IFormulaFunction getFunction(ISpreadsheetToken token) throws UnexpectedTokenException {
        switch (token.getTokenString()) {
            case "PROMEDIO":
                return FormulaPromedioFunction.getInstance();
            case "SUMA":
                return FormulaSumaFunction.getInstance();
            case "MIN":
                return FormulaMinFunction.getInstance();
            case "MAX":
                return FormulaMaxFunction.getInstance();
            default:
                throw new UnexpectedTokenException(token.getTokenString());
        }

    }

    @Override
    public IFormulaElementVisitor getFormulaElementVisitor() {
        return PostfixEvaluatorVisitor.getInstance();
    }

    @Override
    public IExpressionEvaluator getExpressionEvaluator(ISpreadsheetFormulaFactory formulaFactory) {
        return PostfixEvaluator.getInstance(formulaFactory);
    }

    @Override
    public ICellDependencyManager getDependencyManager() {
        return CellDependencyManager.getInstance();
    }

}
