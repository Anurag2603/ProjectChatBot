package com.chat.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatBotLogic {
    private Connection connection;

    public ChatBotLogic() {
        // Initialize database connection
        this.connection = DBConnection.getConnection();
    }

    public String generateResponse(String userMessage) {
        // Fetch response from the database based on user input
        String query = "SELECT answer FROM texts WHERE question = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userMessage.toLowerCase());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("answer");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred while fetching the response.";
        }
    }
}

