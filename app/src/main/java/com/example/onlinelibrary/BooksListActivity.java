package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BooksListActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    Button addBook;
    ListView bookListView;
    MyDatabaseHelper myDatabaseHelper;
    Cursor bookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        logout = linearLayout.findViewById(R.id.logoutBook);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);

        addBook = findViewById(R.id.buttonAddBook);
        bookListView = findViewById(R.id.bookListView);

        myDatabaseHelper = new MyDatabaseHelper(this);
        bookData  = myDatabaseHelper.getBook();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.books_layout,bookData,
                new String[]{bookData.getColumnName(1),
                        bookData.getColumnName(2), bookData.getColumnName(3)
                },
                new int[]{R.id.booksLayoutName,R.id.booksLayoutYear, R.id.booksLayoutPublisher},0);
        bookListView.setAdapter(sca);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),AddBookActivity.class);
                Cursor currentElement = myDatabaseHelper.getBookByID(id);
                currentElement.moveToFirst();
                i.putExtra("state", "edit");
                i.putExtra("name",currentElement.getString(1));
                i.putExtra("year",currentElement.getString(2));
                i.putExtra("publisher",currentElement.getString(3));
                i.putExtra("id",id);
                startActivity(i);
            }
        });

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

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddBookActivity.class);
                i.putExtra("state", "add");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookData.close();
    }
}