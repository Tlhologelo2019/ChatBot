import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Message {
    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> disregardedMessages = new ArrayList<>();
    private final ArrayList<String> storedMessages = new ArrayList<>();
    private final ArrayList<String> messageHash = new ArrayList<>();
    private final ArrayList<String> messageId = new ArrayList<>();
    private final ArrayList<String> senders = new ArrayList<>();
    private final ArrayList<String> recipientscellPhone = new ArrayList<>();
    private final Random random = new Random();

    public void sendMessagesInteraction() {
        String sender = JOptionPane.showInputDialog(null, "Enter sender name:");
        if (sender == null) {
            return;
        }

        String recipientCell;
        do {
            recipientCell = JOptionPane.showInputDialog(null, "Enter recipient cell number (+27 format):");
            if (recipientCell == null) {
                return;
            }

            String cleanedNumber = recipientCell.replaceAll("\\s+", "");
            if (!cleanedNumber.matches("^\\+27[0-9]{9}$")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid cell number format. Must be in +27XXXXXXXXX format (11 digits total)",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!recipientCell.replaceAll("\\s+", "").matches("^\\+27[0-9]{9}$"));

        String messageCountStr = JOptionPane.showInputDialog(null, "How many messages would you like to send?");
        if (messageCountStr == null) {
            return;
        }

        try {
            int messageCount = Integer.parseInt(messageCountStr);

            for (int i = 0; i < messageCount; i++) {
                String message = JOptionPane.showInputDialog(null,
                        String.format("Enter message content (%d of %d):", i + 1, messageCount));
                if (message == null) {
                    break;
                }

                String[] options = {"Send", "Store", "Disregard"};
                int action = JOptionPane.showOptionDialog(null,
                        "What would you like to do with this message?",
                        "Message Action",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                String id = generateMessageId();

                switch (action) {
                    case 0 -> {

                        sentMessages.add(message);
                        senders.add(sender);
                        recipientscellPhone.add(recipientCell);
                        messageId.add(id);

                        int messageNumber = sentMessages.size();
                        String hash = generateHashId(id, messageNumber);
                        messageHash.add(hash);

                        JOptionPane.showMessageDialog(null,
                                "Message sent successfully!\nMessage ID: " + id
                                        + "\nHash ID: " + hash);
                    }

                    case 1 -> {

                        storedMessages.add(message);
                        JOptionPane.showMessageDialog(null, "Message stored successfully!");
                    }

                    case 2 -> {

                        disregardedMessages.add(message);
                        JOptionPane.showMessageDialog(null, "Message disregarded and stored in archive.");
                    }

                    default -> {

                        return;
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateMessageId() {
        String newId;
        do {
            int firstDigit = 1 + random.nextInt(9);
            int remainingDigits = random.nextInt(1_000_000_000);
            newId = firstDigit + String.format("%09d", remainingDigits);
        } while (messageId.contains(newId));
        return newId;
    }

    private void sendStoredMessages(){
        if (storedMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No stored messages available.");
            return;
        }

        String[] storedArray = storedMessages.toArray(new String[0]);
        String selectedMessage = (String) JOptionPane.showInputDialog(
                null,
                "Select a stored message to send:",
                "Send Stored Message",
                JOptionPane.QUESTION_MESSAGE,
                null,
                storedArray,
                storedArray[0]
        );
        if (selectedMessage == null) {
            return;
        }

        int messageIndex = storedMessages.indexOf(selectedMessage);
        if (messageIndex == -1) {
            JOptionPane.showMessageDialog(null, "Error locating selected message.");
            return;
        }

        String sender = JOptionPane.showInputDialog(null, "Enter sender name");
        if (sender == null || sender.trim().isEmpty()) {
            return;
        }

        String recipientCell;
        do {
            recipientCell = JOptionPane.showInputDialog(null, "Enter recipient cell number (+27 format):");
            if (recipientCell == null) {
                return;
            }

            String cleanedNumber = recipientCell.replaceAll("\\s+", "");
            if (!cleanedNumber.matches("^\\+27[0-9]{9}$")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid cell number format. Must be in +27XXXXXXXXX format (11 digits total)",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!recipientCell.replaceAll("\\s+", "").matches("^\\+27[0-9]{9}$"));

        String id = generateMessageId();
        int messageNumber = sentMessages.size() + 1;
        String hash = generateHashId(id, messageNumber);

        sentMessages.add(selectedMessage);
        storedMessages.remove(messageIndex);
        senders.add(sender);
        recipientscellPhone.add(recipientCell);
        messageId.add(id);
        messageHash.add(hash);

        JOptionPane.showMessageDialog(null,
                "Stored message sent successfully!\nMessage ID: " + id
                        + "\nHash ID: " + hash);
    }

    private String generateHashId(String messageId, int messageNumber) {
        return messageId.substring(0, 2) + ":" + messageNumber;
    }

    public void printAllMessages() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to display.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== SENT MESSAGES ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("Message ").append(i + 1).append(":\n")
                    .append(" From: ").append(senders.get(i)).append("\n")
                    .append(" To: ").append(recipientscellPhone.get(i)).append("\n")
                    .append(" Content: ").append(sentMessages.get(i)).append("\n")
                    .append(" ID: ").append(messageId.get(i)).append("\n")
                    .append(" Hash: ").append(messageHash.get(i)).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public int getTotalMessages() {
        return sentMessages.size();
    }

    public void messageManagementMenu() {
        while (true) {
            String option = JOptionPane.showInputDialog(null, """
                                                              ==== Message Management ====
                                                              1. Show disregarded messages
                                                              2. Show all stored messages
                                                              3. Send stored messages
                                                              4. Delete a message by Hash ID
                                                              5. Display full report
                                                              6. Search for a message
                                                              7. Display the longest message
                                                              8. Back to main menu
                                                              Choose an option:""");

            if (option == null) {
                option = "8";
            }

            switch (option) {
                case "1" ->
                        showDisregardedMessages();
                case "2" ->
                        showAllStoredMessages();
                case "3" ->
                        sendStoredMessages();
                case "4" ->
                        deleteMessageByHash();
                case "5" ->
                        displayFullReport();
                case "6" ->
                        searchMessageByHashId();
                case "7" ->
                        findLongestMessage();
                case "8" -> {
                    return;
                }
                default ->
                        JOptionPane.showMessageDialog(null, "Invalid choice.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchMessageByHashId(){
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to search.");
            return;
        }

        String hashId = JOptionPane.showInputDialog(null, "Enter the Hash ID to search for a message: ");
        if (hashId == null || hashId.trim().isEmpty()){
            return;
        }

        int index = messageHash.indexOf(hashId);
        if (index == -1){
            JOptionPane.showMessageDialog(null, "No message exists with this Hash ID: " + hashId, "Not Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String sb = "=== MESSAGE DETAILS ===\n" + "Hash ID: " + messageHash.get(index) + "\n" +
                "Message ID: " + messageId.get(index) + "\n" +
                "From: " + senders.get(index) + "\n" +
                "To: " + recipientscellPhone.get(index) + "\n" +
                "Content: " + sentMessages.get(index) + "\n" +
                "Length: " + sentMessages.get(index).length() + "Characters long\n";

        JOptionPane.showMessageDialog(null, sb);
    }

    private void showDisregardedMessages() {
        if (disregardedMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No disregarded messages.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== DISREGARDED MESSAGES ===\n");
        for (int i = 0; i < disregardedMessages.size(); i++) {
            sb.append(i + 1).append(". ").append(disregardedMessages.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void showAllStoredMessages() {
        if (storedMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No stored messages.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== ALL STORED MESSAGES ===\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            sb.append(i + 1).append(". ").append(storedMessages.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void findLongestMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to analyze the longest.");
            return;
        }

        int maxLength = -1;
        int index = -1;

        for (int i = 0; i < sentMessages.size(); i++){
            int currentLength = sentMessages.get(i).length();
            if (currentLength > maxLength) {
                maxLength = currentLength;
                index = i;
            }
        }

        String sb = "=== LONGEST MESSAGE ===\n" + "Length: " + maxLength + " characters long\n" +
                "Hash ID: " + messageHash.get(index) + "\n" +
                "Message ID: " + messageId.get(index) + "\n" +
                "From: " + senders.get(index) + "\n" +
                "To: " + recipientscellPhone.get(index) + "\n" +
                "Message: " + sentMessages.get(index) + "\n";

        JOptionPane.showMessageDialog(null, sb);
    }

    private void deleteMessageByHash() {

        String hash = JOptionPane.showInputDialog(null, "Enter Hash ID to delete:");
        if (hash == null) {
            return;
        }

        int index = messageHash.indexOf(hash);
        if (index != -1) {
            disregardedMessages.add(sentMessages.get(index));
            sentMessages.remove(index);
            senders.remove(index);
            recipientscellPhone.remove(index);
            messageId.remove(index);
            messageHash.remove(index);
            JOptionPane.showMessageDialog(null, "Message deleted and moved to disregarded.");
        } else {
            JOptionPane.showMessageDialog(null, "Message not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayFullReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to display.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== FULL MESSAGE REPORT ===\n");
        sb.append("------------------------------------------------------------------\n");
        sb.append(String.format("| %-5s | %-15s | %-15s | %-20s | %-12s | %-8s |\n",
                "No.", "Sender", "Recipient", "Message", "Message ID", "Hash ID"));
        sb.append("------------------------------------------------------------------\n");

        for (int i = 0; i < sentMessages.size(); i++) {
            String message = sentMessages.get(i);
            if (message.length() > 20) {
                message = message.substring(0, 17) + "...";
            }

            sb.append(String.format("| %-5d | %-15s | %-15s | %-20s | %-12s | %-8s |\n",
                    i + 1,
                    senders.get(i),
                    recipientscellPhone.get(i),
                    message,
                    messageId.get(i),
                    messageHash.get(i)));
        }
        sb.append("------------------------------------------------------------------\n");
        sb.append("Total Sent Messages: ").append(sentMessages.size()).append("\n");
        sb.append("Total Disregarded Messages: ").append(disregardedMessages.size()).append("\n");
        sb.append("Total Stored Messages: ").append(storedMessages.size()).append("\n");

        JOptionPane.showMessageDialog(null, sb.toString());
    }

}
