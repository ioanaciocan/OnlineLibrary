package com.example.onlinelibrary;

import static com.example.onlinelibrary.R.id.buttonLibrariesM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button logout, libraries, books, authors, libraries_books, books_authors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);

        logout = findViewById(R.id.logoutMenu);
        libraries = findViewById(R.id.buttonLibrariesM);
        books = findViewById(R.id.buttonBooksM);
        authors = findViewById(R.id.buttonAuthorsM);
        libraries_books = findViewById(R.id.buttonLibrariesBooksM);
        books_authors = findViewById(R.id.buttonBooksAuthorsM);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("logout",true);
                startActivity(i);
            }
        });
        libraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LibrariesListActivity.class);
                startActivity(i);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),BooksListActivity.class);
                startActivity(i);
            }
        });

    }
}