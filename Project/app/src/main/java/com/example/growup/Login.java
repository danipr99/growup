package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import Objects.All;
import Objects.Client;
import Objects.Coach;

public class Login extends AppCompatActivity {
    private static final String TAG = "FirebaseError";
    private FirebaseAuth mAuth;
    All all= new All();
    TextView buttonRegister;
    Button buttonStart;
    EditText emailInput;
    EditText passwordInput;
    String userId;
    ArrayList<Integer> prueba = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        actualizarAll();


        buttonStart = findViewById(R.id.login_button);
        buttonRegister = findViewById(R.id.inRegister);

        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                emailInput = findViewById(R.id.email);
                passwordInput = findViewById(R.id.password);
                String email =emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    userId=user.getUid();
                                    //Comprobar si es client o coach
                                    readData(new FirebaseCallBack() {
                                        @Override
                                        public void onCallback(boolean isClient) {
                                            if(isClient){
                                                Intent toClient = new Intent(getApplicationContext(), Client_menu.class);
                                                startActivity(toClient);
                                                finish();
                                            }else{
                                                Intent toCoach = new Intent(getApplicationContext(), Coach_selectClient.class);
                                                startActivity(toCoach);
                                                finish();
                                            }
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this,
                                            "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                                }

                        });
            }
        });
        //Interacción con Register
        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), Register.class);
                startActivity(i1);
                finish();
            }
        });

    }

    private void actualizarAll() {
        // Obtener referencia a los nodos Clients y Coachs
        DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference("Clients");
        DatabaseReference coachsRef = FirebaseDatabase.getInstance().getReference("Coachs");

// Agregar un listener para obtener los datos de los hijos de Clients
        coachsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterar sobre cada hijo de Clients
                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    // Obtener el valor del hijo como un objeto Map

                    // Convertir el objeto Map en un objeto Client
                    Client client = new Client();
                    client.setUid(clientSnapshot.getKey());
                    client.setNameSurname(clientSnapshot.child("nameSurname").getValue().toString());//Seguir con los de abajo
                    client.setEmail(clientSnapshot.child("email").getValue().toString());
                    client.setPasword(clientSnapshot.child("pasword").getValue().toString());
                    client.setAge(Integer.parseInt(clientSnapshot.child("age").getValue().toString()));
                    all.newClient(client);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer los datos: " + databaseError.getMessage());
            }
        });

// Agregar un listener para obtener los datos de los hijos de Coachs
        coachsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterar sobre cada hijo de Coachs

                for (DataSnapshot coachSnapshot : dataSnapshot.getChildren()) {
                    // Obtener el valor del hijo como un objeto Map
                    Map<String, Object> coachMap = (Map<String, Object>) coachSnapshot.getValue();
                    // Convertir el objeto Map en un objeto Coach
                    Coach coach = new Coach();
                    coach.setUid(coachSnapshot.getKey());
                    coach.setNameSurname((String) coachMap.get("nameSurname"));
                    coach.setEmail((String) coachMap.get("email"));
                    coach.setPasword((String) coachMap.get("pasword"));
                    all.newCoach(coach);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer los datos: " + databaseError.getMessage());
            }
        });

    }

    private void readData(FirebaseCallBack firebaseCallBack){
        boolean[] result = {false};
        DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference().child("Clients");
        DatabaseReference coachsRef = FirebaseDatabase.getInstance().getReference().child("Coachs");
        Log.e(TAG, "userId: " + userId);

        clientsRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "dataSnapshot: " + dataSnapshot.getKey());
                if (dataSnapshot.exists()) {
                    Log.e(TAG, "Entro en Clients!");
                    result[0] = true;
                    prueba.add(0);
                }
                firebaseCallBack.onCallback(result[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer los datos: " + databaseError.getMessage());
            }
        });

    }
    private interface FirebaseCallBack{
        void onCallback(boolean result);
    }
}
