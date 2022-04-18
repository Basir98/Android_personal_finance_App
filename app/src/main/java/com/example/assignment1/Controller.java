package com.example.assignment1;

import android.app.Activity;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {


    public boolean checkUserInput(String username, String password, String balance){
        if(username.equals("") || password.equals("") || balance.equals("")){
            return false;
        }
        return true;
    }

    public boolean checkUserPassword(String password){
        String regex_pattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,24}$";
        Pattern pattern = Pattern.compile(regex_pattern);

        Matcher matcher1 = pattern.matcher(password);

        if(password.length() > 5 && matcher1.matches()){
            return true;
        }
        return false;
    }

    public void setToastText(String strText, Activity activity){
        Toast.makeText(activity, strText, Toast.LENGTH_SHORT).show();
    }

    public boolean handleSignup(String username, String password, String balance, DatabaseHelper DB){
        Boolean checkUser = DB.checkusername(username);
        if(checkUser == false){
            Boolean insert = DB.insertData(username, password, Integer.parseInt(balance));
            if(insert == true){
                Boolean check = DB.checkUserForLogin(username, password);
                if(check == true){
                    return true;
                }
            }else {
            }
        }else {
        }
        return false;
    }

    public boolean checkForUsername(String username, DatabaseHelper DB){
        return DB.checkusername(username);
    }


}
