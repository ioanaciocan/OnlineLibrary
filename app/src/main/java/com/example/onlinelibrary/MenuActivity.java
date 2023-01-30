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

public class MenuActivity extends AppCompatActivity {

    ImageButton logout, admin, menu;
    ListView listView;
    Cursor data;
    MyDatabaseHelper myDatabaseHelper;
    String permissions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        LinearLayout linearLayout1 = linearLayout.findViewById(R.id.linearLayoutButtons);

        Intent initial_intent = getIntent();
        permissions = initial_intent.getStringExtra("permissions");
        myDatabaseHelper = new MyDatabaseHelper(this);
        logout = linearLayout1.findViewById(R.id.buttonLogoutMenu);
        admin = linearLayout1.findViewById(R.id.buttonAdmin);
        menu = linearLayout1.findViewById(R.id.buttonMenu);
        listView = findViewById(R.id.listView);

        if(permissions != null) {
            if (permissions.equals("user")) {
                admin.setVisibility(View.GONE);
            }
        }
        data  = myDatabaseHelper.getLibrary();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.libraries_layout,data,
                new String[]{data.getColumnName(1),
                        data.getColumnName(2)
                },
                new int[]{R.id.libraryLayoutName,R.id.libraryLayoutAddress},0);
        listView.setAdapter(sca);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), BooksByLibraryActivity.class);
                i.putExtra("library_id",id);
                i.putExtra("permissions",permissions);
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
                i.putExtra("permissions",permissions);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}