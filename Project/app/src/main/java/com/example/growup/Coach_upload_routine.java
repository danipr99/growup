package com.example.growup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Objects.Client;
import Objects.Coach;

public class Coach_upload_routine extends AppCompatActivity {
    private String coach;
    private TextView clientName;
    private ImageButton backButton;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Routines");
    private ArrayList<String> list;
    private LinearLayout linearLayout;
    private LinearLayout newRoutine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_upload_routine);
        String client = getIntent().getStringExtra("name");
        String clientUid = getIntent().getStringExtra("client_uid");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        coach = mAuth.getCurrentUser().getUid();
        linearLayout = findViewById(R.id.list);
        newRoutine = findViewById(R.id.newRoutine);
        newRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Coach_upload_routine.this, Coach_upload_train.class);
                intent.putExtra("client_uid", clientUid);
                intent.putExtra("name", client);
                intent.putExtra("routine", "New");
                startActivity(intent);
            }
        });
        clientName = findViewById(R.id.nameClient);
        clientName.setText(client);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DatabaseReference coachTrains = reference.child(coach);


// Agregar un listener para obtener los datos de los hijos de Clients
        coachTrains.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterar sobre cada hijo de Clients
                for (DataSnapshot routine : dataSnapshot.getChildren()) {
                    String key = routine.getKey();
                    TextView routName = new TextView(Coach_upload_routine.this);
                    int margin = 25;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT

                    );
                    layoutParams.setMargins(margin, margin, margin, margin);
                    layoutParams.gravity = Gravity.CENTER;

                    routName.setLayoutParams(layoutParams);
                    routName.setText(key);
                    routName.setTextSize(26);
                    routName.setBackgroundResource(R.drawable.box);
                    routName.setTextColor(Color.WHITE);

                    routName.setGravity(Gravity.CENTER_HORIZONTAL);

                    routName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Coach_upload_routine.this, Coach_upload_train.class);
                            intent.putExtra("client_uid", clientUid);
                            intent.putExtra("name", client);
                            intent.putExtra("routine", key);
                            startActivity(intent);
                        }
                    });
                    linearLayout.addView(routName);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer los datos: " + databaseError.getMessage());
            }
        });

    }




/*
    private void readData(Coach_upload_routine.FirebaseCallBack firebaseCallBack){
        getListofRoutines();

    }
    private interface FirebaseCallBack{
        void onCallback(boolean result);
    }*/
}