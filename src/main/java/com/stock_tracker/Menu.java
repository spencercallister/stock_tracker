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

    public String menuSelect(String prompt) {
        System.out.print(prompt);
        return reader.nextLine();
    }
}