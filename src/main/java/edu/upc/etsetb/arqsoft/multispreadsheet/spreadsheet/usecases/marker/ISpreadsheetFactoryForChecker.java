package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.marker;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.controller.SpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public interface ISpreadsheetFactoryForChecker {

    public static ISpreadsheetControllerForChecker createSpreadsheetControllerForChecker(
            AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return SpreadsheetControllerForChecker.getInstance(spreadsheetFactory, cellContentFactory);

    }
}
