package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.ACellContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.FormulaContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.NumericalContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.TextualContent;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidFormulaTypeException;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;

public class CellContentFactory extends AMultiCellContentFactory {

    public CellContentFactory() {
    }

    /**
     * Method that returns the needed CellContent for the given content
     * 
     * @param content
     * @return CellContent
     * @throws InvalidFormulaTypeException
     * @throws MultiSpreadsheetFormulaException
     */
    public ACellContent getCellContent(String content)
            throws InvalidFormulaTypeException {
        if (content.length() >= 2 && content.charAt(0) == '=') {
            return FormulaContent.getInstance(content);
        } else {
            try {
                return NumericalContent.getInstance(content);
            } catch (NumberFormatException e) {
                return TextualContent.getInstance(content);
            }
        }
    }
}