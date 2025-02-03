package com.stock_tracker;

import java.sql.*;

public class MotherDuckService {
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InNsY2FsbGlzdGVyQGdtYWlsLmNvbSIsInNlc3Npb24iOiJzbGNhbGxpc3Rlci5nbWFpbC5jb20iLCJwYXQiOiJaVVItN3pBS1RDZ084V1UwNzRvZW56cGc1SFNNQkRFZjRvMmxDRVdFLVFrIiwidXNlcklkIjoiMDg0ZjIzNGMtOGJkOC00Mzk5LWI5YzMtNjY3MTkzNTBhMjkyIiwiaXNzIjoibWRfcGF0IiwicmVhZE9ubHkiOmZhbHNlLCJ0b2tlblR5cGUiOiJyZWFkX3dyaXRlIiwiaWF0IjoxNzM3ODQ5MTY4fQ.oYg9GLPuCo4ZMWIgMSTynCy9U4qmTDaofUKfe_ULhuM";

    /**
     * Queries the given database in MotherDuck for a single result value.
     * 
     * Executes a prepared statement query against the given database in MotherDuck
     * with the given parameters. If the query returns at least one row, the first
     * column of the first row is returned as a string. Otherwise, null is returned.
     * 
     * @param databaseName the name of the database in MotherDuck to query
     * @param query        the SQL query to execute
     * @param params       the parameters to use in the query
     * @return the single result value, or null if no result is returned
     */
    
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

    /**
     * Executes an update query against the given database in MotherDuck.
     * 
     * Executes a prepared statement query against the given database in MotherDuck
     * with the given parameters. The query is executed as an update query, and the
     * number of rows affected is not returned.
     * 
     * @param databaseName the name of the database in MotherDuck to query
     * @param query        the SQL query to execute
     * @param params       the parameters to use in the query
     */
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

    /**
     * Establishes a connection to the specified MotherDuck database.
     * 
     * Creates a connection using the JDBC DriverManager with the given
     * database name and the MotherDuck token for authentication.
     * 
     * @param databaseName the name of the database to connect to
     * @return a Connection object to the specified database
     * @throws SQLException if a database access error occurs
     */

    private Connection createConnection(String databaseName) throws SQLException {
        return DriverManager.getConnection("jdbc:duckdb:md:" + databaseName + "?motherduck_token=" + token);
    }

    /**
     * Prepares a statement on the given connection with the given query and parameters.
     * 
     * Prepares a statement on the given connection with the given query and parameters.
     * If any of the parameters are String arrays, they are converted to SQL Arrays
     * and set as such on the statement. Otherwise, the parameters are set using
     * {@link PreparedStatement#setObject(int, Object)}.
     * 
     * @param conn   the connection to prepare the statement on
     * @param query  the query to prepare
     * @param params the parameters to set on the statement
     * @return the prepared statement
     * @throws SQLException if a database access error occurs
     */
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