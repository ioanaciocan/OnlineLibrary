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

public class AddBookActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    TextView text;
    EditText name, year, publisher;
    Button save, delete;
    MyDatabaseHelper myDatabaseHelper;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        myDatabaseHelper = new MyDatabaseHelper(this);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        logout = linearLayout.findViewById(R.id.logoutAddBook);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);
        text = linearLayout.findViewById(R.id.textAddBook);

        name = findViewById(R.id.editTextName);
        year = findViewById(R.id.editTextYear);
        publisher = findViewById(R.id.editTextPublisher);
        save = findViewById(R.id.buttonSaveBook);
        delete = findViewById(R.id.buttonDeleteBook);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        Log.d("state", state);

        if(state.equals("add")){
            delete.setVisibility(View.GONE);
            text.setText("Add Book");
        }else if(state.equals("edit")){
            String n = intent.getStringExtra("name");
            String y = intent.getStringExtra("year");
            String p = intent.getStringExtra("publisher");
            id = intent.getLongExtra("id",-1);

            name.setText(n);
            year.setText(y);
            publisher.setText(p);
            delete.setVisibility(View.VISIBLE);
            save.setText("Update");
            text.setText("Edit Book");
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
                    myDatabaseHelper.insertBook(name.getText().toString(),year.getText().toString()
                            ,publisher.getText().toString());
                    Intent new_intent = new Intent(getApplicationContext(),BooksListActivity.class);
                    startActivity(new_intent);
                }else if(state.equals("edit")){
                    myDatabaseHelper.updateBook(id,String.valueOf(name.getText()),
                            String.valueOf(year.getText()),String.valueOf(publisher.getText()));
                    Intent intent1 = new Intent(getApplicationContext(),BooksListActivity.class);
                    startActivity(intent1);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseHelper.deleteBook(id);
                Intent new_intent = new Intent(getApplicationContext(),BooksListActivity.class);
                startActivity(new_intent);
            }
        });

    }
}