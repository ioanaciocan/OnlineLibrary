package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, pass;
    Button register, login;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameText1);
        pass = (EditText) findViewById(R.id.passText1);
        register = (Button) findViewById(R.id.registerButton1);
        login = (Button) findViewById(R.id.singinButton1);
        myDatabaseHelper = new MyDatabaseHelper(this);
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            Intent intent = new Intent(getApplicationContext(), LibrariesListActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(LoginActivity.this, "Please log in", Toast.LENGTH_SHORT).show();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String password = pass.getText().toString();

                if(user.equals("") || user.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter al fields",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkuserpass = myDatabaseHelper.checkUsernamePassword(user, password);
                    if(checkuserpass == true){
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "true");
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Sign In successfull",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),LibrariesListActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}