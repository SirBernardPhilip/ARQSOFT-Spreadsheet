package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import java.io.IOException;
import java.util.Map;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoReadAccessException;
import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoWriteAccessException;

public interface ISpreadsheetImporter {

        /**
         ** Method that imports the spreadsheet
         * 
         * @param spreadsheet
         * @throws MultiSpreadsheetException
         * @throws IOException
         * @throws NoWriteAccessException
         * @throws NoReadAccessException
         */
        public Map<ICellCoordinate, String> importSpreadsheet(String loadPath)
                        throws MultiSpreadsheetException, IOException, NoReadAccessException;
}
