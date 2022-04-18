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
    Controller controller = new Controller();


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
                String balance = userBalance.getText().toString();

                boolean result = controller.checkUserInput(user, password, balance);

                if(result){
                    boolean passwordResult = controller.checkUserPassword(password);
                    if(passwordResult){
                        boolean res = controller.handleSignup(user, password, balance, DB);
                        if(res){
                            controller.setToastText("Registered successfully", MainActivity.this);
                            UserModel.username = user;
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }else {
                            boolean userNameResult = controller.checkForUsername(user, DB);
                            if(userNameResult){
                                controller.setToastText("User already exists! Try again with a different username", MainActivity.this);

                            }else {
                                controller.setToastText("Registration failed", MainActivity.this);
                            }
                        }
                    }else{
                        controller.setToastText("Password must include number and capital letters, try again with a different password!", MainActivity.this);
                    }
                }else {
                    controller.setToastText("Please enter alla the fields", MainActivity.this);
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

    public void handleSignup(String username, String password, String balance){
        Boolean checkUser = DB.checkusername(username);
        if(checkUser == false){
            Boolean insert = DB.insertData(username, password, Integer.parseInt(balance));
            if(insert == true){
                controller.setToastText("Registered successfully", MainActivity.this);
                Boolean check = DB.checkUserForLogin(username, password);
                if(check == true){
                    UserModel.username = username;
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }else {
                controller.setToastText("Registration failed", MainActivity.this);
            }
        }else {
            controller.setToastText("User already exists! Try again with a different username", MainActivity.this);
        }
    }


}