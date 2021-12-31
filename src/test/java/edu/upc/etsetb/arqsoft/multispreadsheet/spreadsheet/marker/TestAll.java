/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.marker;

import edu.upc.ac.poo.correctorgenerico.SuperClassForTests;
import java.util.HashMap;
import java.util.Map;
import org.junit.rules.ErrorCollector;
import org.junit.runner.JUnitCore;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public class TestAll {

    private static String[] clases = {
            "TextContentTest", "NumberContentTest", "FormulaContentTest",
            "DependentCellsTest", "CircularDependenciesTest", "SaveTest",
            "UploadTest", "IUTest" };

    public static final double[] tantosPorCiento = {
            1.5, // TextContentTest
            1.5, // NumberContentTest
            64.5, // FormulaTest. Edition and computation of formulas
            12.5, // UpdateTest. Update of cells with formulas that depend on the modified cell
            7.5, // DependenciesTest. Circular dependencies
            5, // SaveTest
            5, // LoadTest
            2.5 // IUTest
    };

    public static Map<String, Double> notas;
    public static Map<String, Double> porcentajes;

    public static ErrorCollector allCollector;

    static {
        notas = new HashMap<>();
        porcentajes = new HashMap<>();
        // allCollector = SuperClassForTests.allCollector;
        int i = -1;
        for (String className : clases) {
            i++;
            notas.put(className, 0.0);
            porcentajes.put(className, tantosPorCiento[i]);
        }
    }

    public static void main(String[] args) {
        JUnitCore.runClasses(TestSuite.class);
        System.out.println("\n\nResumen de notas obtenidas en corrección automática:\n");
        double notaFinal = 0;
        double notaParcial;
        for (Map.Entry<String, Double> nota : notas.entrySet()) {
            String className = nota.getKey();
            notaParcial = nota.getValue() * porcentajes.get(className) / 100;
            notaFinal += notaParcial;
            System.out.println("Nota en clase " + className
                    + ": " + SuperClassForTests.withMathRound(nota.getValue(), 3) + " (Porcentaje en nota final: "
                    + porcentajes.get(className) + "%). Contribución a nota final: " + notaParcial);
        }
        System.out.println("\nNota final de corrección automática: " + SuperClassForTests.withMathRound(notaFinal, 3));
    }
}
