package edu.upc.etsetb.arqsoft.multispreadsheet.run;

import edu.upc.etsetb.arqsoft.multispreadsheet.ui.AUserInterface;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions.InvalidCellContentGenerationTypeException;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.exceptions.InvalidSpreadsheetTypeException;

/**
 * Main class, contains the static method that runs the program.
 * 
 */
public class Main {

    private static AMultiSpreadsheetFactory multiSpreadsheetFactory;
    private static AMultiCellContentFactory multiCellContentFactory;
    private static AUserInterface userInterface;

    /**
     * Prints the usage for the program
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar <jar-file> <spreadsheet-type> <cell-content-generation-type>");
    }

    /**
     * Main
     * 
     * @param args[]
     */
    public static void main(String args[]) {
        if (args.length != 2) {
            // Main.printUsage();
            // For debugging purposes
            try {
                Main.multiSpreadsheetFactory = AMultiSpreadsheetFactory.getInstance("spreadsheet");
                Main.multiCellContentFactory = AMultiCellContentFactory.getInstance("default");
                Main.userInterface = multiSpreadsheetFactory.getUserInterface(Main.multiSpreadsheetFactory,
                        Main.multiCellContentFactory);

                boolean continueExecution = true;
                while (continueExecution) {
                    continueExecution = userInterface.readCommand();
                }
            } catch (InvalidSpreadsheetTypeException
                    | InvalidCellContentGenerationTypeException e) {
                Main.printUsage();
            }
        } else {
            try {
                Main.multiSpreadsheetFactory = AMultiSpreadsheetFactory.getInstance(args[0]);
                Main.multiCellContentFactory = AMultiCellContentFactory.getInstance(args[1]);
                Main.userInterface = multiSpreadsheetFactory.getUserInterface(Main.multiSpreadsheetFactory,
                        Main.multiCellContentFactory);

                boolean continueExecution = true;
                while (continueExecution) {
                    continueExecution = userInterface.readCommand();
                }
            } catch (InvalidSpreadsheetTypeException
                    | InvalidCellContentGenerationTypeException e) {
                Main.printUsage();
            }
        }

    }
}
