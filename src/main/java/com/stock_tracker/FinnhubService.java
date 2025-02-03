package com.stock_tracker;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FinnhubService {

    private String url = "https://finnhub.io/api/v1/%s&exchange=US&token=%s";
    private String symbol;
    private String apiKey = "cu81ic1r01qhqu5btj6gcu81ic1r01qhqu5btj70";
    private String data;
    

    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();

    public FinnhubService(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Makes a GET request to the Finnhub API for the given endpoint and returns the response body.
     * 
     * @param endpoint The Finnhub API endpoint to query, e.g. "quote", "profile", etc.
     * @return The response body of the GET request
     */
    public String RetrieveData(String endpoint) {
        String apiUrl = String.format(url, endpoint, apiKey);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) data = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Queries the Finnhub API for the given stock symbol and returns a Symbol
     * object containing the symbol details.
     *
     * This method queries the Finnhub "search" endpoint for the given stock
     * symbol. If the given symbol is found, it is parsed as JSON and converted
     * to a Symbol object, which is then returned. If the given symbol is not
     * found, this method returns null.
     *
     * @return The Symbol object associated with the given stock symbol, or null
     * if not found
     */
    public Symbol RetrieveSymbol() {
        String data = RetrieveData("search?q=" + symbol);
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        JsonArray results = jsonObject.getAsJsonArray("result");
        if (results == null) return null;
        for (JsonElement element : results) {
            JsonObject stock = element.getAsJsonObject();
            if (symbol.equals(stock.get("symbol").getAsString())) {
                return gson.fromJson(stock.toString(), Symbol.class);
            }
        }

        return null;
    }

    /**
     * Retrieves the profile information for the given stock symbol.
     *
     * This method queries the Finnhub "stock/profile2" endpoint for the given stock
     * symbol and converts the JSON response into a Profile object.
     * 
     * @return The Profile object containing the profile information for the stock symbol.
     */

    public Profile RetrieveProfile() {
        String data = RetrieveData("stock/profile2?symbol=" + symbol);
        return gson.fromJson(data, Profile.class);
    }

    /**
     * Retrieves the news information for the given stock symbol.
     * 
     * This method queries the Finnhub "company-news" endpoint for the given stock
     * symbol and converts the JSON response into a News object.
     * 
     * The news query is limited to the last two weeks of news articles.
     * 
     * @return The News object containing the news articles for the stock symbol.
     */
    public News RetrieveNews() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastTwoWeeks = currentDate.minusWeeks(2);
        String data = RetrieveData("company-news?symbol=" + symbol + "&from="+ lastTwoWeeks + "&to=" + currentDate);
        JsonArray newsArray = gson.fromJson(data, JsonArray.class);

        News news = new News();
        for (JsonElement element : newsArray) {
            JsonObject newsObject = element.getAsJsonObject();
            Article article = gson.fromJson(newsObject.toString(), Article.class);
            news.addArticle(article);
        }

        return news;
    }

    /**
     * Retrieves the quote information for the given stock symbol.
     * 
     * This method queries the Finnhub "quote" endpoint for the given stock
     * symbol and converts the JSON response into a Quote object.
     * 
     * @return The Quote object containing the quote information for the stock symbol.
     */
    public Quote RetrieveQuote() {
        String data = RetrieveData("quote?symbol=" + symbol);
        return gson.fromJson(data, Quote.class);
    }


}
