package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.postfix;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.ISpreadsheetFormulaFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions.SpreadsheetFormulaException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.expression.ISpreadsheetExpressionGenerator;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.ISpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetTokenizer;

public class SpreadsheetPostfixGenerator implements ISpreadsheetExpressionGenerator {

    private ISpreadsheetFormulaFactory formulaFactory;
    private ISpreadsheetSyntaxChecker syntaxChecker;
    private ISpreadsheetTokenizer tokenizer;
    private List<ISpreadsheetToken> tokens;

    private SpreadsheetPostfixGenerator(ISpreadsheetFormulaFactory formulaFactory) {
        this.formulaFactory = formulaFactory;
        this.syntaxChecker = this.formulaFactory.getSpreadsheetSyntaxChecker();
        this.tokenizer = this.formulaFactory.getSpreadsheetTokenizer(this.formulaFactory);
        this.tokens = new LinkedList<ISpreadsheetToken>();
    }

    public static SpreadsheetPostfixGenerator getInstance(ISpreadsheetFormulaFactory formulaFactory) {
        SpreadsheetPostfixGenerator postfixGenerator = new SpreadsheetPostfixGenerator(formulaFactory);
        return postfixGenerator;
    }

    @Override
    public void generate(String input) throws SpreadsheetFormulaException {
        this.tokenizer.tokenize(input);
        this.syntaxChecker.check(this.tokenizer.getTokens());
        Stack<ISpreadsheetToken> stack = new Stack<ISpreadsheetToken>();
        LinkedList<ISpreadsheetToken> result = new LinkedList<ISpreadsheetToken>();

        for (ISpreadsheetToken token : this.syntaxChecker.getTokens()) {
            if (token.isNumber() || token.isCell() || token.isCellRange()) {
                result.add(token);
            } else {
                Boolean done = false;
                while (!done) {
                    Optional<ISpreadsheetToken> stackHead = Optional.empty();
                    if (!stack.isEmpty()) {
                        stackHead = Optional.of(stack.peek());
                    }
                    EPRecedenceActionCode actionCode = PrecedenceManager.getStackAction(token,
                            stackHead);
                    switch (actionCode) {
                        case PUSH_TO_STACK:
                            stack.push(token);
                            done = true;
                            break;
                        case POP_FROM_STACK:
                            result.add(stack.pop());
                            break;
                        case FIND_NEXT_OPEN_PAR:
                            ISpreadsheetToken poppedToken = stack.pop();
                            while (!poppedToken.isOpenPar()) {
                                result.add(poppedToken);
                                poppedToken = stack.pop();
                            }
                            if (!stack.isEmpty()) {
                                ISpreadsheetToken checkFunction = stack.peek();
                                if (checkFunction.isFunction()) {
                                    result.add(stack.pop());
                                }
                            }
                            done = true;
                            break;
                    }
                }
            }
        }
        while (!stack.empty()) {
            ISpreadsheetToken pop = stack.pop();
            result.add(pop);
        }
        this.tokens = result;
    }

    @Override
    public List<ISpreadsheetToken> getTokens() {
        return this.tokens;
    }

}
