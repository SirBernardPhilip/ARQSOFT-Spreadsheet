package edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions;


public class NoReadAccessException extends Exception {
    /**
     * Creates new NoReadAccessException with the given filepath.
     * 
     * @param message
     */
    public NoReadAccessException(String filepath) {
        super(String.format("The path %s can not be read from", filepath));
    }
}
