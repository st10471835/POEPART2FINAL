import java.util.Scanner; // Import the Scanner class

public class MesageChat {

    private static int messageCounter = 0;
    private static final int SIZE = 100;
    private static Message[] messages = new Message[SIZE];


    static class Message {
        String id;
        String recipient;
        String text;
        String hash;

        public Message(String id, String recipient, String text, String hash) {
            this.id = id;
            this.recipient = recipient;
            this.text = text;
            this.hash = hash;
        }
    }

    // This method handles the user's options, looping until the user quits.
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Welcome to QuickChat ---");
            System.out.println("Choose an option:");
            System.out.println("1. Send Message");
            System.out.println("2. View Messages");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");

            String option = scanner.nextLine(); // Read user's option

            switch (option) {
                case "1":
                    System.out.print("How many messages do you want to send? ");
                    try {
                        int msgCount = Integer.parseInt(scanner.nextLine());
                        for (int i = 0; i < msgCount; i++) {
                            sendMessage(scanner); // Pass scanner to sendMessage
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                    break;
                case "2":
                    printMessages();
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close(); // Close the scanner when done
    }

    public void sendMessage(Scanner scanner) { // Accept Scanner as a parameter
        if (messageCounter >= SIZE) {
            System.out.println("Message storage full. Cannot send more messages.");
            return;
        }

        String recipient;
        do {
            System.out.print("Enter recipient's number (must start with +27 and have 9 digits after): ");
            recipient = scanner.nextLine();
        } while (!recipient.matches("^\\+27\\d{9}$"));

        System.out.print("Enter your message (max 250 characters): ");
        String messageText = scanner.nextLine();

        if (messageText.length() > 250) {
            System.out.println("Message exceeds 250 characters. Please reduce size.");
            return;
        }

        // Increment counter before creating ID
        messageCounter++;
        String messageID = String.format("%010d", messageCounter); // Formats to 10 digits, e.g., 0000000001
        String hash = createMessageHash(messageID, messageText);

        System.out.println("\nChoose action for this message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            // Store the message in the array. Adjust index for 0-based array.
            messages[messageCounter - 1] = new Message(messageID, recipient, messageText, hash);
            System.out.println("Message sent successfully!");
        } else if ("2".equals(choice)) {
            System.out.println("The message has been disregarded.");
            // This prevents the disregarded message from being counted, decrementing the counter.
            messageCounter--;
        } else {
            System.out.println("Invalid choice. Message was not sent.");
            messageCounter--; // Treat as disregarded if invalid choice
        }
    }

    public String createMessageHash(String id, String msg) {
        // Handle empty message to prevent ArrayIndexOutOfBoundsException for words
        if (msg == null || msg.trim().isEmpty()) {
            return id.substring(0, 2) + ":" + id.charAt(id.length() - 1) + "N/A";
        }
        String[] words = msg.split(" ");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        // If message has only one word, firstWord and lastWord are the same
        if (words.length == 1) {
            return id.substring(0, 2) + ":" + id.charAt(id.length() - 1) + firstWord;
        }

        return id.substring(0, 2) + ":" + id.charAt(id.length() - 1) +
                firstWord + lastWord;
    }


    public void printMessages() {
        if (messageCounter == 0) {
            System.out.println("No messages to show.");
        } else {
            System.out.println("\n--- Your Messages ---");
            for (int i = 0; i < messageCounter; i++) {
                Message msg = messages[i];
                if (msg != null) { //
                    System.out.println("ID: " + msg.id);
                    System.out.println("Hash: " + msg.hash);
                    System.out.println("To: " + msg.recipient);
                    System.out.println("Message: " + msg.text);
                    System.out.println("--------------------");
                }
            }
        }
    }



    }

