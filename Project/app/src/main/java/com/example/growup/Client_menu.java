package com.example.growup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Client_menu extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout trainingLayout;
    LinearLayout dietLayout;
    LinearLayout uploadLayout;
    LinearLayout statLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        backButton = findViewById(R.id.back_button);
        trainingLayout = findViewById(R.id.square_1);
        dietLayout = findViewById(R.id.square_2);
        uploadLayout = findViewById(R.id.square_3);
        statLayout = findViewById(R.id.square_4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        trainingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Client_calendar.class);
                startActivity(i);
            }
        });
        dietLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Coach_Training.class);
                startActivity(i);
            }
        });
        uploadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Coach_Training.class);
                startActivity(i);
            }
        });
        statLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Coach_Training.class);
                startActivity(i);
            }
        });
    }
}