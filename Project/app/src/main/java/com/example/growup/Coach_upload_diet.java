package com.example.growup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Objects.Diet;

public class Coach_upload_diet extends AppCompatActivity {

    private ImageButton backButton;
    private FirebaseAuth mAuth;
    private DatabaseReference referenceDiet;
    private TextView clientName;
    private Spinner spinnerTipoDieta;


    private EditText desayunoEditText;
    private EditText comidaEditText;
    private EditText cenaEditText;
    private EditText snackEditText;
    private EditText recomendacionesEditText;
    private EditText kcalEditText;
    private EditText carbsEditText;
    private EditText protEditText;
    private EditText fatsEditText;
    private Button subirButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_upload_diet);
        String client = getIntent().getStringExtra("name");
        mAuth = FirebaseAuth.getInstance();
        String coach = mAuth.getCurrentUser().getUid();
        clientName = findViewById(R.id.nameClient);
        clientName.setText(client);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinnerTipoDieta = findViewById(R.id.spinnerTipoDieta);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Bulk");
        adapter.add("Cut");
        spinnerTipoDieta.setPrompt("Select your coach");
        spinnerTipoDieta.setAdapter(adapter);




        desayunoEditText = findViewById(R.id.desayunoEditText);
        comidaEditText = findViewById(R.id.comidaEditText);
        cenaEditText = findViewById(R.id.cenaEditText);
        snackEditText = findViewById(R.id.snackEditText);
        recomendacionesEditText = findViewById(R.id.recomendacionesEditText);
        kcalEditText = findViewById(R.id.kcalEditText);
        carbsEditText = findViewById(R.id.carbsEditText);
        protEditText = findViewById(R.id.protEditText);
        fatsEditText = findViewById(R.id.fatEditText);
        subirButton = findViewById(R.id.subirButton);
        subirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Diet diet = new Diet(desayunoEditText.getText().toString(), comidaEditText.getText().toString(), cenaEditText.getText().toString(), snackEditText.getText().toString(), recomendacionesEditText.getText().toString(), Integer.parseInt(kcalEditText.getText().toString()), Integer.parseInt(carbsEditText.getText().toString()), Integer.parseInt(protEditText.getText().toString()), Integer.parseInt(fatsEditText.getText().toString()), coach);
                if(spinnerTipoDieta.getSelectedItem().equals("Bulk")){

                    referenceDiet = FirebaseDatabase.getInstance().getReference().child("Diet").child(coach);

                    referenceDiet.child("Bulk").setValue(diet).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Dieta guardada correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(spinnerTipoDieta.getSelectedItem().equals("Cut")){
                    referenceDiet = FirebaseDatabase.getInstance().getReference().child("Diet").child(coach);
                    referenceDiet.child("Cut").setValue(diet).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Dieta guardada correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}