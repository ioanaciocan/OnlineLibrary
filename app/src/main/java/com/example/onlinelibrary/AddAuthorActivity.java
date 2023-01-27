package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddAuthorActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    TextView text;
    EditText lastname, firstname, country;
    Button save, delete;
    MyDatabaseHelper myDatabaseHelper;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        myDatabaseHelper = new MyDatabaseHelper(this);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        text = linearLayout.findViewById(R.id.authorAddTitle);
        logout = linearLayout.findViewById(R.id.logoutAddAuthor);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);

        lastname = findViewById(R.id.editTextLastname);
        firstname = findViewById(R.id.editTextFirstname);
        country = findViewById(R.id.editTextCountry);
        save = findViewById(R.id.buttonSaveAuthor);
        delete = findViewById(R.id.buttonDeleteAuthor);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        Log.d("state", state);

        if(state.equals("add")){
            delete.setVisibility(View.GONE);
            text.setText("Add Author");
        }else if(state.equals("edit")){
            String l = intent.getStringExtra("lastname");
            String f = intent.getStringExtra("firstname");
            String c = intent.getStringExtra("country");
            id = intent.getLongExtra("id",-1);

            lastname.setText(l);
            firstname.setText(f);
            country.setText(c);
            delete.setVisibility(View.VISIBLE);
            save.setText("Update");
            text.setText("Edit Author");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("logout",true);
                startActivity(i);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminMenuActivity.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(intent1);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state.equals("add")){
                    myDatabaseHelper.insertAuthor(lastname.getText().toString(),firstname.getText().toString()
                            ,country.getText().toString());
                    Intent new_intent = new Intent(getApplicationContext(),AuthorsListActivity.class);
                    startActivity(new_intent);
                }else if(state.equals("edit")){
                    myDatabaseHelper.updateAuthor(id,String.valueOf(lastname.getText()),
                            String.valueOf(firstname.getText()),String.valueOf(country.getText()));
                    Intent intent1 = new Intent(getApplicationContext(),AuthorsListActivity.class);
                    startActivity(intent1);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseHelper.deleteAuthor(id);
                Intent new_intent = new Intent(getApplicationContext(),AuthorsListActivity.class);
                startActivity(new_intent);
            }
        });
    }
}