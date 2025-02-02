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

    public String getSymbol() {
        return symbol.getSymbol();
    }

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
