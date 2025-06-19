import javax.swing.*;
import java.util.ArrayList;


public class Main {

    private static final ArrayList<String> firstNames = new ArrayList<>();
    private static final ArrayList<String> lastNames = new ArrayList<>();
    private static final ArrayList<String> userNames = new ArrayList<>();
    private static final ArrayList<String> passwords = new ArrayList<>();
    private static final ArrayList<String> cellNumbers = new ArrayList<>();

    public static void main(String[] args) {
        Login loginSystem = new Login();
        Message messageSystem = new Message();

        while (true) {
            String[] options = {"Login", "Register", "View Users", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Welcome to ChatBot!",
                    "ChatBot",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0 -> {
                    if (tryLogin(loginSystem)) {
                        runApplication(messageSystem);
                    }
                }
                case 1 -> registerNewUser(loginSystem);
                case 2 -> showAllUsers();
                case 3 -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    System.exit(0);
                }
                default -> System.exit(0);
            }
        }
    }

    private static boolean tryLogin(Login loginSystem) {
        String inputUsername = JOptionPane.showInputDialog(null, "=== Login ===\nEnter username:");

        if (inputUsername == null) {

            return false;
        }

        if (!userNames.contains(inputUsername)) {
            JOptionPane.showMessageDialog(null, "Username not found. Please register first.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String inputPassword = JOptionPane.showInputDialog(null, "Enter password:");

        if (inputPassword == null) {
            return false;
        }

        int userIndex = userNames.indexOf(inputUsername);

        if (passwords.get(userIndex).equals(inputPassword)) {
            JOptionPane.showMessageDialog(null, "Login successful. Welcome " + inputUsername + "!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static void registerNewUser(Login loginSystem) {
        String firstname = JOptionPane.showInputDialog(null, "Enter your First Name:");
        if (firstname == null) {
            return;
        }
        firstname = firstname.toUpperCase();

        String lastname = JOptionPane.showInputDialog(null, "Enter your Last Name:");
        if (lastname == null) {
            return;
        }
        lastname = lastname.toUpperCase();

        String username;
        do {
            username = JOptionPane.showInputDialog(null, "Enter your username (must be 5 chars with underscore):");
            if (username == null) {
                return;
            }
            username = username.toLowerCase();

            if (loginSystem.checkUserName(username)) {
                JOptionPane.showMessageDialog(null, "Invalid username. Must be 5 characters long and contain an underscore.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (loginSystem.checkUserName(username));

        String cellphone;
        do {
            cellphone = JOptionPane.showInputDialog(null, "Enter your cellphone number (SA format):");
            if (cellphone == null) {
                return;
            }

            if (loginSystem.checkCellPhoneNumber(cellphone)) {
                JOptionPane.showMessageDialog(null, "Invalid cellphone. Must be in SA format (+27 or 0 followed by 9 digits).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (loginSystem.checkCellPhoneNumber(cellphone));

        String password;
        do {
            password = JOptionPane.showInputDialog(null, "Enter your password:");
            if (password == null) {
                return;
            }

            if (loginSystem.checkUserPassword(password)) {
                JOptionPane.showMessageDialog(null,
                        "Weak password. Must have:\n- At least 8 characters\n- 1 uppercase\n- 1 number\n- 1 special character",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (loginSystem.checkUserPassword(password));

        firstNames.add(firstname);
        lastNames.add(lastname);
        userNames.add(username);
        passwords.add(password);
        cellNumbers.add(cellphone);

        JOptionPane.showMessageDialog(null, "Registered successfully!\nUsername: " + username);
    }

    private static void showAllUsers() {
        if (userNames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users registered yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== REGISTERED USERS ===\n");
        for (int i = 0; i < userNames.size(); i++) {
            sb.append("User ").append(i + 1).append(":\n")
                    .append(" Name: ").append(firstNames.get(i)).append(" ").append(lastNames.get(i)).append("\n")
                    .append(" Username: ").append(userNames.get(i)).append("\n")
                    .append(" Cell: ").append(cellNumbers.get(i)).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void runApplication(Message messageSystem) {
        boolean running = true;
        while (running) {
            String option = JOptionPane.showInputDialog(null, """
                                                              Choose an option:
                                                              1. Send Message
                                                              2. Show Recently Sent Messages
                                                              3. Message Management
                                                              4. View All Users
                                                              5. Quit""");

            if (option == null) {
                option = "5";
            }

            switch (option) {
                case "1" -> messageSystem.sendMessagesInteraction();
                case "2" -> {
                    messageSystem.printAllMessages();
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + messageSystem.getTotalMessages());
                }
                case "3" -> messageSystem.messageManagementMenu();
                case "4" -> showAllUsers();
                case "5" -> running = false;
                default -> JOptionPane.showMessageDialog(null, "Invalid choice.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}