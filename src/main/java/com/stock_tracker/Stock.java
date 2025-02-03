package com.stock_tracker;

public class Stock {
    private Symbol symbol;
    private Profile profile;
    private News news;
    private Quote quote;

    ConsoleTools console = new ConsoleTools();

    public Stock(Symbol symbol, Profile profile, News news, Quote quote) {
        this.symbol = symbol;
        this.profile = profile;
        this.news = news;
        this.quote = quote;
    }

    /**
     * Gets the symbol of the stock.
     * 
     * @return the symbol of the stock
     */
    public String getSymbol() {
        return symbol.getSymbol();
    }

    /**
     * Displays the selected detail of the stock based on user choice.
     *
     * This method accepts a menu manager and a choice string to determine
     * which stock detail to display. The choices are:
     * 1. Display symbol details.
     * 2. Display profile details.
     * 3. Display news articles related to the stock.
     * 4. Display quote details.
     * 5. Return to the main user menu.
     * 6. Exit the application.
     * If an invalid choice is entered, the menu is cleared, and the user
     * is prompted to enter a valid option.
     *
     * @param menuManager the menu manager handling the interaction
     * @param choice the user's choice indicating which detail to display
     */

    public void displayDetail(MenuManager menuManager, String choice) {
        switch (choice) {
            case "1":
                symbol.displaySymbol();
                return;
            case "2":
                profile.displayProfile();
                return;
            case "3":
                news.displayNews(menuManager, this);
                return;
            case "4":
                quote.displayQuote();
                return;
            case "5":
                menuManager.mainUser();
                return;
            case "6":
                System.exit(0);
            default:
                console.clear();
                System.out.println("Sorry, please enter one of the available options.");
                menuManager.stockDetail(this);
        }
    }

}
