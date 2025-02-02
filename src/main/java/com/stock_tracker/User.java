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

    public String listSymbols() {
        StringBuilder sb = new StringBuilder();
        for (String symbol : stockSymbols) {
            sb.append(symbol).append("\n");
        }
        return sb.toString();
    }

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

    public void addSymbol(String symbol) {
        stockSymbols.add(symbol);
        updateUserStocks();
        setUserStocks();
    }

    public Stock selectStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;   
            }
        }
        return null;
    }
}
