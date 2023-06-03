package com.example.growup;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.List;

import Objects.All;
import Objects.Client;
import Objects.Coach;
import Objects.User;

public class Register extends AppCompatActivity {
    private static final String TAG = "FirebaseError";
    private DatabaseReference coachsRef = FirebaseDatabase.getInstance().getReference("Coachs");
    private ArrayList<Coach> coachs = new ArrayList<Coach>();
    private FirebaseAuth mAuth;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText cPasswordInput;
    private EditText nameInput;
    private EditText ageInput;
    private CheckBox isBulk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Intent login = new Intent(getApplicationContext(), Login.class);
        setContentView(R.layout.activity_register);
        TextView buttonLogin = findViewById(R.id.inLogin);
        Button register_button = findViewById(R.id.register_button);

// Lista de opciones para el Spinner
        Spinner spinner = findViewById(R.id.spinnerRegister);

        LinearLayout checkboxLayout = findViewById(R.id.checkboxLayout);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = parent.getItemAtPosition(position).toString();

                // Verificar si la opción seleccionada es "Im a coach"
                if (selectedRole.equals("I'm a coach")) {
                    checkboxLayout.setVisibility(View.GONE); // Ocultar el LinearLayout
                } else {
                    checkboxLayout.setVisibility(View.VISIBLE); // Mostrar el LinearLayout
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> options = getListofCoachs();
        options.add("I'm a coach");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select your coach");
        spinner.setAdapter(adapter);
        isBulk = findViewById(R.id.checkbox);



        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(login);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                emailInput = findViewById(R.id.email_edittext);
                passwordInput = findViewById(R.id.password_edittext);
                cPasswordInput = findViewById(R.id.cPassword_edittext);
                nameInput = findViewById(R.id.nameSurname_edittext);
                ageInput = findViewById(R.id.age_edittext);
                String mail = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String name = nameInput.getText().toString();
                int age = Integer.parseInt(ageInput.getText().toString());
                String uid;
                //Validación del login
                if(!isEmpty(mail) && !isEmpty(password) && password.equals(cPasswordInput.getText().toString())){
                        //Registro de un coach
                        try{
                            mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User register complete", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //Compruebo si es un Coach o un cliente
                                        if(spinner.getSelectedItem().toString().equals("I'm a coach")){//Registro de un Coach
                                            List<Client> clients = new ArrayList<Client>();

                                            Coach c = new Coach(name, mail, age, FirebaseAuth.getInstance().getCurrentUser().getUid(),clients);
                                            saveUserinDB(c);
                                            //Inserción al conjunto de Coachs
                                        }else{//Registro de un Client

                                            String uidCoach = searchCoach(spinner.getSelectedItem().toString());
                                            Client c = new Client(name, mail, age, FirebaseAuth.getInstance().getCurrentUser().getUid(), uidCoach, isBulk.isChecked());
                                            saveUserinDB(c);
                                        }
                                        startActivity(login);
                                        finish();
                                    }else{
                                        Toast.makeText(Register.this, "Fatal error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(Register.this, "Error in register: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Toast.makeText(Register.this, "Complete data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String searchCoach(String name) {
        String result = "";
        for(Coach one : coachs){
            if(one.getNameSurname().equals(name)){
                result = one.getUid();
            }
        }
        return result;
    }

    private ArrayList<String> getListofCoachs() {
        ArrayList<String> result = new ArrayList<String>();
// Agregar un listener para obtener los datos de los hijos de Clients
        coachsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    Coach coach = new Coach();
                    String w = clientSnapshot.getKey();
                    coach.setUid(w);
                    coach.setNameSurname(clientSnapshot.child("nameSurname").getValue().toString());
                    coach.setEmail(clientSnapshot.child("email").getValue().toString());
                    coach.setAge(Integer.parseInt(clientSnapshot.child("age").getValue().toString()));

                    result.add(coach.getNameSurname());
                    coachs.add(coach);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer los datos: " + databaseError.getMessage());
            }
        });
        return result;
    }

    public void saveUserinDB(User usuario) {
        DatabaseReference ref;
        if (usuario instanceof Client) {
            ref = FirebaseDatabase.getInstance().getReference().child("Clients");
        } else if (usuario instanceof Coach) {
            ref = FirebaseDatabase.getInstance().getReference().child("Coachs");
        } else {
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref.child(uid).setValue(usuario);
    }

}