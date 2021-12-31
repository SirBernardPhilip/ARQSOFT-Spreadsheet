package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.usecases.marker;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public interface ISpreadsheetControllerForChecker {

    public void setCellContent(String cellCoord, String strContent)
            throws MultiSpreadsheetException;

    public double getCellContentAsDouble(String coord) throws MultiSpreadsheetException;

    public String getCellContentAsString(String cooord) throws MultiSpreadsheetException;

}
