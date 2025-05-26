

import java.util.Scanner;

public class LoginClass extends RegisterClass { // Top-level class

    public LoginClass(String username, String password) {
        super( username, password); // Call the superclass constructor
    }

    public LoginClass() {

    }

    public boolean loginUser() {
        Scanner scanner = new Scanner(System.in); // Local scanner for this method
        System.out.println("--- User Login ---");
        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        // Compare with the username and password inherited/set from the superclass
        if (enteredUsername.equals(this.username) && enteredPassword.equals(this.password)) {
            System.out.println("Login successful! Welcome, " + this.username + "!");
            // scanner.close(); // Not closing here to avoid closing System.in prematurely
            return true;
        } else {
            System.out.println("Invalid username or password.");
            // scanner.close(); // Not closing here to avoid closing System.in prematurely
            return false;
        }
    }
}