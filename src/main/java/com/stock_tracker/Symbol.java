package com.stock_tracker;

public class Symbol {
    private String description;
    private String displaySymbol;
    private String symbol;
    private String type;

    public Symbol(String description, String displaySymbol, String symbol, String type) {
        this.description = description;
        this.displaySymbol = displaySymbol;
        this.symbol = symbol;
        this.type = type;
    }

    public void displaySymbol() {
        StringBuilder articleDisplay = new StringBuilder();

        articleDisplay.append("=====================================\n");
        articleDisplay.append("            Symbol Details          \n");
        articleDisplay.append("=====================================\n");
        articleDisplay.append(String.format("Description: %-25s\n", description));
        articleDisplay.append(String.format("Display Symbol: %-25s\n", displaySymbol));
        articleDisplay.append(String.format("Symbol: %-25s\n", symbol));
        articleDisplay.append(String.format("Type: %-25s\n", type));
        articleDisplay.append("=====================================\n");

        System.out.println(articleDisplay);
    }

    public String getSymbol() {
        return symbol;
    }
}
