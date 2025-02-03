package com.stock_tracker;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.UUID;

public class Authenticate {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final MotherDuckService mdService = new MotherDuckService();
    private final String dbName = "stock_tracker";

    private boolean authenticated = false;
    private final String inputUsername;
    private final String inputPassword;

    public Authenticate(String username, String password) {
        this.inputUsername = username;
        this.inputPassword = password;
    }

    /**
     * Compares the input password with the hashed password stored in the database.
     *
     * If the input password matches the hashed password, sets authenticated to true and
     * returns the user ID. Otherwise, returns null.
     *
     * @return user ID if login successful, null otherwise
     */
    public String checkCredentials() {
        String storedHashedPassword = getUserPassword(inputUsername);

        if (encoder.matches(inputPassword, storedHashedPassword)) {
            System.out.println("Login successful!");
            authenticated = true;
            return getUserId();
        } else {
            return null;
        }
    }

    /**
     * Checks if a user exists in the database for the given username.
     *
     * Executes a query to determine if there is a record with the specified username
     * in the users table. If a record is found, it returns true; otherwise, false.
     *
     * @return true if the user exists, false otherwise
     */

    public boolean doesUserExist() {
        String query = "SELECT 1 FROM users WHERE username = ?";
        return mdService.querySingleResult(dbName, query, inputUsername) != null;
    }

    /**
     * Creates a new user with the given username and password.
     *
     * Checks if the user already exists. If the user does not exist, it creates a new
     * record in the users table with the given username and a hashed version of the
     * password. The hashed password is generated using BCrypt.
     */
    public void createUser() {
        if (doesUserExist()) {
            System.out.println("User already exists.");
            return;
        }

        String hashedPassword = encoder.encode(inputPassword);
        String userId = UUID.randomUUID().toString();
        String query = "INSERT INTO users (_id, username, password) VALUES (?, ?, ?)";
        mdService.executeUpdate(dbName, query, userId, inputUsername, hashedPassword);
        System.out.println("User created successfully.");
    }

    /**
     * Retrieves the password for a given username.
     *
     * Executes a query to find the password associated with the given username in the users table.
     *
     * @param username the username of the user to retrieve the password for
     * @return the password of the user (hashed using BCrypt) or null if no user with
     * the given username is found
     */
    private String getUserPassword(String username) {
        String query = "SELECT password FROM users WHERE username = ?";
        return mdService.querySingleResult(dbName, query, username);
    }

    /**
     * Indicates whether the user is authenticated or not.
     *
     * @return true if the user has been successfully authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Retrieves the user ID for the given username.
     *
     * Executes a query to find the user ID associated with the input username
     * in the users table.
     *
     * @return the user ID of the user or null if no user with the given username is found
     */

    public String getUserId() {
        String query = "SELECT _id FROM users WHERE username = ?";
        return mdService.querySingleResult(dbName, query, inputUsername);
    }
}