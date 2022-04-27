package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Finance.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("Create Table users(username TEXT, password TEXT, balance INT, type TEXT, title TEXT, category TEXT, amount INT, calendar TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Boolean insertData(String username, String password, int balance) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("balance", balance);
        contentValues.put("type", "");
        contentValues.put("title", "");
        contentValues.put("category", "");
        contentValues.put("amount", 0);
        contentValues.put("calendar", "");
        long result = MyDB.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean checkUserForLogin(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDB.rawQuery("Select * from users where username = ? AND password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public String getHashedPassword(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDB.rawQuery("Select password from users where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
                return cursor.getString(0);
        } else{
            return "";
        }
    }


    // get username and user balance
    public Cursor getUserInfo(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("Select username, balance FROM users where username = ?", new String[]{username});
    }

    public Boolean modifyBalance(String username, int balance, String type, String title, String selected, int amount) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("balance", balance);
        contentValues.put("type", type);            // income or expense
        contentValues.put("title", title);          // case title
        contentValues.put("category", selected);    // category like Salary or Food
        contentValues.put("amount", amount);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        contentValues.put("calendar", currentDate);

        long result = MyDB.insert("users", null, contentValues);
        return result != -1;
    }

    /**
     * view data in SQLite, en gräns på 50 rader är satt på tabellen
     * @return cursor
     */
    public Cursor viewData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from users WHERE username = ? LIMIT 50 OFFSET 1", new String[] {username});
    }

    public Cursor viewSpecificTypeData(String username, String str){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from users WHERE username = ? AND type = ?", new String[] {username, str});
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE from users WHERE username = ?", new String[]{username});
        db.close();
    }
}
