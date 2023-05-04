package com.example.growup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import Objects.All;
import Objects.Client;
import Objects.Coach;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
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

        EditText emailInput = findViewById(R.id.email_edittext);
        EditText passwordInput = findViewById(R.id.password_edittext);
        EditText cPasswordInput = findViewById(R.id.cPassword_edittext);
        EditText nameInput = findViewById(R.id.nameSurname_edittext);
        EditText ageInput = findViewById(R.id.age_edittext);

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
                if(emailInput.getText() != null && passwordInput.getText() != null){
                    if(passwordInput.getText()==cPasswordInput.getText()){
                        if(spinner.getSelectedItem().toString()=="I'm a coach"){

                            //Registro de un coach

                            ArrayList<Client> clients = new ArrayList<Client>();
                            //Creación del coach
                            Coach c = new Coach(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()),clients);
                            //Inserción al conjunto de Coachs
                            all.newCoach(c);
                        }else{//Registro de un Client
                            //Creación del client
                            Client c = new Client(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()), all.searchCoach(spinner.getSelectedItem().toString()));
                            all.newClient(c);

                        }
                    }
                }





                startActivity(i1);
            }
        });

    }
}