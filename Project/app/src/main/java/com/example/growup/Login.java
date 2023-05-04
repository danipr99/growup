package com.example.growup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Objects.All;

public class Login extends AppCompatActivity {
    All all= new All();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView buttonRegister = findViewById(R.id.inRegister);
        Button buttonStart = findViewById(R.id.login_button);
        EditText emailInput = findViewById(R.id.email_edittext);
        EditText passwordInput = findViewById(R.id.password_edittext);
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent toCoach = new Intent(getApplicationContext(), coach_selectClient.class);
                Intent toClient = new Intent(getApplicationContext(), coach_selectClient.class);
                int code = all.loginPass(emailInput.getText().toString(), passwordInput.getText().toString());
                switch (code){
                    case -1:
                        System.out.println("User not found1");
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
            }
        });

    }

}