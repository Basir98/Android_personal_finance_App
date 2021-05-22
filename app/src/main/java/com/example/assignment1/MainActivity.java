package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, balance;
    Button btnSignUp, btnLogin;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        balance = (EditText) findViewById(R.id.balance);
        btnSignUp = (Button) findViewById(R.id.btnsignup);
        btnLogin = (Button) findViewById(R.id.btnsignin);
        btnLogin.setBackgroundColor(Color.BLUE);
        btnSignUp.setBackgroundColor(Color.GRAY);

        DB = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String user =username.getText().toString();
                String bala = balance.getText().toString();

                if(user.equals("") || bala.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter alla the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUser = DB.checkusername(user);
                    if(checkUser == false){
                        Boolean insert = DB.insertData(user, Integer.parseInt(bala));
                        if(insert == true){
                            Toast.makeText(MainActivity.this, "Registered successfully",Toast.LENGTH_SHORT).show();
                            Boolean check = DB.checkUserForLogin(user);
                            if(check == true){
                                UserModel.username = user;
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }

                        }else {
                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
                } catch (Exception e){ }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}