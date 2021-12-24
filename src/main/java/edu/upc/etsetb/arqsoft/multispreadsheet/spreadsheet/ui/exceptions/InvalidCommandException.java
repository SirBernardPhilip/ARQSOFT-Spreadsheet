package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String command) {
        super(String.format("The command %s is not a valid command", command));
    }
}