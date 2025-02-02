package com.stock_tracker;

public class Article {
    private String category;
    private String datetime;
    private String headline;
    // private String id;
    // private String image;
    private String related;
    private String source;
    private String summary;
    private String url;

    public Article(String category, String datetime, String headline, String id, String image, String related, String source, String summary, String url) {
        this.category = category;
        this.datetime = datetime;
        this.headline = headline;
        // this.id = id;
        // this.image = image;
        this.related = related;
        this.source = source;
        this.summary = summary;
        this.url = url;
    }

    
    public void displayArticle() {
        DateFormat dateFormat = new DateFormat();
        String formattedDateTime = dateFormat.localDateTime(datetime);
    
        String cleanSummary = summary.replaceAll("[^\\x00-\\x7F]", "");
    
        StringBuilder articleDisplay = new StringBuilder();
    
        articleDisplay.append("=====================================\n");
        articleDisplay.append("            Article Details          \n");
        articleDisplay.append("=====================================\n");
        articleDisplay.append(String.format("Category: %-25s\n", category));
        articleDisplay.append(String.format("Date Posted: %-25s\n", formattedDateTime));
        articleDisplay.append(String.format("Headline: %-25s\n", headline));
        articleDisplay.append(String.format("Related:  %-25s\n", related));
        articleDisplay.append(String.format("Source:   %-25s\n", source));
        articleDisplay.append(String.format("Summary:  %-25s\n", cleanSummary));
        articleDisplay.append(String.format("URL:      %-25s\n", url));
        articleDisplay.append("=====================================\n");
        articleDisplay.append("\n");
    
        System.out.println(articleDisplay.toString());
    }
    
}
