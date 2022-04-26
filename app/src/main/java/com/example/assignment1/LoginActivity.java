package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    EditText editText_username, editTextPassword;
    Button btnLogin;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_username = findViewById(R.id.username1);
        editTextPassword = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnsignin1);
        btnLogin.setBackgroundColor(Color.BLUE);
        DB = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String user = editText_username.getText().toString();
            String password = editTextPassword.getText().toString();
            if(user.equals("")){
                Toast.makeText(LoginActivity.this, "Please enter a user name", Toast.LENGTH_SHORT).show();
            }else {
                String bcryptHashString = DB.getHashedPassword(user);
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);

                if(result.verified){
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    UserModel.username = user;
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid user info or User is not created! Try Again", Toast.LENGTH_SHORT).show();
                }
                /*
                Boolean checkuser = DB.checkUserForLogin(user, password);
                if(checkuser == true){
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    UserModel.username = user;
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid user info or User is not created! Try Again", Toast.LENGTH_SHORT).show();
                }
                 */
            }
        });
    }
}