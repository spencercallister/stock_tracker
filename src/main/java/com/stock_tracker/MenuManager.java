package com.stock_tracker;

public class MenuManager {

    private ConsoleTools console = new ConsoleTools();
    private User user;

    /**
     * Displays the stock details to the user, and repeatedly asks the user
     * which detail they would like to view.
     *
     * The user is presented with a menu of options to view the symbol,
     * profile, news, or quote of the stock. The user is also given the
     * option to go back to the previous menu or quit the program.
     *
     * @param stock The stock to display the details for.
     */
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

    /**
     * Displays a menu to the user to select a stock from their list of
     * stocks.
     *
     * The user is presented with a menu of their stocks, and asked to enter
     * the symbol of the stock they would like to view. The user is also given
     * the option to go back to the previous menu or quit the program.
     *
     * @param message The message to display to the user.
     */
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

    /**
     * Prompts the user to add a new stock by entering its symbol.
     *
     * Displays a menu to the user to enter a new stock's symbol. If the symbol
     * is not found in the Finnhub database, the user is prompted to enter a valid symbol.
     * If the stock is already added to the user's list, the user is prompted to choose another stock.
     * Once a valid, unique stock symbol is entered, the stock is added to the user's list and the main user menu is displayed.
     *
     * @param message The message to display to the user when prompting for a stock symbol.
     */

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

    /**
     * Displays the main menu to the user, allowing them to add a stock, view
     * their stocks, or quit the program.
     *
     * If the user has no stocks, they are prompted to add a stock first.
     *
     * The user is presented with a menu of options to add a stock, view their
     * stocks, or quit the program. The user's choice is then used to determine
     * which action to take.
     */
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

    /**
     * Asks the user what they would like to do when they are unable to create an account
     * because the username already exists.
     *
     * The user is presented with a menu of options to retry creating the account,
     * login to an existing account, or quit the program. The user's choice is then
     * used to determine which action to take.
     */
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

    /**
     * Asks the user what they would like to do when they fail to log in.
     *
     * The user is presented with a menu of options to retry logging in,
     * create a new account, or quit the program. The user's choice is then
     * used to determine which action to take.
     *
     * @param message The message to display to the user.
     */

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


    /**
     * Prompts the user for their username and password, and attempts to authenticate
     * them.
     *
     * If the user does not exist, the user is asked what they would like to do. If the
     * user's credentials are incorrect, they are asked to retry. If the user is
     * successfully authenticated, an instance of the User class is created and the
     * user is sent to the main user menu.
     */
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


    /**
     * Prompts the user for their desired username and password, and attempts to create
     * a new user account.
     *
     * If the user already exists, the user is asked what they would like to do. If the
     * user is successfully created, an instance of the User class is created and the
     * user is sent to the main user menu.
     */
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

    /**
     * The entry point for the program.
     *
     * Displays a menu with options to login, create an account, or quit the program.
     * The user is repeatedly asked for their choice until a valid option is selected.
     */
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

