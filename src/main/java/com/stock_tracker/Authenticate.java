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

    public boolean doesUserExist() {
        String query = "SELECT 1 FROM users WHERE username = ?";
        return mdService.querySingleResult(dbName, query, inputUsername) != null;
    }

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

    private String getUserPassword(String username) {
        String query = "SELECT password FROM users WHERE username = ?";
        return mdService.querySingleResult(dbName, query, username);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getUserId() {
        String query = "SELECT _id FROM users WHERE username = ?";
        return mdService.querySingleResult(dbName, query, inputUsername);
    }
}