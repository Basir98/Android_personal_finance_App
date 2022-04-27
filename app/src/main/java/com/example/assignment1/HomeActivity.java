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
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView tv_username, tv_balance;
    DatabaseHelper DB;
    Button btn_addIncome, btn_addExpense, btn_logout, btn_separateLists;

    ArrayList<String> listItem;
    ArrayAdapter<String> adapter;
    ListView userList;
    DatabaseController databaseController = new DatabaseController();
    Controller controller = new Controller();

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

        handleUserIncomeAndExpenseList();

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

    @SuppressLint("SetTextI18n")
    public void handleUserIncomeAndExpenseList(){
        DB = new DatabaseHelper(this);
        Cursor cursor = databaseController.handleUserList(DB);
        if(cursor.getCount()==0) {
            controller.setToastText("No Data", HomeActivity.this);
        }else {
            while(cursor.moveToNext()){
                tv_username.setText(cursor.getString(0));
                tv_balance.setText(cursor.getInt(1)+ " SEK");

                // list of incomes and expenses
                listItem = databaseController.populateUserIncomeAndExpenseList(listItem, cursor, DB);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
                userList.setAdapter(adapter);
            }
        }
    }

}