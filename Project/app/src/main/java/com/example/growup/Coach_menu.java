package com.example.growup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import Objects.All;

public class Coach_menu extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout trainingLayout;
    LinearLayout dietLayout;
    LinearLayout uploadLayout;
    LinearLayout statLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_menu);
        Intent intent = getIntent();
        String client = intent.getStringExtra("name");
        String clientUid = intent.getStringExtra("client_uid");
        TextView clientName = findViewById(R.id.nameClient);
        clientName.setText(client);

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
                Intent i = new Intent(getApplicationContext(), Coach_calendar.class);
                i.putExtra("name", client);
                i.putExtra("client_uid", clientUid);
                startActivity(i);
            }
        });
        dietLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Coach_diet.class);
                i.putExtra("name", client);
                i.putExtra("client_uid", clientUid);
                startActivity(i);
            }
        });
        uploadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Coach_upload.class);
                i.putExtra("name", client);
                i.putExtra("client_uid", clientUid);
                startActivity(i);
            }
        });
        statLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Statistics.class);
                i.putExtra("name", client);
                i.putExtra("client_uid", clientUid);
                startActivity(i);
            }
        });
    }
}