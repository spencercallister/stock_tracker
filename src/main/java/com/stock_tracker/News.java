package com.stock_tracker;

import java.util.*;

public class News {
    private List<Article> news = new ArrayList<>();
    private ConsoleTools console = new ConsoleTools();

    public void addArticle(Article article) {
        news.add(article);
    }

    public void displayNews(MenuManager menuManager, Stock stock) {
        Scanner scanner = new Scanner(System.in); 
        int totalArticles = news.size();
        int currentIndex = 0;
        int pageSize = 1;
    
        try {
            while (currentIndex < totalArticles) {
                for (int i = currentIndex; i < Math.min(currentIndex + pageSize, totalArticles); i++) {
                    news.get(i).displayArticle();
                }
    
                if (currentIndex + pageSize < totalArticles) {
                    System.out.println("""
    
                    Press Enter to view the next article.
                    Type anything to return to the menu...
                    """);
    
                    System.out.flush();
                    
                    if (scanner.hasNextLine()) {
                        String input = scanner.nextLine().trim();
                        if (!input.isEmpty()) {
                            console.clear();
                            System.out.println("Returning to the menu...");
                            menuManager.stockDetail(stock);
                            return;
                        }
                    }
                } else {
                    console.clear();
                    System.out.println("End of articles. Returning to the menu...");
                    menuManager.stockDetail(stock);
                    return; 
                }
    
                currentIndex += pageSize;
            }
        } finally {
            scanner.close();
        }
    }
}