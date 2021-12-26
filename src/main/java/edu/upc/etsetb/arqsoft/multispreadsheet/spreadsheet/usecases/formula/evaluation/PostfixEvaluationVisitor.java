package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.Stack;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperand;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.exceptions.NoValueComputedException;

public class PostfixEvaluationVisitor implements IFormulaElementVisitor {

    private Stack<Double> stack;

    private PostfixEvaluationVisitor() {
        this.stack = new Stack<Double>();
    }

    public static PostfixEvaluationVisitor getInstance() {
        return new PostfixEvaluationVisitor();
    }

    @Override
    public void visit(IFormulaOperator operator) {
    }

    @Override
    public void visit(IFormulaOperand operator) {
    }

    @Override
    public Double getResult() throws NoValueComputedException {
        if (this.stack.size() != 1) {
            throw new NoValueComputedException();
        } else {
            return this.stack.peek();
        }
    }

}
