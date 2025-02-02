package com.stock_tracker;

public class MenuManager {

    private ConsoleTools console = new ConsoleTools();
    private User user;

    public void stockDetail(Stock stock) {
        Menu stockDetailMenu = new Menu("Which detail would you like to view?", """
            1. Symbol
            2. Profile
            3. News
            4. Quote
            5. Back
            6. Quit
        """);
        String detailsChoice = stockDetailMenu.menuSelect("Enter your choice: ");
        
        console.clear();
        stock.displayDetail(this, detailsChoice);
        
        stockDetail(stock);
    }

    public void stock(String message) {
        Menu stockMenu = new Menu(message, user.listSymbols());
        String stockSymbol = stockMenu.menuSelect("Enter the stock's symbol: ");

        Stock stock = user.selectStock(stockSymbol);

        if (stock == null) {
            console.clear();
            stock("Stock not found. Please choose one of the following:");
        } 
        
        console.clear();
        stockDetail(stock);
        
    }

    public void addStock(String message) {
        Menu addStockMenu = new Menu(message);
        String stockSymbol = addStockMenu.menuSelect("Enter a new stock's symbol: ");

        FinnhubService stockService = new FinnhubService(stockSymbol);

        if (stockService.RetrieveSymbol() == null) {
            console.clear();
            addStock("Stock not found. Please check that the symbol is correct.");
            return;
        }

        if (user.listSymbols().contains(stockSymbol)) {
            console.clear();
            addStock("Stock already added. Please choose another stock.");
            return;
        }

        user.addSymbol(stockSymbol);

        console.clear();
        mainUser();
    }

    public void mainUser() {
        console.clear();

        if (user.listSymbols().equals("")) {
            console.clear();
            addStock("Please add a stock first.");
        }

        Menu mainUserMenu = new Menu("What would you like to do?", """
            1. Add Stock
            2. View Stocks
            3. Quit
        """);
        String choice = mainUserMenu.menuSelect("Enter your choice: ");

        if (choice.equals("1")) {
            console.clear();
            addStock("Add a new stock");
        } else if (choice.equals("2")) {
            console.clear();
            stock("Please choose one of the following:");
        } else if (choice.equals("3")) {
            System.exit(0);
        }
    }

    public void retryCreate() {
        console.clear();

        Menu retryCreateMenu = new Menu(
            "Sorry, an account with that username already exists.", """
                1. Retry
                2. Login
                3. Quit
            """);
        String choice = retryCreateMenu.menuSelect("Enter your choice: ");

        if (choice.equals("1")) {
            createAccount();
        } else if (choice.equals("2")) {
            login();
        } else if (choice.equals("3")) {
            System.exit(0);
        } else {
            retryCreate();
        }
    }

    public void retryLogin(String message) {
        console.clear();

        Menu retryLoginMenu = new Menu(message, """
            1. Retry
            2. Create Account
            3. Quit
        """);
        String choice = retryLoginMenu.menuSelect("Enter your choice: ");
        if (choice.equals("1")) {
            login();
        } else if (choice.equals("2")) {
            createAccount();
        } else if (choice.equals("3")) {
            System.exit(0);
        } else {
            retryLogin(message);
        }
    } 


    public void login() {
        console.clear();

        Menu loginMenu = new Menu("Login");
        String inputUsername = loginMenu.menuSelect("Username: ");
        String inputPassword = loginMenu.menuSelect("Password: ");

        Authenticate authenticate = new Authenticate(inputUsername, inputPassword);

        System.out.println("Authenticating...");

        if (!authenticate.doesUserExist()) {
            retryLogin("Sorry, an account with that username does not exist.");
            return;
        }

        String userId = authenticate.checkCredentials();

        if (userId == null) {
            retryLogin("Your password is incorrect.");
        } else {
            user = new User(userId);
            mainUser();
        }

    }


    public void createAccount() {
        console.clear();

        Menu createAccountMenu = new Menu("Create Account");
        String inputUsername = createAccountMenu.menuSelect("Username: ");
        String inputPassword = createAccountMenu.menuSelect("Password: ");

        Authenticate authenticate = new Authenticate(inputUsername, inputPassword);

        if (authenticate.doesUserExist()) {
            retryCreate();
        } 
        
        authenticate.createUser();
        String userId = authenticate.checkCredentials();
        
        user = new User(userId);
        mainUser();
    }

    public void start() {
        console.clear();

        Menu startMenu = new Menu(
            "Welcome to Stock Tracker!", """
                1. Login
                2. Create Account
                3. Quit
            """);
        String choice = startMenu.menuSelect("Enter your choice: ");
        
        if (choice.equals("1")) {
            login();
        } else if (choice.equals("2")) {
            createAccount();
        } else if (choice.equals("3")) {
            System.exit(0);
        } else {
            start();
        }
    }

}

