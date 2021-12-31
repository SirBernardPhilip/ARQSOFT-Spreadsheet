package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.List;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IExpressionEvaluator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElement;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;

public class PostfixEvaluator implements IExpressionEvaluator {

    private IFormulaElementVisitor visitor;
    private ISpreadsheetFormulaFactory formulaFactory;

    private PostfixEvaluator(ISpreadsheetFormulaFactory formulaFactory) {
        this.formulaFactory = formulaFactory;
        this.visitor = this.formulaFactory.getFormulaElementVisitor();
    }

    public static PostfixEvaluator getInstance(ISpreadsheetFormulaFactory formulaFactory) {
        return new PostfixEvaluator(formulaFactory);
    }

    public Double evaluate(List<IFormulaElement> elements, ISpreadsheet spreadsheet)
            throws MultiSpreadsheetException, NumberFormatException {
        for (IFormulaElement element : elements) {
            if (element instanceof FormulaCellReference) {
                FormulaCellReference cellReferenceElement = (FormulaCellReference) element;
                cellReferenceElement
                        .setValue(spreadsheet.getCellNumericalValue(cellReferenceElement.getCellCoordinate()));

            }
            element.accept(this.visitor);
        }

        return this.visitor.getResult();
    }

    @Override
    public void reset() {
        this.visitor.reset();
    }
}