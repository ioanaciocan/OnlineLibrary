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

public class AuthorsListActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    Button addAuthor;
    ListView authorListView;
    MyDatabaseHelper myDatabaseHelper;
    Cursor authorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors_list);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        logout = linearLayout.findViewById(R.id.logoutAuthor);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);

        addAuthor = findViewById(R.id.buttonAddAuthor);
        authorListView = findViewById(R.id.authorListView);
        myDatabaseHelper = new MyDatabaseHelper(this);
        authorData  = myDatabaseHelper.getAuthor();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.authors_layout,authorData,
                new String[]{authorData.getColumnName(1),
                        authorData.getColumnName(2),
                        authorData.getColumnName(3)
                },
                new int[]{R.id.authorsLayoutLastname,R.id.authorsLayoutFirstname,R.id.authorsLayoutCountry},0);
        authorListView.setAdapter(sca);

        authorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), AddAuthorActivity.class);
                Cursor currentElement = myDatabaseHelper.getAuthorByID(id);
                currentElement.moveToFirst();
                i.putExtra("state", "edit");
                i.putExtra("lastname",currentElement.getString(1));
                i.putExtra("firstname",currentElement.getString(2));
                i.putExtra("country",currentElement.getString(3));
                i.putExtra("id",id);
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("logout",true);
                startActivity(i);
            }
        });

        addAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddAuthorActivity.class);
                i.putExtra("state", "add");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authorData.close();
    }
}