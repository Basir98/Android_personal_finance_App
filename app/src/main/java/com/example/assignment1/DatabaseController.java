package com.example.assignment1;

import android.database.Cursor;
import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DatabaseController {
    Controller controller = new Controller();


    public boolean handleSignup(String username, String password, String balance, DatabaseHelper DB){
        Boolean checkUser = DB.checkusername(username);
        if(!checkUser){
            Boolean insert = DB.insertData(username, password, Integer.parseInt(balance));
            if(insert){
                return DB.checkUserForLogin(username, password);
            }
        }
        return false;
    }

    public boolean checkForUsername(String username, DatabaseHelper DB){
        return DB.checkusername(username);
    }

    public Cursor handleUserList(DatabaseHelper DB){
        return DB.getUserInfo(UserModel.username);
    }

    public ArrayList<String> populateUserIncomeAndExpenseList(ArrayList<String> listItem, Cursor cursor, DatabaseHelper DB){
        listItem.clear();
        Cursor cursor1 = DB.viewData(cursor.getString(0));
        if (!(cursor1.getCount() == 0)) {
            listItem.add(0, cursor1.getColumnName(3) + "\t\t\t\t|\t\t" + cursor1.getColumnName(4) +
                    "\t\t\t|\t\t" + cursor1.getColumnName(5) + "\t\t|\t\t" + cursor1.getColumnName(6));
            while (cursor1.moveToNext()) {
                String strCategory = "";
                switch (cursor1.getString(5)) {
                    case "Food":
                        strCategory = "Food";
                        break;
                    case "Leisure":
                        strCategory = "Leisure";
                        break;
                    case "Travel":
                        strCategory = "Travel";
                        break;
                    case "Accommodation":
                        strCategory = "Accommodation";
                        break;
                    case "Other":
                        strCategory = "Other";
                        break;
                    case "Salary":
                        strCategory = "Salary";
                        break;
                }
                listItem.add(cursor1.getString(3) + "\t\t\t" + controller.truncate(cursor1.getString(4), 5)
                        + "\t\t\t" + controller.truncate(strCategory, 6) + "\t\t\t\t" + cursor1.getInt(6) + " kr");
            }
        }
        return listItem;
    }

    public boolean handleHashedPassword(String username, String password, DatabaseHelper DB){
        String bcryptHashString = DB.getHashedPassword(username);
        if(bcryptHashString.isEmpty()){
            return false;
        }else {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
            return result.verified;
        }
    }

    public void handleDeleteUser(String username, DatabaseHelper DB){
        DB.deleteUser(username);
    }

    public boolean handleAddExpense(String title, int addExpenseAmount, String selected, DatabaseHelper DB){
        Cursor cursor = DB.getUserInfo(UserModel.username);
        if(!(cursor.getCount() ==0)) {
            if(cursor.moveToLast()){
                String userN = cursor.getString(0);
                int currentBalance = cursor.getInt(1);
                currentBalance -= addExpenseAmount;
                return DB.modifyBalance(userN, currentBalance, "Expense", title, selected, addExpenseAmount);
            }
        }
        return false;
    }

    public boolean handleAddIncome(String title, int addIncomeAmount, String selected, DatabaseHelper DB){
        Cursor cursor = DB.getUserInfo(UserModel.username);
        if(!(cursor.getCount() ==0)) {
            if(cursor.moveToLast()){
                String userN = cursor.getString(0);
                int currentBalance = cursor.getInt(1);
                currentBalance += addIncomeAmount;
                return DB.modifyBalance(userN, currentBalance, "Income", title, selected, addIncomeAmount);
            }
        }
        return false;
    }


}
