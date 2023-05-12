package com.example.growup;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Objects.All;
import Objects.Client;
import Objects.Coach;
import Objects.User;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailInput;
    EditText passwordInput;
    EditText cPasswordInput;
    EditText nameInput;
    EditText ageInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        All all = new All();

        setContentView(R.layout.activity_register);
        TextView buttonLogin = findViewById(R.id.inLogin);
        Button register_button = findViewById(R.id.register_button);

// Lista de opciones para el Spinner
        Spinner spinner = findViewById(R.id.spinnerRegister);
        List<String> options = new ArrayList<>();
        options.add("I'm a coach");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setPrompt("Select your coach");
        spinner.setAdapter(adapter);



        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), Login.class);
                startActivity(i1);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), Login.class);
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
                                        if(spinner.getSelectedItem().toString().equals("I'm a coach")){
                                            List<Client> clients = new ArrayList<Client>();

                                            Coach c = new Coach(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()), "uid",clients);
                                            saveUserinDB(c);
                                            //Inserción al conjunto de Coachs
                                            all.newCoach(c);
                                        }else{//Registro de un Client
                                            //Creación del client
                                            Client c = new Client(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()), all.searchCoach(spinner.getSelectedItem().toString()));
                                            saveUserinDB(c);
                                            all.newClient(c);

                                        }
                                        System.out.println(user.toString());

                                        startActivity(i1);
                                        finish();
                                    }else{
                                        Toast.makeText(Register.this, "Fatal error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("NOT WORKING");
                            Toast.makeText(Register.this, "Error in register: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                }else{
                    Toast.makeText(Register.this, "Complete data", Toast.LENGTH_SHORT).show();
                }


/*
                if(spinner.getSelectedItem().toString().equals("I'm a coach")){
                    ArrayList<Client> clients = new ArrayList<Client>();

                    Coach c = new Coach(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()), "uid",clients);
                    //Inserción al conjunto de Coachs
                    all.newCoach(c);
                }else{//Registro de un Client
                    //Creación del client
                    Client c = new Client(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()), all.searchCoach(spinner.getSelectedItem().toString()));
                    all.newClient(c);

                }
                startActivity(i1);*/
            }
        });

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