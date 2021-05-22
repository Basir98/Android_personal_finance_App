package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editText_username;
    Button btnLogin;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_username = (EditText) findViewById(R.id.username1);
        btnLogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editText_username.getText().toString();
                if(user.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter a user name", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkuser = DB.checkUserForLogin(user);
                    if(checkuser == true){
                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        UserModel.username = user;
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }
}