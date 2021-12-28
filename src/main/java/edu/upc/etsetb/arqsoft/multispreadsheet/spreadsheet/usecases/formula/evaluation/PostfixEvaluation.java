package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;

public class PostfixEvaluation implements IExpressionEvaluator {

    private IFormulaElementVisitor visitor;
    private ISpreadsheetFormulaFactory formulaFactory;

    private PostfixEvaluation(ISpreadsheetFormulaFactory formulaFactory) {
        this.formulaFactory = formulaFactory;
        this.visitor = this.formulaFactory.getFormulaElementVisitor();
    }

    public static PostfixEvaluation getInstance(ISpreadsheetFormulaFactory formulaFactory) {
        return new PostfixEvaluation(formulaFactory);
    }

    public Double evaluate(List<IFormulaElement> elements) throws MultiSpreadsheetException {
        for (IFormulaElement element : elements) {
            element.accept(this.visitor);
        }

        return visitor.getResult();
    }
}