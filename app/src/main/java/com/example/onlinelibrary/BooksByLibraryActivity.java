package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BooksByLibraryActivity extends AppCompatActivity {

    TextView library;
    LinearLayout linearLayout, linearLayout1;
    ImageButton menu, logout, admin;
    ListView listView;
    MyDatabaseHelper myDatabaseHelper;
    Cursor cursor;
    Long library_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_by_library);

        myDatabaseHelper = new MyDatabaseHelper(this);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout1 = linearLayout.findViewById(R.id.linearLayoutButtons);
        admin = linearLayout1.findViewById(R.id.buttonAdmin);
        logout = linearLayout1.findViewById(R.id.buttonLogout);
        menu = linearLayout1.findViewById(R.id.buttonMenu);
        listView = findViewById(R.id.listView);
        library = linearLayout.findViewById(R.id.textLibrary);

        Intent intent = getIntent();
        library_id = intent.getLongExtra("library_id",-1);
        cursor = myDatabaseHelper.getBooksByLibraryID(library_id);

        cursor.moveToFirst();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.books_layout,cursor,
                new String[]{cursor.getColumnName(1),
                        cursor.getColumnName(2),
                        cursor.getColumnName(3)},
                new int[]{R.id.booksLayoutName,R.id.booksLayoutYear,R.id.booksLayoutPublisher},0);
        listView.setAdapter(sca);
        library.setText(cursor.getString(4));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), BookInfoActivity.class);
                i.putExtra("book_id",id);
                i.putExtra("library_id",library_id);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MenuActivity.class);
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                i.putExtra("logout",true);
                startActivity(i);
            }
        });
    }
}