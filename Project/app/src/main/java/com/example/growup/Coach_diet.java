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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_diet);
        body = findViewById(R.id.body);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String coach = mAuth.getCurrentUser().getUid();
        createdietLinearLayouts(coach, "bulk");
    }
    private void createdietLinearLayouts(String coachUid, String dietType) {


            DatabaseReference routinesRef = FirebaseDatabase.getInstance().getReference("diet").child(coachUid).child(dietType);

            routinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {

                        String meal = exerciseSnapshot.child("name").getValue().toString();

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
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        nameTextView.setText(meal);
                        nameTextView.setTextColor(Color.WHITE);
                        nameTextView.setTextSize(24);
                        textLayout.setMargins(40, 10, 30, 10);
                        nameTextView.setLayoutParams(textLayout);


                        ImageView arrowImageView = new ImageView(Coach_diet.this);
                        LinearLayout.LayoutParams arrowLayout = new LinearLayout.LayoutParams(
                                60,
                                60,
                                RelativeLayout.ALIGN_RIGHT

                        );
                        arrowLayout.setMargins(0, 40, 0, 40);
                        arrowImageView.setLayoutParams(arrowLayout);
                        arrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow));


                        // Agregamos los elementos al LinearLayout
                        topLayout.addView(nameTextView);
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
                        String series = exerciseSnapshot.child("id").getValue().toString();
                        infoText.setText("Series: " + series);
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

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error de la lectura de datos desde Firebase
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

}