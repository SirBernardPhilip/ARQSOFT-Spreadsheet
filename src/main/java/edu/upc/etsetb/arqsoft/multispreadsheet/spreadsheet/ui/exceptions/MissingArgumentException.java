package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.exceptions;

public class MissingArgumentException extends Exception {
    public MissingArgumentException(int requiredArguments, int actualArguments) {
        super(String.format("The command requires %2d arguments but %2d were provided", requiredArguments,
                actualArguments));
    }
}