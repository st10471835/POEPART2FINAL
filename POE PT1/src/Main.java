import java.util.Scanner;


class Account {

    private String username;

    private String password;

    private String cellphone;

    private String firstName;

    private String surname;


    public Account(String uName, String pWord, String phone, String fName, String lName) {

        username = uName;

        password = pWord;

        cellphone = phone;

        firstName = fName;

        surname = lName;

    }


    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getFirstName() { return firstName; }

    public String getSurname() { return surname; }

}


class Validator {

    public static boolean isUsernameValid(String username) {

        if (username.contains("_") && username.length() > 5) {

            System.out.println("Username has been successfully captured.");

            return true;

        } else {

            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is not longer than 5 characters in length.");

            return false;

        }

    }


    public static boolean isPasswordComplex(String password) {

        boolean hasUppercase = !password.equals(password.toLowerCase());

        boolean hasDigit = password.matches(".*\\d.*");

        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()-+=<>?/{}~|].*");

        boolean longEnough = password.length() >= 8;


        if (hasUppercase && hasDigit && hasSpecialChar && longEnough) {

            System.out.println("Password is successfully captured.");

            return true;

        } else {

            System.out.println("Password is not correctly formatted. It must contain at least one uppercase letter, one digit, one special character, and be at least 8 characters long.");

            return false;

        }

    }


    public static boolean isCellphoneValid(String cellphone) {

        if (cellphone.startsWith("+27") && cellphone.length() == 12) {

            System.out.println("Your number is correct.");

            return true;

        } else {

            System.out.println("Cellphone number is incorrectly formatted or does not contain international code.");

            return false;

        }

    }

}


class AccountService {

    private Account registeredAccount;


    public String register(String username, String password, String cellphone, String firstName, String surname) {

        if (!Validator.isUsernameValid(username)) {

            return "Username is incorrectly formatted.";

        }

        if (!Validator.isPasswordComplex(password)) {

            return "Password does not meet the complexity requirements.";

        }

        if (!Validator.isCellphoneValid(cellphone)) {

            return "Cellphone number is incorrectly formatted.";

        }


        registeredAccount = new Account(username, password, cellphone, firstName, surname);

        return "Account has been successfully registered.";

    }


    public boolean login(String username, String password) {

        return registeredAccount != null &&

                username.equals(registeredAccount.getUsername()) &&

                password.equals(registeredAccount.getPassword());

    }


    public String getWelcomeMessage(boolean loginSuccess) {

        if (loginSuccess) {

            return "Welcome " + registeredAccount.getFirstName() + " " + registeredAccount.getSurname() + ", it is great to see you again!";

        } else {

            return "Login failed. Please check your username and password.";

        }

    }

}


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AccountService accountService = new AccountService();


        System.out.println("Please enter a username: ");

        String username = sc.next();


        System.out.println("Please enter a password: ");

        String password = sc.next();


        System.out.println("Please enter your cellphone number: ");

        String cellNumber = sc.next();


        System.out.println("Please enter your first name: ");

        String firstName = sc.next();


        System.out.println("Please enter your surname: ");

        String surname = sc.next();


        String result = accountService.register(username, password, cellNumber, firstName, surname);

        System.out.println(result);


        if (result.equals("Account has been successfully registered.")) {

            System.out.println("\n=== LOGIN ===");

            System.out.print("Enter your username: ");

            String loginUsername = sc.next();


            System.out.print("Enter your password: ");

            String loginPassword = sc.next();


            boolean loginSuccess = accountService.login(loginUsername, loginPassword);

            System.out.println(accountService.getWelcomeMessage(loginSuccess));

        }


        sc.close();

    }

}
