# ProjectChatBot

# ChatBot Application

This Java application implements a simple ChatBot using Swing for the GUI, MySQL for storing messages and responses, and integrates with Google search for queries without predefined responses.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [How to Run](#how-to-run)
- [Database Setup](#database-setup)
- [Contributing](#contributing)


## Overview

The ChatBot application is a graphical user interface (GUI) application that allows users to have a conversation with a chatbot. The chatbot responds based on predefined responses in the database or performs a Google search for queries it does not have responses for.

The application is divided into three main components:
1. **ChatBotApp**: The main GUI class that sets up the user interface, handles user interactions, and displays the chat history.
2. **ChatBotLogic**: The logic class that generates responses for user messages using a predefined database of questions and answers.
3. **DBConnection**: Manages the connection to the MySQL database where the chat history and predefined responses are stored.

## Features

- GUI interface for user interaction and displaying the chat history.
- Predefined responses based on user queries using a MySQL database.
- Integration with Google search for queries without predefined responses.
- Storage of conversation history in a MySQL database.

## Usage

To use the ChatBot application, follow these steps:

1. **Run the application**: Compile and run the `ChatBotApp` class to start the GUI interface.

2. **Interact with the ChatBot**:
   - Enter a message in the input field at the bottom.
   - Click the "Send" button to send the message to the chatbot.
   - The chatbot will respond based on the predefined responses or perform a Google search for the query.

## Dependencies

- Java SE Development Kit (JDK)
- MySQL Database

## How to Run

1. **Compile the Java files**:
   ```
   javac com/chat/bot/*.java
   ```

2. **Run the ChatBot application**:
   ```
   java com.chat.bot.ChatBotApp
   ```

## Database Setup

1. **MySQL Database Setup**:
   - Ensure you have MySQL installed and running.
   - Create a database named `chatbot_db`.

2. **Database Tables**:
   - Create a table named `texts` with columns `question` and `answer` to store predefined questions and answers.
   - Create a table named `messages` with a column `message` to store conversation history.

   ```sql
   CREATE TABLE texts (
       question VARCHAR(255) PRIMARY KEY,
       answer VARCHAR(255)
   );

   CREATE TABLE messages (
       id INT AUTO_INCREMENT PRIMARY KEY,
       message TEXT
   );
   ```

3. **Populate Predefined Responses**:
   - Insert predefined questions and answers into the `texts` table.

## Contributing

Contributions to the ChatBot application are welcome! Feel free to submit issues, pull requests, or suggest enhancements.

