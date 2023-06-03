package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Objects.Client;

public class Coach_selectClient extends AppCompatActivity {
    private static final String TAG = "FirebaseError";
    private List<Client> clientsWithMyCoachUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_select_client);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        LinearLayout list = findViewById(R.id.list);
        DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference("Clients");
        String myCoachUID = mAuth.getCurrentUser().getUid();



        Query query = clientsRef.orderByChild("uidMyCoach").equalTo(myCoachUID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientsWithMyCoachUID = new ArrayList<Client>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("nameSurname").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    int age = Integer.parseInt(snapshot.child("age").getValue().toString());
                    String uid =  snapshot.child("uid").getValue().toString();
                    Client c = new Client( name, email, age, uid, myCoachUID, false);
                    clientsWithMyCoachUID.add(c);
                }
                if(clientsWithMyCoachUID.isEmpty()){
                    TextView noClients = new TextView(Coach_selectClient.this);
                    int margin = 25;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(margin, margin, margin, margin);
                    layoutParams.gravity = Gravity.CENTER;

                    noClients.setLayoutParams(layoutParams);
                    noClients.setText("You don't have any clients assigned yet, please try again later.");
                    noClients.setTextSize(24);
                    noClients.setTextColor(Color.WHITE);
                    noClients.setGravity(Gravity.CENTER_HORIZONTAL);
                    list.addView(noClients);

                }
                for (Client client : clientsWithMyCoachUID) {
                    TextView clientBox = new TextView(Coach_selectClient.this);
                    int margin = 25;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(margin, margin, margin, margin);
                    layoutParams.gravity = Gravity.CENTER;

                    clientBox.setLayoutParams(layoutParams);
                    clientBox.setText(client.getNameSurname());
                    clientBox.setTextSize(24);
                    clientBox.setTextColor(Color.WHITE);
                    clientBox.setGravity(Gravity.CENTER_HORIZONTAL);

                    clientBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Coach_selectClient.this, Coach_menu.class);
                            intent.putExtra("client_uid", client.getUid());
                            intent.putExtra("name", client.getNameSurname());
                            startActivity(intent);
                        }
                    });
                    list.addView(clientBox);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error al obtener los clientes con el UID de mi entrenador", databaseError.toException());
            }
        });



    }
}