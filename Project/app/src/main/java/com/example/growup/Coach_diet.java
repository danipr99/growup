package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Coach_diet extends AppCompatActivity {
    private LinearLayout body;
    private boolean bulk = true;
    private TextView clientName;
    private TextView macro1;
    private TextView macro2;
    private TextView macro3;
    private TextView macro4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_diet);
        String client = getIntent().getStringExtra("name");
        String clientUid = getIntent().getStringExtra("client_uid");
        body = findViewById(R.id.body);
        clientName = findViewById(R.id.nameClient);
        clientName.setText(client);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String coach = mAuth.getCurrentUser().getUid();
        Log.e("Diet:", clientUid);
        macro1 = findViewById(R.id.kcalValue);
        macro2 = findViewById(R.id.carbsValue);
        macro3 = findViewById(R.id.protValue);
        macro4 = findViewById(R.id.fatValue);

        DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference("Clients").child(clientUid);
        clientRef.child("isBulk").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isBulk = dataSnapshot.getValue(Boolean.class);
                if (isBulk != null) {
                    bulk= isBulk;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Diet:", "FALLOOO");
            }
        });
        if(bulk){
            createdietLinearLayouts(coach, "Bulk");
        }else{
            createdietLinearLayouts(coach, "Cut");

        }
    }
    private void createdietLinearLayouts(String coachUid, String dietType) {

        Log.e("Diet:", "Aqui llega");
        DatabaseReference dietRef = FirebaseDatabase.getInstance().getReference("Diet").child(coachUid).child(dietType);

        dietRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e("Diet:", "Aqui llega tambien");

                    macro1.setText(dataSnapshot.child("kcal").getValue().toString());
                    macro2.setText(dataSnapshot.child("carbs").getValue().toString());
                    macro3.setText(dataSnapshot.child("prot").getValue().toString());
                    macro4.setText(dataSnapshot.child("fat").getValue().toString());
                    String infoBreakfast = dataSnapshot.child("breakfast").getValue().toString();
                    createMeal("Breakfast", infoBreakfast);
                    String infoLunch = dataSnapshot.child("lunch").getValue().toString();
                    createMeal("Lunch", infoLunch);
                    String infoDinner = dataSnapshot.child("dinner").getValue().toString();
                    createMeal("Dinner", infoDinner);
                    String infoSnack = dataSnapshot.child("snack").getValue().toString();
                    createMeal("Snack", infoSnack);
                    String inforecom = dataSnapshot.child("recommendations").getValue().toString();
                    createMeal("Recommendations", inforecom);




                } else {
                    // La dieta no existe en la base de datos

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Ocurrió un error al acceder a los datos de la dieta
                // Maneja el error según tus necesidades
            }
        });


    }

    private void rotateArrow(ImageView arrowImageView, float degree) {
        // Crear un objeto AnimatorSet para animar la rotación de la flecha
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(arrowImageView, "rotation", degree);

        rotation.setDuration(300);

        animatorSet.play(rotation);

        animatorSet.start();
    }
    private void createMeal(String meal, String info){


        //Layout de la caja del ejercicio
        LinearLayout mealLayout = new LinearLayout(Coach_diet.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mealLayout.setOrientation(LinearLayout.VERTICAL);
        mealLayout.setBackgroundResource(R.drawable.box);
        layoutParams.setMargins(20, 5, 20, 5);
        mealLayout.setLayoutParams(layoutParams);

        LinearLayout topLayout = new LinearLayout(Coach_diet.this);
        LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        topLayout.setOrientation(LinearLayout.HORIZONTAL);
        topParams.setMargins(20, 5, 20, 5);
        topLayout.setLayoutParams(topParams);

        TextView nameTextView = new TextView(Coach_diet.this);
        LinearLayout.LayoutParams textLayout = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        if(meal.equals("Recommendations")){
            meal="Tips";
        }
        nameTextView.setText(meal);
        nameTextView.setTextColor(Color.WHITE);
        nameTextView.setTextSize(24);
        textLayout.setMargins(40, 10, 30, 10);
        nameTextView.setLayoutParams(textLayout);

        Space espacioFlexible = new Space(Coach_diet.this);
        LinearLayout.LayoutParams espacioParams = new LinearLayout.LayoutParams(
                0,
                0,
                1.0f
        );
        espacioFlexible.setLayoutParams(espacioParams);

        ImageView arrowImageView = new ImageView(Coach_diet.this);
        LinearLayout.LayoutParams arrowLayout = new LinearLayout.LayoutParams(
                100,
                100
        );
        arrowImageView.setForegroundGravity(Gravity.END);
        arrowLayout.setMargins(0, 20, 20, 20);
        arrowImageView.setLayoutParams(arrowLayout);
        arrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow));


        // Agregamos los elementos al LinearLayout
        topLayout.addView(nameTextView);
        topLayout.addView(espacioFlexible);
        topLayout.addView(arrowImageView);
        mealLayout.addView(topLayout);

        // Layout de la información del ejercicio

        LinearLayout infoLayout = new LinearLayout(Coach_diet.this);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams bodyParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        infoLayout.setLayoutParams(bodyParams);
        infoLayout.setVisibility(View.GONE);
        //TextViews que se insertaran a infoLayout
        TextView infoText = new TextView(Coach_diet.this);
        LinearLayout.LayoutParams seriesLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        infoText.setText(info);
        infoText.setTextColor(Color.WHITE);
        infoText.setTextSize(20);
        seriesLayout.setMargins(60, 10, 20, 10);
        infoText.setLayoutParams(seriesLayout);
        infoLayout.addView(infoText);
        mealLayout.addView(infoLayout);


        // Listener de la flecha
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si los layouts adicionales están actualmente visibles o no
                if (infoLayout.getVisibility() == View.VISIBLE) {
                    // Si están visibles, ocultarlos
                    infoLayout.setVisibility(View.GONE);
                    rotateArrow(arrowImageView, 0f);
                } else {
                    // Si están ocultos, mostrarlos
                    infoLayout.setVisibility(View.VISIBLE);

                    rotateArrow(arrowImageView, 180f);
                }
            }
        });
        body.addView(mealLayout);

    }

}