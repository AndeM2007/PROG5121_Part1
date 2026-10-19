package prog5121_part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    
    @Test
    public void testCorrectUsername() {
        Login login = new Login();
        assertTrue(login.checkUserName("ky_1"));
    }

    @Test
    public void testIncorrectUsername() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCorrectPassword() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testIncorrectPassword() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCorrectCellNumber() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testIncorrectCellNumber() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // --- Dedicated Tests for Login Authentication & Status Messages ---

    @Test
    public void testLoginUserSuccess() {
        Login login = new Login();
        login.registerUser("ky_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.loginUser("ky_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUserFailure() {
        Login login = new Login();
        login.registerUser("ky_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertFalse(login.loginUser("ky_1", "wrongPass"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        Login login = new Login();
        login.registerUser("ky_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        String expectedMessage = "Welcome Kyle, Smith it is great to see you again.";
        assertEquals(expectedMessage, login.returnLoginStatus(true));
    }

    @Test
    public void testReturnLoginStatusFailure() {
        Login login = new Login();
        String expectedMessage = "Username or password incorrect, please try again.";
        assertEquals(expectedMessage, login.returnLoginStatus(false));
    }
}
