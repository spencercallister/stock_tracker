package com.stock_tracker;

import java.util.Scanner;

public class Menu {
    private static final Scanner reader = new Scanner(System.in);
    private String message;
    private String options;

    public Menu(String message, String options) {
        this.message = message;
        this.options = options;
        System.out.println(this.message);
        System.out.println(this.options);
    }

    public Menu(String message) {
        this.message = message;
        System.out.println(this.message);
    }

    /**
     * Asks the user for a choice based on the given prompt.
     * 
     * The user is presented with the given prompt and asked to enter a choice.
     * The choice is then returned.
     * 
     * @param prompt The message to display to the user.
     * @return The user's choice.
     */
    public String menuSelect(String prompt) {
        System.out.print(prompt);
        return reader.nextLine();
    }
}