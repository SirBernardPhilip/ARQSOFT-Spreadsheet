package edu.upc.etsetb.arqsoft.multispreadsheet.ui;

import java.util.Scanner;

public class UserPrompter {

    private Scanner scanner;

    private UserPrompter() {
    }

    public static UserPrompter getInstance() {
        return new UserPrompter();
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Methods that asks the user a yes or no question
     * 
     * @param prompt
     * @return boolean
     */
    public boolean promptYesOrNo(String prompt) {
        String yesOrNo = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.println(prompt);
            System.out.println("[Y/y] or [N/n]");

            yesOrNo = this.scanner.nextLine();
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
    public String promptInfo(String prompt) {
        System.out.println(prompt);
        String info = this.scanner.nextLine();
        // scanner.close();
        return info;

    }
}
