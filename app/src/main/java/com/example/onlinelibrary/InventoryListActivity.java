package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class InventoryListActivity extends AppCompatActivity {

    ImageButton logout,menu,admin;
    LinearLayout linearLayout;
    Button add;
    ListView listView;
    MyDatabaseHelper myDatabaseHelper;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        linearLayout = findViewById(R.id.linearLayout);
        logout = linearLayout.findViewById(R.id.buttonLogout);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdmin);
        add = findViewById(R.id.buttonAddInventory);
        listView = findViewById(R.id.inventoryListView);

        myDatabaseHelper = new MyDatabaseHelper(this);
        data  = myDatabaseHelper.getInventoryMatch();

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.inventory_layout,data,
                new String[]{data.getColumnName(3),
                        data.getColumnName(5)},
                new int[]{R.id.libraryName,R.id.bookName},0);
        listView.setAdapter(sca);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),AddInventoryActivity.class);
                Cursor currentElement = myDatabaseHelper.getInventoryByID(id);
                currentElement.moveToFirst();
                i.putExtra("state", "edit");
                i.putExtra("inventory_id",currentElement.getString(0));
                i.putExtra("library_id",currentElement.getString(1));
                i.putExtra("book_id",currentElement.getString(2));
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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddInventoryActivity.class);
                intent.putExtra("state","add");
                startActivity(intent);
            }
        });

    }
}