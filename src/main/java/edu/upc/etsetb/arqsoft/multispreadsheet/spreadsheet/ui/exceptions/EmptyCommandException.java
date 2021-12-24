package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.exceptions;

public class EmptyCommandException extends Exception {
    public EmptyCommandException() {
        super(String.format("The command is empty"));
    }
}