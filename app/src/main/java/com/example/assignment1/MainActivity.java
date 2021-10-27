 package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class  MainActivity extends AppCompatActivity {

    EditText username, userPassword, userBalance;
    Button btnSignUp, btnLogin;
    DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userBalance = (EditText) findViewById(R.id.balance);
        btnSignUp = (Button) findViewById(R.id.btnsignup);
        btnLogin = (Button) findViewById(R.id.btnsignin);
        btnLogin.setBackgroundColor(Color.BLUE);
        btnSignUp.setBackgroundColor(Color.BLACK);


        DB = new DatabaseHelper(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String password = userPassword.getText().toString();
                String user =username.getText().toString();
                String bala = userBalance.getText().toString();

                if(user.equals("") || bala.equals("")  || password.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter alla the fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.length() >= 5){
                        Boolean checkUser = DB.checkusername(user);
                        if(checkUser == false){
                            Boolean insert = DB.insertData(user, password, Integer.parseInt(bala));
                            if(insert == true){
                                Toast.makeText(MainActivity.this, "Registered successfully",Toast.LENGTH_SHORT).show();
                                Boolean check = DB.checkUserForLogin(user, password);
                                if(check == true){
                                    UserModel.username = user;
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }

                            }else {
                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "User already exists! Try again with a different username", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(MainActivity.this, "Password is too short, try again with a different password!", Toast.LENGTH_SHORT).show();
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