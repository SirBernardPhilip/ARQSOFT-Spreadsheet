package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

public interface ISpreadsheetPrinter {

        /**
         ** Method that prints the spreadsheet
         * 
         * @param spreadsheet
         * @throws MultiSpreadsheetException
         */
        public void printSpreadsheet(ISpreadsheet spreadsheet)
                        throws MultiSpreadsheetException;
}
