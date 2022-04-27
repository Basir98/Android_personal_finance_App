package com.example.assignment1;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RunWith(AndroidJUnit4.class)
public class DBTesting {
    private DatabaseHelper databaseHelper;
    Context context = getApplicationContext();
    DatabaseController databaseController = new DatabaseController();

    @Test
    public void testInsertingDataToDatabase(){
        databaseHelper= new DatabaseHelper(context);

        boolean result1 = databaseHelper.insertData("Test", "12345", 0);

        assertTrue(result1);
    }

    @Test
    public void testCheckUsername(){
        databaseHelper= new DatabaseHelper(context);
        boolean result = databaseHelper.insertData("Test", "12345", 0);

        assertTrue(result);

        Boolean checkUser = databaseHelper.checkusername("Test");
        assertEquals(true, checkUser);
        databaseController.handleDeleteUser("Test", databaseHelper);
    }

    @Test
    public void testSignupWithInValidUserinfo(){

        databaseHelper= new DatabaseHelper(context);
        String username = "Test";
        String password = "test12345";
        String balance = "200";

        databaseController.handleSignup(username, password, balance, databaseHelper);
        boolean result = databaseController.handleSignup(username, password, balance, databaseHelper);
        assertFalse(result);
        databaseController.handleDeleteUser(username, databaseHelper);
    }

    @Test
    public void testSignupWithValidUserinfo(){
        databaseHelper= new DatabaseHelper(context);
        String username = "newtest";
        String password = "12345Test";
        String balance = "200";

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        boolean result = databaseController.handleSignup(username, bcryptHashString, balance, databaseHelper);
        assertTrue(result);
        databaseController.handleDeleteUser(username, databaseHelper);
    }

    @Test
    public void testGetHashedPasswordWithInvalidUserInfo(){
        databaseHelper= new DatabaseHelper(context);
        String username = "Test";
        String password = "Test";

        boolean result = databaseController.handleHashedPassword(username, password, databaseHelper);
        assertFalse(result);
    }

    @Test
    public void testGetHashedPasswordWithValidUserInfo(){
        databaseHelper= new DatabaseHelper(context);
        String username = "newtest";
        String password = "12345Test";

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        boolean result = databaseController.handleSignup(username, bcryptHashString, "100", databaseHelper);
        boolean resultHashedPass = databaseController.handleHashedPassword(username, password, databaseHelper);

        assertTrue(result);
        assertTrue(resultHashedPass);

        databaseController.handleDeleteUser(username, databaseHelper);
    }

    @Test
    public void testHandleDeleteUserWithInvalidCondition(){
        databaseHelper= new DatabaseHelper(context);
        String username = "newtest";
        String password = "12345Test";

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        boolean result = databaseController.handleSignup(username, bcryptHashString, "100", databaseHelper);
        assertTrue(result);

        boolean newResult = databaseController.handleSignup(username, bcryptHashString, "100", databaseHelper);
        assertFalse(newResult);

        databaseController.handleDeleteUser(username, databaseHelper);
    }

    @Test
    public void testHandleDeleteUserWithValidCondition(){
        databaseHelper= new DatabaseHelper(context);
        String username = "newtest";
        String password = "12345Test";

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        boolean result = databaseController.handleSignup(username, bcryptHashString, "100", databaseHelper);
        assertTrue(result);

        databaseController.handleDeleteUser(username, databaseHelper);

        boolean newResult = databaseController.handleSignup(username, bcryptHashString, "100", databaseHelper);
        assertTrue(newResult);

        databaseController.handleDeleteUser(username, databaseHelper);
    }

}
