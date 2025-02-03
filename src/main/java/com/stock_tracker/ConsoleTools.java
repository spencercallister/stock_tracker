package com.stock_tracker;

public class ConsoleTools {
    /**
     * Clear the console.
     *
     * If the operating system is Windows, this runs the {@code cls} command. Otherwise,
     * it sends the ANSI escape code for clearing the screen.
     *
     * @throws Exception if an error occurs while clearing the console
     */
    public void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Failed to clear console: " + e.getMessage());
        }
    }
}