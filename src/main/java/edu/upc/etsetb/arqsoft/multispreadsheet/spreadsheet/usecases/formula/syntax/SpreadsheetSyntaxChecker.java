package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.syntax.ISpreadsheetSyntaxChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.tokens.ISpreadsheetToken;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions.MissingParenthesisException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.formula.syntax.exceptions.UnexpectedTokenException;

public class SpreadsheetSyntaxChecker implements ISpreadsheetSyntaxChecker {

    private LinkedList<ISpreadsheetToken> inputTokens;
    private LinkedList<ISpreadsheetToken> tokens;
    private Optional<ISpreadsheetToken> lookahead;

    private SpreadsheetSyntaxChecker() {
        this.inputTokens = new LinkedList<>();
        this.tokens = new LinkedList<>();
        this.lookahead = Optional.empty();
    }

    public static SpreadsheetSyntaxChecker getInstance() {
        return new SpreadsheetSyntaxChecker();
    }

    @Override
    public void check(List<ISpreadsheetToken> tokens) throws MissingParenthesisException, UnexpectedTokenException {
        this.tokens = new LinkedList<ISpreadsheetToken>();
        this.inputTokens = new LinkedList<ISpreadsheetToken>(tokens);
        this.lookahead = Optional.of(this.inputTokens.getFirst());

        expression();

        if (lookahead.isPresent()) {
            throw new UnexpectedTokenException(lookahead.get().getTokenString());
        }

    }

    private void addLookaheadToResult() {
        if (lookahead.isPresent()) {
            tokens.add(lookahead.get());
        }
    }

    private void nextToken() {
        this.addLookaheadToResult();

        this.inputTokens.pop();

        if (this.inputTokens.isEmpty()) {
            lookahead = Optional.empty();
        } else {
            lookahead = Optional.of(this.inputTokens.getFirst());
        }
    }

    private void sumOp() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isPlusMinus()) {
            nextToken();
            term();
            sumOp();
        }
    }

    private void signedTerm() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isPlusMinus()) {
            nextToken();
            term();
        }
        term();
    }

    private void term() throws MissingParenthesisException, UnexpectedTokenException {
        factor();
        termOp();
    }

    private void termOp() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isMultDiv()) {
            nextToken();
            signedFactor();
            termOp();
        }
    }

    private void signedFactor() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isPlusMinus()) {
            nextToken();
            factor();
        } else {
            factor();
        }
    }

    private void factor() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isFunction()) {
            nextToken();
            if (!lookahead.isPresent() || !lookahead.get().isOpenPar()) {
                throw new MissingParenthesisException(lookahead.isPresent() ? lookahead.get().getTokenString() : "EOF");
            }
            nextToken();
            func_argument();
            func_add_argument();
            if (!lookahead.isPresent() || !lookahead.get().isClosePar()) {
                throw new MissingParenthesisException(lookahead.isPresent() ? lookahead.get().getTokenString() : "EOF");
            }
            nextToken();
        } else if (lookahead.isPresent() && lookahead.get().isOpenPar()) {
            nextToken();
            expression();

            if (!lookahead.isPresent() || !lookahead.get().isClosePar()) {
                throw new MissingParenthesisException(lookahead.isPresent() ? lookahead.get().getTokenString() : "EOF");
            }
            nextToken();
        } else {
            value();
        }
    }

    private void func_argument() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isCellRange()) {
            nextToken();
        } else if (lookahead.isPresent() && lookahead.get().isFunction()) {
            nextToken();
            if (!lookahead.isPresent() || !lookahead.get().isOpenPar()) {
                throw new MissingParenthesisException(lookahead.isPresent() ? lookahead.get().getTokenString() : "EOF");
            }
            nextToken();
            func_argument();
            func_add_argument();
            if (!lookahead.isPresent() || !lookahead.get().isClosePar()) {
                throw new MissingParenthesisException(lookahead.isPresent() ? lookahead.get().getTokenString() : "EOF");
            }
            nextToken();
        } else {
            value();
        }
    }

    private void func_add_argument() throws MissingParenthesisException, UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isSemicolon()) {
            nextToken();
            func_argument();
            func_add_argument();
        }
    }

    private void value() throws UnexpectedTokenException {
        if (lookahead.isPresent() && lookahead.get().isNumber()) {
            nextToken();
        } else if (lookahead.isPresent() && lookahead.get().isCell()) {
            nextToken();
        } else {
            throw new UnexpectedTokenException(lookahead.isPresent() ? lookahead.get().getTokenString() : "EOF");
        }
    }

    private void expression() throws MissingParenthesisException, UnexpectedTokenException {
        signedTerm();
        sumOp();
    }

    @Override
    public List<ISpreadsheetToken> getTokens() {
        return this.tokens;
    }
}
