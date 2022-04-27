package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddIncomeActivity extends AppCompatActivity {
    Spinner spinner;
    CustomAdapter customAdapter;

    String[] category = {"Salary", "Other"};
    int[] images = {R.drawable.ic_salary, R.drawable.ic_other};

    EditText addIncome, title;
    Button btnAddIncome;
    DatabaseHelper DB;
    Controller controller = new Controller();
    DatabaseController databaseController = new DatabaseController();


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
            handleAddIncome(title.getText().toString(), addIncome.getText().toString(), spinner.getSelectedItem().toString());
            } catch (Exception ignored){ }
        });
    }

    public void handleAddIncome(String title, String addIncomeAmount, String selected){
        try{
            if(title.equals("") || addIncomeAmount.equals("")){
                controller.setToastText("Please fill all credentials", AddIncomeActivity.this);
            }else if(title.length()< 5){
                controller.setToastText("Income title is too short", AddIncomeActivity.this);
            }
            else {
                int intAddIncomeAmount = Integer.parseInt(addIncomeAmount);
                boolean result = databaseController.handleAddIncome(title, intAddIncomeAmount, selected, DB);
                if(result){
                    controller.setToastText("Process succeed", AddIncomeActivity.this);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }else {
                    controller.setToastText("Process failed", AddIncomeActivity.this);
                }
            }
        }catch (NumberFormatException e) {
            controller.setToastText(addIncomeAmount+" is not valid integer", AddIncomeActivity.this);
        }
    }

}