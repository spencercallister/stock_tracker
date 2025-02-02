package com.stock_tracker;

public class Quote {
    private String c;
    private String d;
    private String dp;
    private String h;
    private String l;
    private String o;
    private String pc;
    private String t;

    public Quote(String c, String d, String dp, String h, String l, String o, String pc, String t) {
        this.c = c;
        this.d = d;
        this.dp = dp;
        this.h = h;
        this.l = l;
        this.o = o;
        this.pc = pc;
        this.t = t;
    }

    public void displayQuote() {
        DateFormat dateFormat = new DateFormat();
        String formattedDateTime = dateFormat.localDateTime(t);

        StringBuilder articleDisplay = new StringBuilder();

        articleDisplay.append("=====================================\n");
        articleDisplay.append("            Quote Details          \n");
        articleDisplay.append("=====================================\n");
        articleDisplay.append(String.format("Current Price: %-25s\n", c));
        articleDisplay.append(String.format("Change: %-25s\n", d));
        articleDisplay.append(String.format("Percent Change: %-25s\n", dp));
        articleDisplay.append(String.format("Day High: %-25s\n", h));
        articleDisplay.append(String.format("Day Low: %-25s\n", l));
        articleDisplay.append(String.format("Open: %-25s\n", o));
        articleDisplay.append(String.format("Previous Close: %-25s\n", pc));
        articleDisplay.append(String.format("Date: %-25s\n", formattedDateTime));
        articleDisplay.append("=====================================\n");

        System.out.println(articleDisplay);
    }
}

