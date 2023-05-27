package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class Coach_upload extends AppCompatActivity {
    ImageButton backButton;
    TextView clientName;

    LinearLayout uploadTrain;
    LinearLayout uploadDiet;
    private String coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_upload);
        String client = getIntent().getStringExtra("name");
        String clientUid = getIntent().getStringExtra("client_uid");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        coach = mAuth.getCurrentUser().getUid();
        clientName = findViewById(R.id.nameClient);
        clientName.setText(client);

        uploadTrain = findViewById(R.id.trainButton);
        uploadTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Coach_upload.this, Coach_upload_routine.class);
                i1.putExtra("name", client);
                i1.putExtra("client_uid", clientUid);
                startActivity(i1);
            }
        });
        uploadDiet = findViewById(R.id.dietButton);
        uploadDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), Coach_upload_diet.class);
                i1.putExtra("name", client);
                i1.putExtra("client_uid", clientUid);
                startActivity(i1);
            }
        });
        //displayTrainingTypes(coach);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /*private void displayTrainingTypes(String uidCoach) {//Cada entrenador tiene sus dietas o cada usuario tiene sus dietas
        DatabaseReference databaseRefTrain = FirebaseDatabase.getInstance().getReference().child("diet");
        Query query = databaseRefTrain.orderByChild("Coach").equalTo(uidCoach);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String dietType;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    dietType = snapshot.getKey();
                        TextView textView = new TextView(Coach_upload.this);

                        int margin = 25;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(margin, margin, margin, margin);
                        layoutParams.gravity = Gravity.CENTER;

                        textView.setLayoutParams(layoutParams);
                        textView.setText(trainingType);
                        textView.setTextSize(24);
                        textView.setTextColor(Color.WHITE);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        trainList.addView(textView);
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


}