package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView tv_username, tv_balance;
    DatabaseHelper DB;
    Button btn_addIncome, btn_addExpense, btn_logout, btn_separateLists;

    ArrayList<String> listItem;
    ArrayAdapter<String> adapter;
    ListView userList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_username = findViewById(R.id.textview_username);
        tv_balance = findViewById(R.id.textview_balance);
        btn_addIncome = findViewById(R.id.btn_to_add_income);
        btn_addExpense = findViewById(R.id.btn_to_add_expense);
        userList = findViewById(R.id.listView);
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setBackgroundColor(Color.RED);
        btn_separateLists = findViewById(R.id.btn_to_separate_lists);
        listItem = new ArrayList<>();

        DB = new DatabaseHelper(this);
        Cursor cursor = DB.getUserInfo(UserModel.username);
        if(cursor.getCount()==0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                tv_username.setText(cursor.getString(0));
                tv_balance.setText(cursor.getInt(1)+ " SEK");

                listItem.clear();
                // list of saving and expenses
                Cursor cursor1 = DB.viewData(cursor.getString(0));
                if(cursor1.getCount()== 0){
                    Toast.makeText(this, "No Data to Show in list", Toast.LENGTH_SHORT).show();
                }else {
                    listItem.add(0, cursor1.getColumnName(3)+ "       |  "+cursor1.getColumnName(4)+
                            "  |  "+cursor1.getColumnName(5)+ " | "+cursor1.getColumnName(6)+ " | "+cursor1.getColumnName(7));

                    while(cursor1.moveToNext()){
                        String strCategory="";
                        switch (cursor1.getString(5)){
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
                        listItem.add(cursor1.getString(3) +"   "+cursor1.getString(4)
                                    + "   " +strCategory+ "   "+cursor1.getInt(6)+" kr    "+cursor1.getString(7));

                    }
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
                    userList.setAdapter(adapter);
                }
            }
        }

        btn_addIncome.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddIncomeActivity.class);
            startActivity(intent);
        });

        btn_addExpense.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
            startActivity(intent);
        });

        btn_separateLists.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SeparateListsActivity.class);
            startActivity(intent);

        });

        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

    }

}