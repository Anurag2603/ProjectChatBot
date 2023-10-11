package com.chat.bot;

import java.sql.*;



public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatbot_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Anurag2603";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
