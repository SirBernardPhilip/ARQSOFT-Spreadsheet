package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import java.util.Scanner;

public class UserPrompter {

    /**
     * Methods that asks the user a yes or no question
     * 
     * @param prompt
     * @return boolean
     */
    public static boolean promptYesOrNo(String prompt) {
        // FIXME: Should I use the scanner we already have in UserInterface?
        Scanner scanner = new Scanner(System.in);
        String yesOrNo = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.println(prompt);
            System.out.println("[Y/y] or [N/n]");

            yesOrNo = scanner.nextLine();
            validInput = yesOrNo.toUpperCase().equals("Y")
                    || yesOrNo.toUpperCase().equals("N");
            if (!validInput) {
                System.out
                        .println("Invalid input! Please enter /'Y/' or /'N/'");
            }
        }
        // scanner.close();
        return yesOrNo.toUpperCase().equals("Y");
    }

    /**
     * Method that asks the user to input some data
     * 
     * @param prompt
     * @return String
     */
    public static String promptInfo(String prompt) {
        // FIXME: Should I use the scanner we already have in UserInterface?
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String info = scanner.nextLine();
        // scanner.close();
        return info;

    }
}
