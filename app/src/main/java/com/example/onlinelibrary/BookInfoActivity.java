package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookInfoActivity extends AppCompatActivity {

    ImageButton admin, logout, menu;
    LinearLayout linearLayout, linearLayout1,info;
    TextView bookname, author, year, country, publisher, library;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        myDatabaseHelper = new MyDatabaseHelper(this);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout1 = linearLayout.findViewById(R.id.linearLayoutButtons);
        info = findViewById(R.id.linearLayoutInfo);
        admin = linearLayout1.findViewById(R.id.buttonAdmin);
        menu = linearLayout1.findViewById(R.id.buttonMenu);
        logout = linearLayout1.findViewById(R.id.buttonMenu);
        bookname = info.findViewById(R.id.textBookTitle);
        author = info.findViewById(R.id.authorName);
        year = info.findViewById(R.id.year);
        country = info.findViewById(R.id.country);
        publisher = info.findViewById(R.id.publisher);
        library = linearLayout.findViewById(R.id.textLibrary);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("book_id",-1);
        Long library_id = intent.getLongExtra("library_id",-1);
        Cursor cursor = myDatabaseHelper.getCreativeMatchByBookID(id);
        if(cursor.getCount() == 0){
            Intent intent1 = new Intent(getApplicationContext(),MenuActivity.class);
            Toast.makeText(BookInfoActivity.this, "No book information", Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        }else{
            cursor.moveToFirst();

            Cursor library_cursor = myDatabaseHelper.getLibraryByID(library_id);
            library_cursor.moveToFirst();
            library.setText(library_cursor.getString(1));
            bookname.setText(cursor.getString(3));
            year.setText(cursor.getString(4));
            publisher.setText(cursor.getString(5));
            author.setText(cursor.getString(7)+" "+cursor.getString(6));
            country.setText(cursor.getString(8));
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

    }
}