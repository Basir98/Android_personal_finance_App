 package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import at.favre.lib.crypto.bcrypt.BCrypt;

 public class  MainActivity extends AppCompatActivity {
    EditText username, userPassword, userBalance;
    Button btnSignUp, btnLogin;
    DatabaseHelper DB;
    Controller controller = new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        userPassword =  findViewById(R.id.userPassword);
        userBalance = findViewById(R.id.balance);
        btnSignUp = findViewById(R.id.btnsignup);
        btnLogin = findViewById(R.id.btnsignin);
        btnLogin.setBackgroundColor(Color.BLUE);
        btnSignUp.setBackgroundColor(Color.BLACK);

        DB = new DatabaseHelper(this);
        btnSignUp.setOnClickListener(v -> {
            if(controller.checkUserPassword(userPassword.getText().toString())){
                try{
                    String bcryptHashString = BCrypt.withDefaults().hashToString(12, userPassword.getText().toString().toCharArray());

                    String user =username.getText().toString();
                    String balance = userBalance.getText().toString();

                    handleSignup(user, bcryptHashString, balance);
                } catch (Exception ignored){ }

            }else {
                controller.setToastText("Password must include number and capital letters, try again with a different password!", MainActivity.this);
            }
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    public void handleSignup(String username, String hashPassword, String balance){
        boolean result = controller.checkUserInput(username, hashPassword, balance);
        if(result){
            boolean res = controller.handleSignup(username, hashPassword, balance, DB);
            if(res){
                controller.setToastText("Registered successfully", MainActivity.this);
                UserModel.username = username;
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }else {
                boolean userNameResult = controller.checkForUsername(username, DB);
                if(userNameResult){
                    controller.setToastText("User already exists! Try again with a different username", MainActivity.this);
                }else {
                    controller.setToastText("Registration failed", MainActivity.this);
                }
            }
        }else {
            controller.setToastText("Please enter alla the fields", MainActivity.this);
        }
    }
}