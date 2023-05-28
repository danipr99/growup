package com.example.growup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Objects.Measure;

public class Client_measures extends AppCompatActivity {
    private DatabaseReference reference;
    private ImageButton backButon;
    private EditText lArmEditText;
    private EditText rArmEditText;
    private EditText chestEditText;
    private EditText abdEditText;
    private EditText hipEditText;
    private EditText lThighEditText;
    private EditText rThighEditText;
    private EditText weightEditText;
    private TextView subir;
    private FirebaseAuth mAuth;
    private String uidClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_measures);
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uidClient = mAuth.getCurrentUser().getUid();
        backButon = findViewById(R.id.back_buton);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lArmEditText = findViewById(R.id.lArmEditText);
        rArmEditText = findViewById(R.id.rArmEditText);
        chestEditText = findViewById(R.id.chestEditText);
        abdEditText = findViewById(R.id.abdEditText);
        hipEditText = findViewById(R.id.hipEditText);
        lThighEditText = findViewById(R.id.lThighEditText);
        rThighEditText = findViewById(R.id.rThighEditText);
        weightEditText = findViewById(R.id.weightEditText);
        subir = findViewById(R.id.subir);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lArmValue = Integer.parseInt(lArmEditText.getText().toString());
                int rArmValue = Integer.parseInt(rArmEditText.getText().toString());
                int chestValue = Integer.parseInt(chestEditText.getText().toString());
                int abdValue = Integer.parseInt(abdEditText.getText().toString());
                int hipValue = Integer.parseInt(hipEditText.getText().toString());
                int lThiValue = Integer.parseInt(lThighEditText.getText().toString());
                int rThiValue = Integer.parseInt(rThighEditText.getText().toString());
                int weightValue = Integer.parseInt(weightEditText.getText().toString());

                Measure m1 = new Measure(lArmValue, rArmValue, chestValue, abdValue, hipValue, lThiValue, rThiValue, weightValue);
                reference.child("Measures").child(uidClient).child(String.valueOf(System.currentTimeMillis())).setValue(m1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}