package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.exceptions;

public class CircularDependencyException extends SpreadsheetFormulaException {
    /**
     * Creates new CircularDependencyException with the given message.
     * 
     * @param message
     */
    public CircularDependencyException() {
        super("There is a circular dependency in the formula.");
    }
}