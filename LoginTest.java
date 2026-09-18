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
}