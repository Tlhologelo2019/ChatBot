# ChatBot

# QuickChatBot - SMS-like Messaging Application

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Swing](https://img.shields.io/badge/GUI-Swing-orange)

QuickChatBot is a Java-based messaging application that simulates SMS functionality with advanced message management features. Built with Java Swing, it demonstrates core OOP principles and efficient data handling using ArrayLists.

## Features

### Core Messaging
- Send messages with validated sender/recipient information
- South African cell number validation (+27 format)
- Message storage options (Send/Store/Disregard)
- Automatic unique ID generation for each message

### Message Management
- Search messages by Hash ID
- Find the longest sent message (character count)
- 🗑Delete messages (moves to disregarded archive)
- Generate comprehensive message reports

### Data Organization
- Separate storage for sent, stored, and disregarded messages
- Hash ID system for quick message reference (Format: ID_PREFIX:MESSAGE_NUMBER)
- Real-time message statistics tracking

## Technical Implementation

### Data Structures
```java
private ArrayList<String> sentMessages
private ArrayList<String> disregardedMessages
private ArrayList<String> storedMessages
private ArrayList<String> messageHash
private ArrayList<String> messageId
private ArrayList<String> senders
private ArrayList<String> recipientscellPhone

Requirements:
  Java 17+
  JOptionPane (included in standard Java libraries)

How to Run:
  Clone repository
  Compile: javac QuickChatBot.java
  Run: java QuickChatBot

Future Enhancements:
  Stored messages to be later sent
  Undo desregaded messages
  Database persistence
  Message encryption
  Contact management system
  Message threading
