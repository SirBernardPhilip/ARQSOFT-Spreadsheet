package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/** @author bernatfelip */
public class EntityTestsRunner {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(EntitiesTestSuite.class);
        if (result.getFailureCount() != 0) {
            System.out.println("\nThere have been failures. Below follow the details:\n");
            for (Failure failure : result.getFailures()) {
                System.out.println("Test case associated to method: " + failure);
            }
        } else {
            System.out.println("\nAll the test cases were successful");
        }
    }
}