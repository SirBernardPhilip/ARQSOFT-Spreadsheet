/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.marker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
@RunWith(Suite.class)
@SuiteClasses({
        TextContentTest.class,
        NumberContentTest.class,
        FormulaContentTest.class,
        DependentCellsTest.class,
        CircularDependenciesTest.class
})
public class TestSuite {

}
