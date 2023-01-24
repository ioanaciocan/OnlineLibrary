package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    ImageButton logout, admin, menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        LinearLayout linearLayout1 = linearLayout.findViewById(R.id.linearLayoutButtons);

        logout = linearLayout1.findViewById(R.id.buttonLogoutMenu);
        admin = linearLayout1.findViewById(R.id.buttonAdmin);
        menu = linearLayout1.findViewById(R.id.buttonMenu);



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

            }
        });

    }
}