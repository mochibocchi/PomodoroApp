import com.example.pomodoroapp.Controller.RegisterController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterControllerTest {

    @Test
    public void testIsValidEmail_ValidEmail() {
        RegisterController controller = new RegisterController();
        assertTrue(controller.isValidEmail("test@example.com"));
    }

    @Test
    public void testIsValidEmail_InvalidEmail() {
        RegisterController controller = new RegisterController();
        assertFalse(controller.isValidEmail("email"));
    }

    @Test
    public void testIsValidPassword_ValidPassword() {
        RegisterController controller = new RegisterController();
        assertTrue(controller.isValidPassword("Password123!"));
    }

    @Test
    public void testIsValidPassword_InvalidPassword() {
        RegisterController controller = new RegisterController();
        assertFalse(controller.isValidPassword("pass"));
    }

    @Test
    public void testOnRegister_ValidData() {
        RegisterController controller = new RegisterController();

        String firstName = "John";
        String lastName = "Doe";
        String email = "john@example.com";
        String password = "Password123!";

        controller.setFirstNameTextField(firstName);
        controller.setLastNameTextField(lastName);
        controller.setEmailTextField(email);
        controller.setPasswordTextField(password);
        controller.onRegister(null);

    }
}