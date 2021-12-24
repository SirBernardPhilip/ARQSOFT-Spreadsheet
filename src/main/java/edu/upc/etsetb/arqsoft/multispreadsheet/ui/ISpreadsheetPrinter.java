package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface ISpreadsheetPrinter {

    /**
     * Method that obtains all the values as a matrix
     * It also puts the names of the columns and rows
     * 
     * @param spreadsheet
     * @return String[][]
     * @throws MultiSpreadsheetException
     */
    public String[][] getAllValuesMatrix(ISpreadsheet spreadsheet)
            throws MultiSpreadsheetException;

    /**
     ** Method that prints the spreadsheet
     * 
     * @param spreadsheet
     * @throws MultiSpreadsheetException
     */
    public void printSpreadsheet(ISpreadsheet spreadsheet)
            throws MultiSpreadsheetException;
}
