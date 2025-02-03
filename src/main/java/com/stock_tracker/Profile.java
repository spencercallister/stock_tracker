package com.stock_tracker;

public class Profile {
    private String country;
    private String currency;
    private String exchange;
    private String ipo;
    private String marketCapitalization;
    private String name;
    private String phone;
    private String shareOutstanding;
    private String ticker;
    private String weburl;
    private String logo;
    private String finnhubIndustry;

    public Profile(String country, String currency, String exchange, String ipo, String marketCapitalization, String name, String phone, String shareOutstanding, String ticker, String weburl, String logo, String finnhubIndustry) {
        this.country = country;
        this.currency = currency;
        this.exchange = exchange;
        this.ipo = ipo;
        this.marketCapitalization = marketCapitalization;
        this.name = name;
        this.phone = phone;
        this.shareOutstanding = shareOutstanding;
        this.ticker = ticker;
        this.weburl = weburl;
        this.logo = logo;
        this.finnhubIndustry = finnhubIndustry;
    }

    /**
     * Displays the profile details to the user.
     * 
     * The profile details are outputted in a formatted string and
     * include the country, currency, exchange, IPO, market capitalization,
     * name, phone, share outstanding, ticker, web URL, logo, and Finnhub
     * industry.
     */
    public void displayProfile() {
        StringBuilder articleDisplay = new StringBuilder();

        articleDisplay.append("=====================================\n");
        articleDisplay.append("            Profile Details          \n");
        articleDisplay.append("=====================================\n");
        articleDisplay.append(String.format("Country: %-25s\n", country));
        articleDisplay.append(String.format("Currency: %-25s\n", currency));
        articleDisplay.append(String.format("Exchange: %-25s\n", exchange));
        articleDisplay.append(String.format("IPO: %-25s\n", ipo));
        articleDisplay.append(String.format("Market Capitalization: %-25s\n", marketCapitalization));
        articleDisplay.append(String.format("Name: %-25s\n", name));
        articleDisplay.append(String.format("Phone: %-25s\n", phone));
        articleDisplay.append(String.format("Share Outstanding: %-25s\n", shareOutstanding));
        articleDisplay.append(String.format("Ticker: %-25s\n", ticker));
        articleDisplay.append(String.format("Web URL: %-25s\n", weburl));
        articleDisplay.append(String.format("Logo: %-25s\n", logo));
        articleDisplay.append(String.format("Finnhub Industry: %-25s\n", finnhubIndustry));
        articleDisplay.append("=====================================\n");

        System.out.println(articleDisplay);
    }
}
