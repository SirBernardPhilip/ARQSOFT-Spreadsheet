package edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions;


/**
 * Exception for filesystem write.
 */
public class NoWriteAccessException extends Exception {
    /**
     * Creates new NoWriteAccessException with the given filepath.
     * 
     * @param message
     */
    public NoWriteAccessException(String filepath) {
        super(String.format("The path %s can not be written into", filepath));
    }
}
