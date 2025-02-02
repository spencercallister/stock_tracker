package com.stock_tracker;

import java.sql.*;

public class MotherDuckService {
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InNsY2FsbGlzdGVyQGdtYWlsLmNvbSIsInNlc3Npb24iOiJzbGNhbGxpc3Rlci5nbWFpbC5jb20iLCJwYXQiOiJaVVItN3pBS1RDZ084V1UwNzRvZW56cGc1SFNNQkRFZjRvMmxDRVdFLVFrIiwidXNlcklkIjoiMDg0ZjIzNGMtOGJkOC00Mzk5LWI5YzMtNjY3MTkzNTBhMjkyIiwiaXNzIjoibWRfcGF0IiwicmVhZE9ubHkiOmZhbHNlLCJ0b2tlblR5cGUiOiJyZWFkX3dyaXRlIiwiaWF0IjoxNzM3ODQ5MTY4fQ.oYg9GLPuCo4ZMWIgMSTynCy9U4qmTDaofUKfe_ULhuM";

    public String querySingleResult(String databaseName, String query, Object... params) {
        try (
            Connection conn = createConnection(databaseName);
            PreparedStatement pstmt = prepareStatement(conn, query, params);
            ResultSet rs = pstmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String databaseName, String query, Object... params) {
        try (
            Connection conn = createConnection(databaseName);
            PreparedStatement pstmt = prepareStatement(conn, query, params)
        ) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection(String databaseName) throws SQLException {
        return DriverManager.getConnection("jdbc:duckdb:md:" + databaseName + "?motherduck_token=" + token);
    }

    private PreparedStatement prepareStatement(Connection conn, String query, Object... params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof String[]) {
                Array array = conn.createArrayOf("TEXT", (String[]) params[i]);
                pstmt.setArray(i + 1, array);
            } else {
                pstmt.setObject(i + 1, params[i]);
            }
        }
        return pstmt;
    }
}