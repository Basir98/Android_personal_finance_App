package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddExpenseActivity extends AppCompatActivity {
    Spinner spinner;
    CustomAdapter customAdapter;

    String[] category = {"Food", "Leisure", "Travel", "Accommodation", "Other"};
    int[] images = {R.drawable.ic_food, R.drawable.ic_leisure, R.drawable.ic_travel, R.drawable.ic_accommodation, R.drawable.ic_other};

    EditText addExpense, title;
    Button btnAddExpense;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        DB = new DatabaseHelper(this);
        spinner = findViewById(R.id.expense_spinner);

        customAdapter = new CustomAdapter(this, category, images);
        spinner.setAdapter(customAdapter);

        title = findViewById(R.id.editText_expense_title);
        addExpense = findViewById(R.id.editText_expense);

        btnAddExpense = findViewById(R.id.button_add_expense);
        btnAddExpense.setOnClickListener(v -> {
            try {

            String strTitle = title.getText().toString();
            int intAddExpense = Integer.parseInt(addExpense.getText().toString());
            String selected = spinner.getSelectedItem().toString();

            Toast.makeText(AddExpenseActivity.this, "Selected: "+selected, Toast.LENGTH_SHORT).show();

            if(strTitle.equals("") || addExpense.getText().toString().equals("")){
                Toast.makeText(AddExpenseActivity.this, "Please fill all credentials", Toast.LENGTH_SHORT).show();
            }else {
                Cursor cursor = DB.getUserInfo(UserModel.username);
                if(cursor.getCount()==0) {
                    Toast.makeText(AddExpenseActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }else {
                    if(cursor.moveToLast()){
                        String userN = cursor.getString(0);
                        int currentBalance = cursor.getInt(1);
                        currentBalance -= intAddExpense;
                        Boolean checked = DB.modifyBalance(userN, currentBalance, "Expense", strTitle, selected, intAddExpense);
                        if(checked){
                            Toast.makeText(AddExpenseActivity.this, "Process succeed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(AddExpenseActivity.this, "Process failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (Exception ignored){ }
        });
    }
}