
public class Main { // Top-level class
    public static void main(String[] args) {
        // --- Registration ---
        RegisterClass newUser = new RegisterClass();
        newUser.registerUser();


        LoginClass userLogin = new LoginClass(newUser.getUsername(), newUser.getPassword());
        boolean loginSuccess = userLogin.loginUser();
        if (loginSuccess) {
            System.out.println("Application flow continues after successful login.");

            MesageChat app = new MesageChat(); // Corrected class name
            app.menu(); // Call the menu method
        } else {
            System.out.println("Application flow ends due to failed login.");
        }
    }
}