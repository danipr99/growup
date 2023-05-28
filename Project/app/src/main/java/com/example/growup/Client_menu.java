package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Client_menu extends AppCompatActivity {
    private ImageButton backButton;
    private LinearLayout trainingLayout;
    private LinearLayout dietLayout;
    private LinearLayout uploadLayout;
    private LinearLayout statLayout;
    private FirebaseAuth mAuth;
    private String uidCoach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        mAuth = FirebaseAuth.getInstance();
        String clientUid = mAuth.getCurrentUser().getUid();



        DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference()
                .child("Clients")
                .child(clientUid)
                .child("uidMyCoach");

        clientRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String coach = dataSnapshot.getValue().toString();
                    uidCoach = coach;
                    Log.d("UID Coach", uidCoach);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                i.putExtra("coach_uid", uidCoach);
                startActivity(i);
            }
        });
        dietLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Client_diet.class);
                i.putExtra("coach_uid", uidCoach);
                startActivity(i);
            }
        });
        uploadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Client_measures.class);
                startActivity(i);
            }
        });
        statLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Statistics.class);
                i.putExtra("coach_uid", uidCoach);
                startActivity(i);
            }
        });
    }
}