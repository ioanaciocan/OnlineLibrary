package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AdminMenuActivity extends AppCompatActivity {

    ImageButton logout, menu, admin;
    Button libraries, books, authors, libraries_books, books_authors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        logout = linearLayout.findViewById(R.id.logoutMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);
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

        authors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AuthorsListActivity.class);
                startActivity(i);
            }
        });

        libraries_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InventoryListActivity.class);
                startActivity(i);
            }
        });

    }
}