package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import Objects.All;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    All all= new All();
    TextView buttonRegister;
    Button buttonStart;
    EditText emailInput;
    EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonStart = findViewById(R.id.login_button);
        buttonRegister = findViewById(R.id.inRegister);
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent toCoach = new Intent(getApplicationContext(), coach_selectClient.class);
                Intent toClient = new Intent(getApplicationContext(), coach_selectClient.class);


                emailInput = findViewById(R.id.email_edittext);
                passwordInput = findViewById(R.id.password_edittext);
                String email =emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    mAuth.getCurrentUser().getUid();
                                    Intent i1 = new Intent(getApplicationContext(), Register.class);
                                    startActivity(i1);
                                    finish();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this,
                                            "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                                }

                        });




                int code = all.loginPass(email, password);
                switch (code){
                    case -1:
                        System.out.println("User not found");
                        break;
                    case 0:

                        startActivity(toClient);
                       break;
                    case 1:
                        startActivity(toCoach);
                        break;
                }



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
/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            login();
            finish();
        }
    }*/

    private void login(){
    }
}