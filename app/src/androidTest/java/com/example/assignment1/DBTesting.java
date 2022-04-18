package com.example.assignment1;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DBTesting {
    private DatabaseHelper databaseHelper;
    Context context = getApplicationContext();

    @Test
    public void testInsertingDataToDatabase(){
        databaseHelper= new DatabaseHelper(context);

        boolean result1 = databaseHelper.insertData("Test", "12345", 0);

        assertEquals(true, result1);
    }

    @Test
    public void testCheckUsername(){
        databaseHelper= new DatabaseHelper(context);
        boolean result = databaseHelper.insertData("Test", "12345", 0);

        assertEquals(true, result);

        Boolean checkUser = databaseHelper.checkusername("sdsd");
        assertEquals(true, checkUser);
    }

    @Test
    public void testSignupWithValidUserinfo(){
        databaseHelper= new DatabaseHelper(context);
        String username = "Test";
        String password = "test12345";
        String balance = "200";

        Controller controller = new Controller();

        boolean result = controller.handleSignup(username, password, balance, databaseHelper);
        assertEquals(false, result);
    }







}
