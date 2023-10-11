package com.chat.bot;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBotApp extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private ChatBotLogic chatBot;
    private Connection connection;

    public ChatBotApp() {
        // Initialize GUI components
        chatArea = new JTextArea();
        messageField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        chatBot = new ChatBotLogic();

        // Set up the JFrame
        setTitle("ChatBot");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(createChatArea(), BorderLayout.CENTER);
        add(messageField, BorderLayout.SOUTH);
        add(sendButton, BorderLayout.EAST);

        // Initialize database connection
        connection = DBConnection.getConnection();

        // Apply some design enhancements
        designEnhancements();

        // Display a welcome message
        appendMessageToChat("ChatBot : Welcome! How can I assist you today?");
    }

    private JScrollPane createChatArea() {
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Automatic scrolling
        DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        return scrollPane;
    }

    private void designEnhancements() {
        // Set background and foreground colors for components
        chatArea.setBackground(Color.WHITE);
        chatArea.setForeground(Color.BLACK);
        messageField.setBackground(Color.WHITE);
        messageField.setForeground(Color.BLACK);
        sendButton.setBackground(Color.BLUE);
        sendButton.setForeground(Color.WHITE);

        // Set font for components
        Font font = new Font("Arial", Font.PLAIN, 14);
        chatArea.setFont(font);
        messageField.setFont(font);
        sendButton.setFont(font);

        // Decrease send button size
        sendButton.setPreferredSize(new Dimension(70, 30));
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userMessage = messageField.getText();
            appendMessageToChat("You : " + userMessage);
            messageField.setText(""); // Clear the input field

            // Get the chatbot's response
            String botResponse = null;
            if (userMessage.equals("")) {
                appendMessageToChat("ChatBot : Please Provide a Command");
            } else {
                botResponse = chatBot.generateResponse(userMessage);
                if (botResponse == null) {
                    appendMessageToChat("Here is the google search to " + userMessage);
                    try {
                        googleSearch(userMessage);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    appendMessageToChat("ChatBot : " + botResponse);
                }
            }

            // Store the conversation in the database
            storeMessageInDatabase(userMessage);
            storeMessageInDatabase("ChatBot : " + botResponse);
        }

        private void storeMessageInDatabase(String message) {
            try {
                String insertQuery = "INSERT INTO messages (message) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, message);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void appendMessageToChat(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        chatArea.append("[" + formattedDate + "] " + message + "\n");
    }

    private void googleSearch(String toSearch) throws IOException {
        String searchURL = "https://www.google.com/search?q=" + URLEncoder.encode(toSearch, "UTF-8");
        try {
            Desktop.getDesktop().browse(new URI(searchURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    
}
