package com.example.growup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Client_Training extends AppCompatActivity {
    private String clientUid;
    private static final String TAG = "LOG";
    private final long MAX_SIZE = 1024 * 1024;
    private String coach;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference storageRef1;
    private int selectedTextView = -1;
    private TextView clientName;
    private ImageButton backButton;
    private TextView monthTextView;
    private TextView dayTextView;
    private int dayOfWeek;
    private int dayOfMonth;
    private int month;
    private LinearLayout body;
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_training);
        String client = getIntent().getStringExtra("name");
        coach = getIntent().getStringExtra("coach_uid");
        dayOfWeek = getIntent().getIntExtra("dayOfWeek", 0);
        dayOfMonth = getIntent().getIntExtra("dayOfMonth", 0);
        month = getIntent().getIntExtra("month", 0);
        storage = FirebaseStorage.getInstance();
        body = findViewById(R.id.body);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        clientUid = mAuth.getCurrentUser().getUid();
        clientName = findViewById(R.id.nameClient);
        clientName.setText(client);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        monthTextView = findViewById(R.id.monthTextView);
        monthTextView.setText(months[month]);
        dayTextView = findViewById(R.id.dayTextView);
        Log.e("FECHAS", dayOfWeek + " " + dayOfMonth);
        String type;
        dayTextView.setText(days[dayOfWeek-1] + " " + dayOfMonth);
        switch (dayOfWeek){
            case 1:
                type="Push";
                break;
            case 3:
                type="Pull";
                break;
            case 5:
                type="Leg";
                break;
            default:
                type="Rest";
                break;
        }


        createExerciseLinearLayouts(coach, type);
    }
    private void createExerciseLinearLayouts(String coachUid, String routineType) {
        if (!routineType.equals("Rest")) {

            DatabaseReference routinesRef = FirebaseDatabase.getInstance().getReference("Routines").child(coachUid).child(routineType);

            routinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {

                        String exerciseName = exerciseSnapshot.child("name").getValue().toString();
                        Log.e(TAG, exerciseName);

                        //Layout de la caja del ejercicio
                        LinearLayout exerciseLayout = new LinearLayout(Client_Training.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        exerciseLayout.setOrientation(LinearLayout.VERTICAL);
                        exerciseLayout.setBackgroundResource(R.drawable.box);
                        layoutParams.setMargins(20, 5, 20, 5);
                        exerciseLayout.setLayoutParams(layoutParams);

                        LinearLayout topLayout = new LinearLayout(Client_Training.this);
                        LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        topLayout.setOrientation(LinearLayout.HORIZONTAL);
                        topParams.setMargins(20, 5, 20, 5);
                        topLayout.setLayoutParams(topParams);


                        TextView nameTextView = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams textLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        nameTextView.setText(exerciseName);
                        nameTextView.setTextColor(Color.WHITE);
                        nameTextView.setTextSize(24);
                        textLayout.setMargins(40, 10, 30, 10);
                        nameTextView.setLayoutParams(textLayout);

                        ImageView imageView = new ImageView(Client_Training.this);
                        LinearLayout.LayoutParams imageLayout = new LinearLayout.LayoutParams(
                                100,
                                100,
                                RelativeLayout.ALIGN_RIGHT

                        );
                        imageLayout.setMargins(0, 20, 0, 20);
                        imageView.setLayoutParams(imageLayout);
                        //Cargar imagen
                        String imageUrl = exerciseSnapshot.child("feedback").getValue().toString() + ".png";

                        storageRef = storage.getReference().child(imageUrl);

                        storageRef.getBytes(MAX_SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageView.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e(TAG, "Error al cargar imagen ");

                            }
                        });

                        ImageView arrowImageView = new ImageView(Client_Training.this);
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
                        topLayout.addView(imageView);
                        topLayout.addView(arrowImageView);
                        exerciseLayout.addView(topLayout);

                        // Layout de la información del ejercicio

                        LinearLayout infoLayout = new LinearLayout(Client_Training.this);
                        infoLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams bodyParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        infoLayout.setLayoutParams(bodyParams);
                        infoLayout.setVisibility(View.GONE);
                        //TextViews que se insertaran a infoLayout
                        TextView seriesTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams seriesLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String series = exerciseSnapshot.child("id").getValue().toString();
                        seriesTV.setText("Series: " + series);
                        seriesTV.setTextColor(Color.WHITE);
                        seriesTV.setTextSize(20);
                        seriesLayout.setMargins(60, 10, 20, 10);
                        seriesTV.setLayoutParams(seriesLayout);
                        infoLayout.addView(seriesTV);

                        TextView kgTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams kgLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String kg = exerciseSnapshot.child("kg").getValue().toString();
                        kgTV.setText("KG: " + kg);
                        kgTV.setTextColor(Color.WHITE);
                        kgTV.setTextSize(20);
                        kgLayout.setMargins(60, 10, 20, 10);
                        kgTV.setLayoutParams(kgLayout);
                        infoLayout.addView(kgTV);

                        TextView repTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams repLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String rep = exerciseSnapshot.child("repetitions").getValue().toString();
                        repTV.setText("Repetitions: " + rep);
                        repTV.setTextColor(Color.WHITE);
                        repTV.setTextSize(20);
                        repLayout.setMargins(60, 10, 20, 10);
                        repTV.setLayoutParams(repLayout);
                        infoLayout.addView(repTV);

                        TextView recTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams recLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String rec = exerciseSnapshot.child("rec").getValue().toString();
                        recTV.setText("REC: " + rec);
                        recTV.setTextColor(Color.WHITE);
                        recTV.setTextSize(20);
                        recLayout.setMargins(60, 10, 20, 10);
                        recTV.setLayoutParams(recLayout);
                        infoLayout.addView(recTV);

                        TextView rirTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams rirLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String rir = exerciseSnapshot.child("rir").getValue().toString();
                        rirTV.setText("RIR: " + rir);
                        rirTV.setTextColor(Color.WHITE);
                        rirTV.setTextSize(20);
                        rirLayout.setMargins(60, 10, 20, 10);
                        rirTV.setLayoutParams(rirLayout);
                        infoLayout.addView(rirTV);

                        TextView volTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams volLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String vol = exerciseSnapshot.child("volume").getValue().toString();
                        volTV.setText("Volume: " + vol);
                        volTV.setTextColor(Color.WHITE);
                        volTV.setTextSize(20);
                        volLayout.setMargins(60, 10, 20, 10);
                        volTV.setLayoutParams(volLayout);
                        infoLayout.addView(volTV);

                        TextView detTV = new TextView(Client_Training.this);
                        LinearLayout.LayoutParams detLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        String det = exerciseSnapshot.child("details").getValue().toString();
                        detTV.setText("Details: " + det);
                        detTV.setTextColor(Color.WHITE);
                        detTV.setTextSize(20);
                        detLayout.setMargins(60, 10, 20, 10);
                        detTV.setLayoutParams(detLayout);
                        infoLayout.addView(detTV);

                        LinearLayout feedbackLayout = new LinearLayout(Client_Training.this);
                        feedbackLayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams fbParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        feedbackLayout.setLayoutParams(fbParams);
                        feedbackLayout.setVisibility(View.GONE);
                        feedbackLayout.setGravity(Gravity.CENTER); // Alinear a la izquierda
                        feedbackLayout.setWeightSum(5);

                        // Crear un LinearLayout para cada imagen y texto
                        ImageView[] emojiView = new ImageView[5];
                        List<TextView> textViewList = new ArrayList<>();

                        for (int i = 0; i < 5; i++) {
                            LinearLayout imageTextLayout = new LinearLayout(Client_Training.this);
                            imageTextLayout.setOrientation(LinearLayout.VERTICAL);
                            emojiView[i] = new ImageView(Client_Training.this);


                            LinearLayout.LayoutParams emojiLayout = new LinearLayout.LayoutParams(
                                    130,
                                    130, 1


                            );
                            emojiLayout.setMargins(30, 20, 30, 20);
                            emojiView[i].setLayoutParams(emojiLayout);
                            //Cargar imagen
                            String emojiURL = i + ".png";

                            storageRef = storage.getReference().child(emojiURL);
                            int finalI = i;
                            storageRef.getBytes(MAX_SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    emojiView[finalI].setImageBitmap(bitmap);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.e(TAG, "Error al cargar imagen ");

                                }
                            });

                            // Texto bajo emoji
                            TextView textView = new TextView(Client_Training.this);
                            textViewList.add(textView);

                            String textFeedback;
                            switch (i) {
                                case 0:
                                    textFeedback = "Injured";
                                    break;
                                case 1:
                                    textFeedback = "Easy";
                                    break;
                                case 2:
                                    textFeedback = "Affordable";
                                    break;
                                case 3:
                                    textFeedback = "Hard";
                                    break;
                                case 4:
                                    textFeedback = "Impossible";
                                    break;
                                default:
                                    textFeedback = "ERROR";
                            }
                            textView.setText(textFeedback);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            textView.setGravity(Gravity.CENTER_HORIZONTAL);
                            final int index = i; // Identificador único para el TextView


                            imageTextLayout.addView(emojiView[i]);
                            imageTextLayout.addView(textView);
                            imageTextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (selectedTextView != index) {
                                        // Cambiar el color del TextView actualmente seleccionado (si existe)
                                        if (selectedTextView != -1) {
                                            textViewList.get(selectedTextView).setTextColor(Color.WHITE);
                                        }

                                        // Establecer el TextView actualmente seleccionado
                                        selectedTextView = index;

                                        routinesRef.child(exerciseName).child("feedback").setValue(index);
                                        textView.setTextColor(Color.parseColor("#804191"));
                                    }
                                }
                            });
                            feedbackLayout.addView(imageTextLayout);
                        }

                        // Layout de la foto explicativa del ejercicio
                        LinearLayout photoLayout = new LinearLayout(Client_Training.this);
                        photoLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams photoParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        photoLayout.setLayoutParams(photoParams);
                        photoLayout.setVisibility(View.GONE);
                        ImageView photoView = new ImageView(Client_Training.this);
                        LinearLayout.LayoutParams sphotoLayout = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT, 1


                        );
                        sphotoLayout.setMargins(30, 30, 30, 30);
                        photoView.setLayoutParams(sphotoLayout);
                        //Cargar imagen


                        readData(routineType, exerciseName, new Client_Training.FirebaseCallBack() {
                            @Override
                            public void onCallback(String result) {
                                Log.e("IMAGEN", result);
                                storageRef1 = storage.getReference().child(result);
                                storageRef1.getBytes(MAX_SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        photoView.setImageBitmap(bitmap);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Log.e(TAG, "Error al cargar imagen ");

                                    }
                                });

                            }
                        });

                        exerciseLayout.addView(infoLayout);
                        exerciseLayout.addView(feedbackLayout);
                        photoLayout.addView(photoView);
                        exerciseLayout.addView(photoLayout);

                        // Listener de la flecha
                        arrowImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Verificar si los layouts adicionales están actualmente visibles o no
                                if (infoLayout.getVisibility() == View.VISIBLE) {
                                    // Si están visibles, ocultarlos
                                    infoLayout.setVisibility(View.GONE);
                                    feedbackLayout.setVisibility(View.GONE);
                                    photoLayout.setVisibility(View.GONE);
                                    rotateArrow(arrowImageView, 0f);
                                } else {
                                    // Si están ocultos, mostrarlos
                                    infoLayout.setVisibility(View.VISIBLE);
                                    feedbackLayout.setVisibility(View.VISIBLE);
                                    photoLayout.setVisibility(View.VISIBLE);
                                    rotateArrow(arrowImageView, 180f);
                                }
                            }
                        });
                        body.addView(exerciseLayout);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error de la lectura de datos desde Firebase
                }
            });
        }else{// DIA DE DESCANSO
            LinearLayout restLayout = new LinearLayout(Client_Training.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    300
            );
            restLayout.setOrientation(LinearLayout.VERTICAL);
            restLayout.setBackgroundResource(R.drawable.box);
            layoutParams.setMargins(20, 5, 20, 5);
            restLayout.setLayoutParams(layoutParams);


            TextView restText = new TextView(Client_Training.this);
            LinearLayout.LayoutParams textLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            restText.setText("Enjoy your rest day");
            restText.setTextColor(Color.WHITE);
            restText.setTextSize(26);
            textLayout.setMargins(40, 20, 30, 20);
            restText.setLayoutParams(textLayout);
            restLayout.addView(restText);
            body.addView(restLayout);

        }
    }

    private void rotateArrow(ImageView arrowImageView, float degree) {
        // Crear un objeto AnimatorSet para animar la rotación de la flecha
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(arrowImageView, "rotation", degree);

        rotation.setDuration(300);

        animatorSet.play(rotation);

        animatorSet.start();
    }
    private void readData(String routineType, String exerciseName, Client_Training.FirebaseCallBack firebaseCallBack){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Routines").child(coach).child(routineType).child(exerciseName).child("photo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String photo = snapshot.getValue().toString();
                    firebaseCallBack.onCallback(photo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error al descargar imagen ");

            }
        });
    }

    private interface FirebaseCallBack{
        void onCallback(String result);
    }


}