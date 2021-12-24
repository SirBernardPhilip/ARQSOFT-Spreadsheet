/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import java.util.Scanner;

import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiCellContentFactory;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetController;
import edu.upc.etsetb.arqsoft.multispreadsheet.usecases.AMultiSpreadsheetFactory;

/** @author bernatfelip */
public abstract class AUserInterface {

    protected AMultiSpreadsheetFactory spreadsheetFactory;
    protected AMultiCellContentFactory cellContentFactory;

    protected AMultiSpreadsheetController spreadsheetController;

    protected Scanner scan;

    protected AUserInterface(AMultiSpreadsheetFactory spreadsheetFactory, AMultiCellContentFactory cellContentFactory) {
        this.scan = new Scanner(System.in);
        this.spreadsheetFactory = spreadsheetFactory;
        this.cellContentFactory = cellContentFactory;
    }

    public abstract boolean readCommand();

}
