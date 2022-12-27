package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, pass, repass;
    Button register, signin;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.usernameText);
        pass = (EditText) findViewById(R.id.passText);
        repass = (EditText) findViewById(R.id.repassText);
        register = (Button) findViewById(R.id.registerButton);
        signin = (Button) findViewById(R.id.singinButton);
        myDatabaseHelper = new MyDatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String password = pass.getText().toString();
                String repassword = repass.getText().toString();

                if(user.equals("") || password.equals("") || repassword.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields",
                        Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(repassword)){
                        Boolean checkuser = myDatabaseHelper.checkusername(user);
                        if(checkuser == false){
                            Boolean insert = myDatabaseHelper.insertUser(user, password);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "Registerd " +
                                        "successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LibrariesListActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration" +
                                        "failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "User already exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Passwords not matching",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}