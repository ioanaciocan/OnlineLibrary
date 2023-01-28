package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddCreativeActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    TextView text;
    Button save, delete;
    MyDatabaseHelper myDatabaseHelper;
    String id;
    Spinner bookSpinner, authorSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_creative);

        myDatabaseHelper = new MyDatabaseHelper(this);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        text = linearLayout.findViewById(R.id.text);
        logout = linearLayout.findViewById(R.id.logout);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdmin);
        save = findViewById(R.id.buttonSave);
        delete = findViewById(R.id.buttonDelete);
        bookSpinner = findViewById(R.id.spinnerBook);
        authorSpinner = findViewById(R.id.spinnerAuthor);


        Cursor cursor_books = myDatabaseHelper.getBook();
        startManagingCursor(cursor_books);
        String[] columns = new String[] { "name"};
        int[] to = new int[] { android.R.id.text1};
        SimpleCursorAdapter sca_books = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor_books, columns, to);
        sca_books.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookSpinner.setAdapter(sca_books);

        Cursor cursor_author = myDatabaseHelper.getAuthor();
        startManagingCursor(cursor_author);
        String[] columns1 = new String[] { "lastname"};
        SimpleCursorAdapter sca_author = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor_author, columns1, to);
        sca_author.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authorSpinner.setAdapter(sca_author);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");

        if(state.equals("add")){
            delete.setVisibility(View.GONE);
            text.setText("Add Creative");
        }else if(state.equals("edit")){
            String author_id = intent.getStringExtra("author_id");
            String book_id = intent.getStringExtra("book_id");
            id = intent.getStringExtra("creative_id");
            delete.setVisibility(View.VISIBLE);
            save.setText("Update");
            text.setText("Edit Creative");

            for (int position = 0; position < sca_books.getCount(); position++) {
                if(sca_books.getItemId(position) == Integer.valueOf(book_id)){
                    bookSpinner.setSelection(position);
                    break;
                }
            }
            for (int position = 0; position < sca_author.getCount(); position++) {
                if(sca_author.getItemId(position) == Integer.valueOf(author_id)){
                    authorSpinner.setSelection(position);
                    break;
                }
            }
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
                    myDatabaseHelper.insertCreative((int) bookSpinner.getSelectedItemId(),(int)authorSpinner.getSelectedItemId());
                    Intent new_intent = new Intent(getApplicationContext(),CreativeListActivity.class);
                    startActivity(new_intent);
                }else if(state.equals("edit")){
                    myDatabaseHelper.updateCreative(id, String.valueOf(bookSpinner.getSelectedItemId()),String.valueOf(authorSpinner.getSelectedItemId()));
                    Intent intent1 = new Intent(getApplicationContext(),CreativeListActivity.class);
                    startActivity(intent1);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseHelper.deleteCreative(id);
                Intent new_intent = new Intent(getApplicationContext(),CreativeListActivity.class);
                startActivity(new_intent);
            }
        });

    }
}