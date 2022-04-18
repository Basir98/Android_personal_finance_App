package com.example.assignment1;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Controller controller = new Controller();
    MainActivity mainActivity = new MainActivity();

    @Test
    public void testUserInputSignupWithEmptyValues(){
        String username = "";
        String password = "";
        String balance = "";

        boolean result =  controller.checkUserInput(username, password, balance);
        assertEquals("This test case tests the user input with empty values", false, result);

    }

    @Test
    public void testUserInputSignupWithCorrectValues(){
        String username = "Test";
        String password = "test12345";
        String balance = "200";

        boolean result =  controller.checkUserInput(username, password, balance);
        assertEquals("This test case tests the user input with non empty values", true, result);
    }

    @Test
    public void testCheckUserPasswordWithEmptyPassword(){
        String password = "";
        boolean result =  controller.checkUserPassword(password);
        assertEquals(false, result);
    }

    @Test
    public void testCheckUserPasswordWithInvalidPassword(){
        String password = "12345test";
        boolean result =  controller.checkUserPassword(password);
        assertEquals(false, result);
    }

    @Test
    public void testCheckUserPasswordWithValidPassword(){
        String password = "12345Test";
        boolean result =  controller.checkUserPassword(password);
        assertEquals(true, result);
    }






}