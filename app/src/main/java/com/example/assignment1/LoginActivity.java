package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editText_username, editTextPassword;
    Button btnLogin;
    DatabaseHelper DB;
    Controller controller = new Controller();
    DatabaseController databaseController = new DatabaseController();

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
                boolean result = databaseController.handleHashedPassword(user, password, DB);
                if(result){
                    controller.setToastText("Login successful", LoginActivity.this);
                    UserModel.username = user;
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }else {
                    controller.setToastText("Invalid user info or User is not created! Try Again", LoginActivity.this);
                }
            }
        });
    }
}