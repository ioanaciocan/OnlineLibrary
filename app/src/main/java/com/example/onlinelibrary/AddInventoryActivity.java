package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddInventoryActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    TextView text;
//    EditText book, library;
    Button save, delete;
    MyDatabaseHelper myDatabaseHelper;
    String id;
    Spinner bookSpinner, librarySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        myDatabaseHelper = new MyDatabaseHelper(this);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        text = linearLayout.findViewById(R.id.textAddLibrary);
        logout = linearLayout.findViewById(R.id.logoutAddLibrary);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);

        text = findViewById(R.id.textAddLibrary);
//        book = findViewById(R.id.editTextBook);
//        library = findViewById(R.id.editTextLibrary);
        save = findViewById(R.id.buttonSaveLibrary);
        delete = findViewById(R.id.buttonDeleteLibrary);


        bookSpinner = findViewById(R.id.spinnerBook);
        Cursor cursor_books = myDatabaseHelper.getBook();
        startManagingCursor(cursor_books);

        String[] columns = new String[] { "name"};
        int[] to = new int[] { android.R.id.text1};

        SimpleCursorAdapter sca_books = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor_books, columns, to);
        sca_books.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookSpinner.setAdapter(sca_books);

        librarySpinner = findViewById(R.id.spinnerLibrary);
        Cursor cursor_library = myDatabaseHelper.getLibrary();
        startManagingCursor(cursor_library);

        SimpleCursorAdapter sca_library = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor_library, columns, to);
        sca_library.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        librarySpinner.setAdapter(sca_library);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        Log.d("state", state);

        if(state.equals("add")){
            delete.setVisibility(View.GONE);
            text.setText("Add Inventory");
        }else if(state.equals("edit")){
            String library_id = intent.getStringExtra("library_id");
            String book_id = intent.getStringExtra("book_id");
            id = intent.getStringExtra("inventory_id");

//            book.setText(book_id);
//            library.setText(library_id);
            delete.setVisibility(View.VISIBLE);
            save.setText("Update");
            text.setText("Edit Inventory");

            for (int position = 0; position < sca_books.getCount(); position++) {
                if(sca_books.getItemId(position) == Integer.valueOf(book_id)){
                    bookSpinner.setSelection(position);
                    break;
                }
            }
            for (int position = 0; position < sca_library.getCount(); position++) {
                if(sca_library.getItemId(position) == Integer.valueOf(library_id)){
                    librarySpinner.setSelection(position);
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
                    Log.d("state", "onclick state");
//                    myDatabaseHelper.insertInventory(Integer.valueOf(library.getText().toString()),Integer.valueOf(book.getText().toString()));
                    myDatabaseHelper.insertInventory((int) librarySpinner.getSelectedItemId(),(int)bookSpinner.getSelectedItemId());
                    Intent new_intent = new Intent(getApplicationContext(),InventoryListActivity.class);
                    startActivity(new_intent);
                }else if(state.equals("edit")){
//                    myDatabaseHelper.updateInventory(id,library.getText().toString(),book.getText().toString());
                    myDatabaseHelper.updateInventory(id, String.valueOf(librarySpinner.getSelectedItemId()),String.valueOf(bookSpinner.getSelectedItemId()));
                    Intent intent1 = new Intent(getApplicationContext(),InventoryListActivity.class);
                    startActivity(intent1);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseHelper.deleteInventory(id);
                Intent new_intent = new Intent(getApplicationContext(),InventoryListActivity.class);
                startActivity(new_intent);
            }
        });
    }
}