package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddLibraryActivity extends AppCompatActivity {

    ImageButton logout,  admin, menu;
    TextView text;
    EditText name, address;
    Button save, delete;
    MyDatabaseHelper myDatabaseHelper;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        myDatabaseHelper = new MyDatabaseHelper(this);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        text = linearLayout.findViewById(R.id.textAddLibrary);
        logout = linearLayout.findViewById(R.id.logoutAddLibrary);
        menu = linearLayout.findViewById(R.id.buttonMenu);
        admin = linearLayout.findViewById(R.id.buttonAdminMenu);

        text = findViewById(R.id.textAddLibrary);
        name = findViewById(R.id.editTextName);
        address = findViewById(R.id.editTextAddress);
        save = findViewById(R.id.buttonSaveLibrary);
        delete = findViewById(R.id.buttonDeleteLibrary);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        Log.d("state", state);

        if(state.equals("add")){
            delete.setVisibility(View.GONE);
            text.setText("Add Library");
        }else if(state.equals("edit")){
            String n = intent.getStringExtra("name");
            String a = intent.getStringExtra("address");
            id = intent.getLongExtra("id",-1);

            name.setText(n);
            address.setText(a);
            delete.setVisibility(View.VISIBLE);
            save.setText("Update");
            text.setText("Edit Library");
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
                    myDatabaseHelper.insertLibrary(name.getText().toString(),address.getText().toString());
                    Intent new_intent = new Intent(getApplicationContext(),LibrariesListActivity.class);
                    startActivity(new_intent);
                }else if(state.equals("edit")){
                    myDatabaseHelper.updateLibrary(id,String.valueOf(name.getText()),String.valueOf(address.getText()));
                    Intent intent1 = new Intent(getApplicationContext(),LibrariesListActivity.class);
                    startActivity(intent1);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseHelper.deleteLibrary(id);
                Intent new_intent = new Intent(getApplicationContext(),LibrariesListActivity.class);
                startActivity(new_intent);
            }
        });
    }
}