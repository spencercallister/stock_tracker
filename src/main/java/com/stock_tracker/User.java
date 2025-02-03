package com.stock_tracker;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class User {
    private String userId;
    private List<String> stockSymbols = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();

    public User(String userId) {
        this.userId = userId;
        System.out.println("Retrieving your stocks...");
        getUserSymbols();
        setUserStocks();
    }

    /**
     * Returns a string representation of the user's stock symbols.
     *
     * Iterates through the list of stock symbols and appends each symbol to a StringBuilder,
     * separating them with new line characters. The resulting string includes all stock symbols
     * currently associated with the user, each on a new line.
     *
     * @return a string containing all stock symbols, each followed by a newline.
     */

    public String listSymbols() {
        StringBuilder sb = new StringBuilder();
        for (String symbol : stockSymbols) {
            sb.append(symbol).append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves the list of stock symbols associated with the user.
     *
     * Queries the MotherDuck database using the user ID to retrieve the list of symbols
     * stored in the users table. The symbols are stored in a single string field, and are
     * parsed as JSON and converted to an ArrayList<String>. The resulting list of symbols
     * is stored in the stockSymbols field of the User object.
     */
    public void getUserSymbols() {
        MotherDuckService md = new MotherDuckService();
        String query = "SELECT symbols FROM users WHERE _id = ?";
        String symbolsJson = md.querySingleResult("stock_tracker", query, userId);

        if (symbolsJson != null && !symbolsJson.isEmpty()) {
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                stockSymbols = gson.fromJson(symbolsJson, listType);
            } catch (Exception e) {
                System.err.println("Error parsing symbols JSON: " + e.getMessage());
            }
        }
    }

    /**
     * Populate the stocks list of the user object.
     *
     * This method iterates through the list of stock symbols associated with the user
     * and retrieves the corresponding stock data from Finnhub. The stock data is
     * used to create a new Stock object, which is then added to the stocks list.
     */
    public void setUserStocks(){
        for (String stockSymbol : stockSymbols) {
            FinnhubService stockService = new FinnhubService(stockSymbol);
            Symbol symbol = stockService.RetrieveSymbol();
            Profile profile = stockService.RetrieveProfile();
            News news = stockService.RetrieveNews();
            Quote quote = stockService.RetrieveQuote();
            Stock stock = new Stock(symbol, profile, news, quote);
            stocks.add(stock);
        }
    }

    /**
     * Updates the user's list of stock symbols in the MotherDuck database.
     *
     * Converts the stockSymbols ArrayList to a JSON string using Gson, and then
     * executes an UPDATE query on the users table in the MotherDuck database,
     * updating the symbols field for the user with the given userId with the new
     * list of stock symbols.
     */
    public void updateUserStocks() {
        MotherDuckService md = new MotherDuckService();
        String query = "UPDATE users SET symbols = ? WHERE _id = ?";
        try {
            // Convert stockSymbols to JSON using Gson
            Gson gson = new Gson();
            String symbolsJson = gson.toJson(stockSymbols);

            md.executeUpdate("stock_tracker", query, symbolsJson, userId);
        } catch (Exception e) {
            System.err.println("Error updating user stocks: " + e.getMessage());
        }
    }

    /**
     * Add a stock symbol to the user's list of symbols.
     *
     * Adds the given stock symbol to the user's list of symbols, updates the user's
     * record in the MotherDuck database, and then updates the list of Stock objects
     * associated with the user.
     *
     * @param symbol The stock symbol to add to the user's list of symbols
     */
    public void addSymbol(String symbol) {
        stockSymbols.add(symbol);
        updateUserStocks();
        setUserStocks();
    }

    /**
     * Retrieves a Stock object associated with the given stock symbol from the user's list of stocks.
     *
     * @param symbol The stock symbol of the Stock object to retrieve
     * @return The Stock object associated with the given stock symbol, or null if not found
     */
    public Stock selectStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;   
            }
        }
        return null;
    }
}
