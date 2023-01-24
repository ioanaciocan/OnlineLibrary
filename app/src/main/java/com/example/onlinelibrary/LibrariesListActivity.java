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

public class LibrariesListActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    Button addLibrary;
    ListView libraryListView;
    MyDatabaseHelper myDatabaseHelper;
    Cursor libraryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries_list);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        logout = linearLayout.findViewById(R.id.logoutLibraries);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);

        addLibrary = findViewById(R.id.buttonAddLibrary);
        libraryListView = findViewById(R.id.lilbraryListView);
        myDatabaseHelper = new MyDatabaseHelper(this);
        libraryData  = myDatabaseHelper.getLibrary();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.libraries_layout,libraryData,
                new String[]{libraryData.getColumnName(1),
                        libraryData.getColumnName(2)
                        },
                    new int[]{R.id.libraryLayoutName,R.id.libraryLayoutAddress},0);
        libraryListView.setAdapter(sca);

        libraryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = (String) parent.getItemAtPosition(position);
                Intent i = new Intent(getApplicationContext(), AddLibraryActivity.class);
                Cursor currentElement = myDatabaseHelper.getLibraryByID(id);
                currentElement.moveToFirst();
                i.putExtra("state", "edit");
                i.putExtra("name",currentElement.getString(1));
                i.putExtra("address",currentElement.getString(2));
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

        addLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddLibraryActivity.class);
                i.putExtra("state", "add");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        libraryData.close();
    }
}