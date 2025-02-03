package com.stock_tracker;

import java.util.*;

public class News {
    private List<Article> news = new ArrayList<>();
    private ConsoleTools console = new ConsoleTools();

/**
 * Adds the given article to the list of news articles.
 *
 * @param article the article to be added to the news list
 */

    public void addArticle(Article article) {
        news.add(article);
    }

    /**
     * Displays the list of news articles associated with a stock, allowing the user
     * to navigate through the articles one by one. If the user presses Enter, the next
     * article is displayed. If the user types anything, the function will return to the
     * stock detail menu.
     *
     * @param menuManager the MenuManager instance used to navigate menus
     * @param stock the Stock instance associated with the news articles
     */

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