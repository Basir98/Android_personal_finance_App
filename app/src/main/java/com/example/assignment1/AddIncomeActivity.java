package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddIncomeActivity extends AppCompatActivity {
    Spinner spinner;
    CustomAdapter customAdapter;

    String[] category = {"Salary", "Other"};
    int[] images = {R.drawable.ic_salary, R.drawable.ic_other};

    EditText addIncome, title;
    Button btnAddIncome;
    DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        DB = new DatabaseHelper(this);
        spinner = findViewById(R.id.income_spinner);
        customAdapter = new CustomAdapter(this, category, images);
        spinner.setAdapter(customAdapter);

        title = findViewById(R.id.editText_income_title);
        addIncome = findViewById(R.id.editText_income);
        btnAddIncome = findViewById(R.id.button_add_income);

        btnAddIncome.setOnClickListener(v -> {
            try{

            String strTitle = title.getText().toString();
            int intAddIncome = Integer.parseInt(addIncome.getText().toString());
            String selected = spinner.getSelectedItem().toString();
            if(strTitle.equals("") || addIncome.getText().toString().equals("")){
                Toast.makeText(AddIncomeActivity.this, "Please fill all credentials", Toast.LENGTH_SHORT).show();
            }else {
                Cursor cursor = DB.getUserInfo(UserModel.username);
                if(cursor.getCount()==0) {
                    Toast.makeText(AddIncomeActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }else {
                    if(cursor.moveToLast()){
                        String userN = cursor.getString(0);
                        int currentBalance = cursor.getInt(1);
                        currentBalance += intAddIncome;
                        Boolean checked = DB.modifyBalance(userN, currentBalance, "Income", strTitle, selected, intAddIncome);
                        if(checked){
                            Toast.makeText(AddIncomeActivity.this, "Process succeed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(AddIncomeActivity.this, "Process failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            } catch (Exception ignored){ }
        });

    }
}