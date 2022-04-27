package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddExpenseActivity extends AppCompatActivity {
    Spinner spinner;
    CustomAdapter customAdapter;

    String[] category = {"Food", "Leisure", "Travel", "Accommodation", "Other"};
    int[] images = {R.drawable.ic_food, R.drawable.ic_leisure, R.drawable.ic_travel, R.drawable.ic_accommodation, R.drawable.ic_other};

    EditText addExpense, title;
    Button btnAddExpense;
    DatabaseHelper DB;
    Controller controller = new Controller();
    DatabaseController databaseController = new DatabaseController();

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
            handleAddExpense(title.getText().toString(), addExpense.getText().toString(), spinner.getSelectedItem().toString());
        } catch (Exception ignored){ }
        });
    }

    public void handleAddExpense(String title, String addExpenseAmount, String selected){
        try{
            if(title.equals("") || addExpenseAmount.equals("")){
                controller.setToastText("Please fill all credentials", AddExpenseActivity.this);
            }else if(title.length()< 5){
                controller.setToastText("Expense title is too short", AddExpenseActivity.this);
            }
            else {
                int intAddExpenseAmount = Integer.parseInt(addExpenseAmount);
                boolean result = databaseController.handleAddExpense(title, intAddExpenseAmount, selected, DB);
                if(result){
                    controller.setToastText("Process succeed", AddExpenseActivity.this);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    controller.setToastText("Process failed", AddExpenseActivity.this);
                }
            }
        }catch (NumberFormatException e) {
            controller.setToastText(addExpenseAmount+" is not valid integer", AddExpenseActivity.this);
        }
    }

}