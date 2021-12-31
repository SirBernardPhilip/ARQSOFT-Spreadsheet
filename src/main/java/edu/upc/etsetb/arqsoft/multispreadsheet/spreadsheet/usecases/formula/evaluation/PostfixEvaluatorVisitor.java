package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation;

import java.util.Arrays;
import java.util.Stack;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaElementVisitor;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunction;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaFunctionArgumentSeparator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperand;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.IFormulaOperator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack.IStackValue;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack.StackDoubleValue;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack.StackListDoubleValue;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.evaluation.exceptions.UnexpectedStackTypeException;

public class PostfixEvaluatorVisitor implements IFormulaElementVisitor {

    private Stack<IStackValue> stack;

    private PostfixEvaluatorVisitor() {
        this.stack = new Stack<IStackValue>();
    }

    public static PostfixEvaluatorVisitor getInstance() {
        return new PostfixEvaluatorVisitor();
    }

    private StackDoubleValue assertIsStackDoubleValue(IStackValue value) throws UnexpectedStackTypeException {
        if (value instanceof StackListDoubleValue) {
            throw new UnexpectedStackTypeException();
        }
        return (StackDoubleValue) value;
    }

    @Override
    public void visit(IFormulaOperator operator) throws MultiSpreadsheetException {
        StackDoubleValue first = assertIsStackDoubleValue(this.stack.pop());

        if (this.stack.empty()) {
            if (operator instanceof FormulaSubtractOperator) {
                this.stack.push(new StackDoubleValue(first.getStackValue() * (-1d)));
            } else {
                this.stack.push(first);
            }
        } else {
            StackDoubleValue second = assertIsStackDoubleValue(this.stack.pop());
            this.stack.push(new StackDoubleValue(operator.operate(second.getStackValue(), first.getStackValue())));

        }
    }

    @Override
    public void visit(IFormulaOperand operand) throws MultiSpreadsheetException {
        this.stack.add(new StackDoubleValue(operand.getValue()));
    }

    @Override
    public void visit(IFormulaFunctionArgumentSeparator operator) {
        IStackValue first = this.stack.pop();
        if (first instanceof StackDoubleValue) {
            StackDoubleValue firstParsed = (StackDoubleValue) first;
            IStackValue second = this.stack.pop();
            if (second instanceof StackDoubleValue) {
                StackDoubleValue secondParsed = (StackDoubleValue) second;
                StackListDoubleValue listValue = new StackListDoubleValue(secondParsed.getStackValue());
                listValue.addValue(firstParsed.getStackValue());
                this.stack.push(listValue);
            } else {
                StackListDoubleValue secondParsed = (StackListDoubleValue) second;
                secondParsed.addValue(firstParsed.getStackValue());
                this.stack.push(secondParsed);
            }
        } else {
            StackListDoubleValue firstParsed = (StackListDoubleValue) first;
            IStackValue second = this.stack.pop();
            if (second instanceof StackDoubleValue) {
                StackDoubleValue secondParsed = (StackDoubleValue) second;
                firstParsed.addValue(secondParsed.getStackValue());
                this.stack.push(firstParsed);
            } else {
                StackListDoubleValue secondParsed = (StackListDoubleValue) second;
                for (Double doubles : secondParsed.getStackValue()) {
                    firstParsed.addValue(doubles);

                }
                this.stack.push(firstParsed);
            }
        }
    }

    @Override
    public void visit(IFormulaFunction function) {
        IStackValue first = this.stack.pop();
        if (first instanceof StackDoubleValue) {
            StackDoubleValue parsed = (StackDoubleValue) first;
            this.stack.push(new StackDoubleValue(function.evaluate(Arrays.asList(parsed.getStackValue()))));
        } else {
            StackListDoubleValue parsed = (StackListDoubleValue) first;
            this.stack.push(new StackDoubleValue(function.evaluate(parsed.getStackValue())));
        }
    }

    @Override
    public Double getResult() throws MultiSpreadsheetException {
        if (this.stack.size() != 1 || this.stack.peek() instanceof StackListDoubleValue) {
            throw new MultiSpreadsheetException("The evaluation did not obtain a valid result");
        } else {
            return ((StackDoubleValue) this.stack.peek()).getStackValue();
        }
    }

    @Override
    public void reset() {
        this.stack = new Stack<IStackValue>();
    }

}
