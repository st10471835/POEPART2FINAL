import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JonathanApp {

    // Simple user class for login validation
    static class User {
        String username;
        String password; // Insecure plain-text for demo only

        User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    // Message class to keep all message data together
    static class Message {
        String sender;
        String recipient;
        String content;
        String messageHash;
        String messageID;

        Message(String sender, String recipient, String content, String messageHash, String messageID) {
            this.sender = sender;
            this.recipient = recipient;
            this.content = content;
            this.messageHash = messageHash;
            this.messageID = messageID;
        }
    }

    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();

    private static User demoUser = new User("admin", "password123");

    public static void main(String[] args) {
        // Login
        boolean loggedIn = login();
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Exiting application due to failed login.", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        // Demo data to populate arrays
        populateDemoData();

        // Main application loop
        mainMenu();
    }

    private static boolean login() {
        for (int attempts = 0; attempts < 3; attempts++) {
            JPanel panel = new JPanel();
            JLabel labelUser = new JLabel("Username:");
            JTextField usernameField = new JTextField(15);
            JLabel labelPass = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(15);

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(labelUser);
            panel.add(usernameField);
            panel.add(labelPass);
            panel.add(passwordField);

            int option = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (demoUser.username.equals(username) && demoUser.password.equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. You have " + (2 - attempts) + " attempts left.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                return false; // Cancel pressed
            }
        }
        return false; // Exceeded attempts
    }

    private static void populateDemoData() {
        // Sent messages demo data
        sentMessages.add(new Message("Aphiwe", "+27834557896", "Did you get the cake?", "hash001", "msg001"));
        sentMessages.add(new Message("Ntando", "+27838954567", "it is dinner time", "hash003", "msg003"));

        // Disregarded messages demo data
        disregardedMessages.add(new Message("Siyanda", "+27837774567", "Where are you? you are late! i have asked you to be on time.", "hash004", "msg004"));
        disregardedMessages.add(new Message("Fanele", "+27834484567", "Yohoooo,I am at your gate", "hash005", "msg005"));

        // Stored messages demo data
        storedMessages.add(new Message("Nonhle", "+27838884567", "Project files attached.", "hash006", "msg006"));
        storedMessages.add(new Message("Nokwanda", "+27838764567", "OK, I am leaving without you.", "hash007", "msg007"));
    }

    private static void mainMenu() {
        String menuText = "Select an option:\n"
                + "1. Display sender and recipient of all sent messages\n"
                + "2. Display the longest sent message\n"
                + "3. Search for a message ID and display recipient and message\n"
                + "4. Delete a message using the message hash\n"
                + "5. Display a detailed report of all sent messages\n"
                + "6. Add a new sent message\n"
                + "7. Exit";

        while (true) {
            String input = JOptionPane.showInputDialog(null, menuText, "Messaging App Menu", JOptionPane.PLAIN_MESSAGE);
            if (input == null) {
                // User pressed cancel or closed dialog
                exitApp();
            }

            input = input.trim();
            if (input.isEmpty()) {
                continue;
            }

            switch (input) {
                case "1":
                    displaySendersRecipients();
                    break;
                case "2":
                    displayLongestSentMessage();
                    break;
                case "3":
                    searchMessageByID();
                    break;
                case "4":
                    deleteMessageByHash();
                    break;
                case "5":
                    displaySentMessagesReport();
                    break;
                case "6":
                    addNewSentMessage();
                    break;
                case "7":
                    exitApp();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1-7.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void displaySendersRecipients() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages available.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Sender -> Recipient\n");
        sb.append("--------------------\n");
        for (Message msg : sentMessages) {
            sb.append(msg.sender).append(" -> ").append(msg.recipient).append("\n");
        }

        showScrollableMessage(sb.toString(), "All Sent Messages (Sender and Recipient)");
    }

    private static void displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages available.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.content.length() > longest.content.length()) {
                longest = msg;
            }
        }

        String report = "Longest Sent Message:\n"
                + "Sender: " + longest.sender + "\n"
                + "Recipient: " + longest.recipient + "\n"
                + "Message ID: " + longest.messageID + "\n"
                + "Message Hash: " + longest.messageHash + "\n"
                + "Content:\n" + longest.content;

        showScrollableMessage(report, "Longest Sent Message");
    }

    private static void searchMessageByID() {
        String searchID = JOptionPane.showInputDialog(null, "Enter Message ID to search:", "Search Message", JOptionPane.PLAIN_MESSAGE);
        if (searchID == null || searchID.trim().isEmpty()) {
            return;
        }
        searchID = searchID.trim();

        for (Message msg : sentMessages) {
            if (msg.messageID.equalsIgnoreCase(searchID)) {
                String result = "Message ID: " + msg.messageID + "\n"
                        + "Recipient: " + msg.recipient + "\n"
                        + "Content:\n" + msg.content;
                showScrollableMessage(result, "Message Found");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID '" + searchID + "' not found in sent messages.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void deleteMessageByHash() {
        String hashToDelete = JOptionPane.showInputDialog(null, "Enter Message Hash to delete:", "Delete Message", JOptionPane.PLAIN_MESSAGE);
        if (hashToDelete == null || hashToDelete.trim().isEmpty()) {
            return;
        }
        hashToDelete = hashToDelete.trim();

        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equalsIgnoreCase(hashToDelete)) {
                Message removed = sentMessages.remove(i);
                JOptionPane.showMessageDialog(null, "Deleted message:\nSender: " + removed.sender + "\nRecipient: " + removed.recipient + "\nMessage ID: " + removed.messageID, "Deleted", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message hash '" + hashToDelete + "' not found in sent messages.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void displaySentMessagesReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages available.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Sent Messages Full Report\n");
        sb.append("-------------------------\n");
        for (Message msg : sentMessages) {
            sb.append("Message ID: ").append(msg.messageID).append("\n");
            sb.append("Message Hash: ").append(msg.messageHash).append("\n");
            sb.append("Sender: ").append(msg.sender).append("\n");
            sb.append("Recipient: ").append(msg.recipient).append("\n");
            sb.append("Content: ").append(msg.content).append("\n");
            sb.append("-------------------------\n");
        }

        showScrollableMessage(sb.toString(), "Sent Messages Report");
    }

    private static void addNewSentMessage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField senderField = new JTextField();
        JTextField recipientField = new JTextField();
        JTextArea messageArea = new JTextArea(5, 20);
        JTextField messageHashField = new JTextField();
        JTextField messageIDField = new JTextField();

        panel.add(new JLabel("Sender:"));
        panel.add(senderField);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Recipient:"));
        panel.add(recipientField);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Message Content:"));
        panel.add(new JScrollPane(messageArea));
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Message Hash:"));
        panel.add(messageHashField);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Message ID:"));
        panel.add(messageIDField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Add New Sent Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String sender = senderField.getText().trim();
            String recipient = recipientField.getText().trim();
            String content = messageArea.getText().trim();
            String hash = messageHashField.getText().trim();
            String id = messageIDField.getText().trim();

            if (sender.isEmpty() || recipient.isEmpty() || content.isEmpty() || hash.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required. Message not added.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check for duplicate ID or hash
            for (Message msg : sentMessages) {
                if (msg.messageID.equalsIgnoreCase(id)) {
                    JOptionPane.showMessageDialog(null, "Message ID already exists. Message not added.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (msg.messageHash.equalsIgnoreCase(hash)) {
                    JOptionPane.showMessageDialog(null, "Message Hash already exists. Message not added.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            sentMessages.add(new Message(sender, recipient, content, hash, id));
            JOptionPane.showMessageDialog(null, "Message added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void exitApp() {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Thank you for using the Messaging App. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private static void showScrollableMessage(String message, String title) {
        JTextArea textArea = new JTextArea(message, 20, 50);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}

