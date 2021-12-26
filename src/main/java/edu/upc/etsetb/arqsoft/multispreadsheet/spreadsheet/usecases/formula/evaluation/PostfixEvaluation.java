package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;

public class PostfixEvaluation {

    private IFormulaElementVisitor visitor;
    private ISpreadsheetFormulaFactory formulaFactory;

    private PostfixEvaluation(ISpreadsheetFormulaFactory formulaFactory) {
        this.formulaFactory = formulaFactory;
        this.visitor = this.formulaFactory.getFormulaElementVisitor();
    }

    public static PostfixEvaluation getInstance(ISpreadsheetFormulaFactory formulaFactory) {
        return new PostfixEvaluation(formulaFactory);
    }

    public Double evaluate(List<ISpreadsheetToken> tokens) throws SpreadsheetFormulaException {
        for (ISpreadsheetToken token : tokens) {
            IFormulaElement element = this.formulaFactory.getFormulaElement(token);
            element.accept(this.visitor);
        }

        return visitor.getResult();
    }
}