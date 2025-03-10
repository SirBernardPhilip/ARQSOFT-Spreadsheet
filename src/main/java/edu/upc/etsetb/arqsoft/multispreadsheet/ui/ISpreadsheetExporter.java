package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import java.io.IOException;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ISpreadsheet;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoWriteAccessException;

public interface ISpreadsheetExporter {

        /**
         * 
         * Method that exports a spreadsheet into a file
         * 
         * @param spreadsheet
         * @param savePath
         * @throws NoWriteAccessException
         * @throws IOException
         * @throws MultiSpreadsheetException
         */
        public void exportSpreadsheet(ISpreadsheet spreadsheet, String savePath)
                        throws NoWriteAccessException,
                        IOException, MultiSpreadsheetException;
}
