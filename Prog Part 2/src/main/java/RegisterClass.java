// RegisterClass.java
import java.util.Scanner;

public class RegisterClass { // Top-level class
    protected String username;
    protected String password;
    protected String phone; // Added phone field

    public RegisterClass() {

    }

    public RegisterClass(String username, String password) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in); // Local scanner for this method
        System.out.println("--- User Registration ---");


        while (true) {
            System.out.print("Please enter your username: ");
            String tempUsername = scanner.nextLine();
            if (validateUsername(tempUsername)) {
                this.username = tempUsername;
                break;
            } else {
                System.out.println("Invalid username. Must be 5+ characters and contain '_'.");
            }
        }

        // Loop until a valid password is entered
        while (true) {
            System.out.print("Please enter your password: ");
            String tempPassword = scanner.nextLine();
            if (validatePassword(tempPassword)) {
                this.password = tempPassword;
                break;
            } else {
                System.out.println("Invalid password. Must be 8+ characters, with uppercase, lowercase, number, and special character.");
            }
        }

        // Loop until a valid phone number is entered
        while (true) {
            System.out.print("Enter phone number must start with +27 : ");
            String tempPhone = scanner.nextLine();
            if (validatePhone(tempPhone)) {
                this.phone = tempPhone;
                break;
            } else {
                System.out.println("Invalid phone number. Must start with '+27' and be followed by 9 digits.");
            }
        }

        System.out.println("Registration successful for user: " + this.username);
        // In a real application, you would store this in a database
    }

    // Private helper method for username validation
    private boolean validateUsername(String username) {
        return username.length() >= 5 && username.contains("_");
    }

    // Private helper method for password validation
    private boolean validatePassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^A-Za-z0-9].*");
    }

    // Private helper method for phone number validation
    private boolean validatePhone(String phone) {
        return phone.matches("^\\+27\\d{9}$");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() { // Added getter for phone
        return phone;
    }
}