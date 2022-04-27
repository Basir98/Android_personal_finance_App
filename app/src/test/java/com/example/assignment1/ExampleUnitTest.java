package com.example.assignment1;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Controller controller = new Controller();

    @Test
    public void testUserInputSignupWithEmptyValues(){
        String username = "";
        String password = "";
        String balance = "";

        boolean result =  controller.checkUserInput(username, password, balance);
        assertFalse("This test case tests the user input with empty values", result);
    }

    @Test
    public void testUserInputSignupWithCorrectValues(){
        String username = "Test";
        String password = "test12345";
        String balance = "200";

        boolean result =  controller.checkUserInput(username, password, balance);
        assertTrue("This test case tests the user input with non empty values", result);
    }

    @Test
    public void testCheckUserPasswordWithEmptyPassword(){
        String password = "";
        boolean result =  controller.checkUserPassword(password);
        assertFalse(result);
    }

    @Test
    public void testCheckUserPasswordWithInvalidPassword(){
        String password = "12345test";
        boolean result =  controller.checkUserPassword(password);
        assertFalse(result);
    }

    @Test
    public void testCheckUserPasswordWithValidPassword(){
        String password = "12345Test";
        boolean result =  controller.checkUserPassword(password);
        assertTrue(result);
    }

    @Test
    public void testTruncateWithInvalidInput() {
        String test = "Test";
        String result = controller.truncate(test, 5);
        assertEquals("Test", result);
    }

    @Test
    public void testTruncateWithValidInput() {
        String test = "Test123";
        String result = controller.truncate(test, 5);
        assertEquals("Test1..", result);
    }




}