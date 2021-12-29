/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.exceptions.EmptyCommandException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.exceptions.InvalidCommandException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.ui.exceptions.MissingArgumentException;
import edu.upc.etsetb.arqsoft.multispreadsheet.ui.AUserInterface;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

/** @author bernatfelip */
public class UserInterface extends AUserInterface {

    protected UserInterface(AMultiSpreadsheetFactory spreadsheetFactory, AMultiCellContentFactory cellContentFactory) {
        super(spreadsheetFactory, cellContentFactory);
        this.spreadsheetController = this.spreadsheetFactory.getController(this.spreadsheetFactory, cellContentFactory);

    }

    public static UserInterface getInstance(AMultiSpreadsheetFactory spreadsheetFactory,
            AMultiCellContentFactory cellContentFactory) {
        return new UserInterface(spreadsheetFactory, cellContentFactory);
    }

    /**
     * Method that reads the next command and prints the menu
     * 
     * @return boolean
     */
    @Override
    public boolean readCommand() {
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("(RF) <TXT path>             -- Read commands from file");
        System.out.println("(C)                         -- Create a new spreadsheet");
        System.out.println("(E) <coord> <content>       -- Edit a cell");
        System.out.println("(L) <S2V path>              -- Load a spreadsheet from a file");
        System.out.println("(S) <S2V path>              -- Save the spreadsheet to a file");
        System.out.println("(X)                         -- Exit the program");
        System.out.println();
        if (scan.hasNextLine()) {
            String line = scan.nextLine();
            System.out.println();
            try {
                return this.runCommand(line);
            } catch (InvalidCommandException e) {
                System.out.println(String.format("The command %s is not valid", line));
                return true;
            } catch (MissingArgumentException e) {
                System.out.println(String.format("The command %s is missing an argument", line));
                return true;
            } catch (EmptyCommandException e) {
                System.out.println("The command is empty");
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("The file could not be accessed");
                return true;
            }
        }
        return false;
    }

    /**
     * Method that runs the command
     * 
     * @param line
     * @return boolean
     * @throws InvalidCommandException
     * @throws MissingArgumentException
     * @throws EmptyCommandException
     * @throws FileNotFoundException
     */
    private boolean runCommand(String line)
            throws MissingArgumentException, InvalidCommandException, EmptyCommandException, FileNotFoundException {
        String[] parsedLine = line.split(" ", 3);
        if (parsedLine.length < 1 || parsedLine[0].trim().length() == 0) {
            throw new EmptyCommandException();
        }
        switch (parsedLine[0]) {
            case "RF":
                if (parsedLine.length < 2) {
                    throw new MissingArgumentException(1, parsedLine.length - 1);
                }
                String filePath = parsedLine[1];
                this.scan = new Scanner(new FileInputStream(filePath));
                return true;
            case "C":
                spreadsheetController.createSpreadsheet();
                spreadsheetController.viewSpreadsheet();
                return true;
            case "E":
                if (parsedLine.length < 3) {
                    throw new MissingArgumentException(2, parsedLine.length - 1);
                }
                spreadsheetController.editCell(parsedLine[1], parsedLine[2]);
                spreadsheetController.viewSpreadsheet();
                return true;
            case "L":
                if (parsedLine.length < 2) {
                    throw new MissingArgumentException(1, parsedLine.length - 1);
                }
                spreadsheetController.loadSpreadsheet(parsedLine[1]);
                spreadsheetController.viewSpreadsheet();
                return true;
            case "S":
                if (parsedLine.length < 2) {
                    throw new MissingArgumentException(1, parsedLine.length - 1);
                }
                spreadsheetController.saveSpreadsheet(parsedLine[1]);
                spreadsheetController.viewSpreadsheet();
                return true;
            case "X":
                return false;
            default:
                throw new InvalidCommandException(line);
        }
    }

}
