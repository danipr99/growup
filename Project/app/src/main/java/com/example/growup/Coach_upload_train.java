package com.example.growup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Objects.Diet;
import Objects.Exercice;

public class Coach_upload_train extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private ImageButton backButton;
    private FirebaseAuth mAuth;
    private DatabaseReference referenceExer;
    private StorageReference storageReference;
    private Uri imageUri;
    private TextView clientName;
    private Spinner spinnerTipoDieta;
    private EditText trainName;
    private EditText exerNameEditText;
    private EditText kgEditText;
    private EditText repEditText;
    private EditText recEditText;
    private EditText rirEditText;
    private EditText detailsEditText;
    private Button insertPhoto;
    private Button subirButton;
    private String imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_upload_train);
        String client = getIntent().getStringExtra("name");
        String uidClient = getIntent().getStringExtra("client_uid");
        String routine = getIntent().getStringExtra("routine");
        mAuth = FirebaseAuth.getInstance();
        String coach = mAuth.getCurrentUser().getUid();
        clientName = findViewById(R.id.nameClient);
        clientName.setText(client);
        storageReference = FirebaseStorage.getInstance().getReference();

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        trainName = findViewById(R.id.trainName);
        trainName.setText(routine);
        exerNameEditText = findViewById(R.id.exerNameEditText);
        kgEditText = findViewById(R.id.kgEditText);
        repEditText = findViewById(R.id.repEditText);
        recEditText = findViewById(R.id.recEditText);
        rirEditText = findViewById(R.id.rirEditText);
        detailsEditText = findViewById(R.id.detailsEditText);
        insertPhoto = findViewById(R.id.imagebutt);
        insertPhoto.setOnClickListener(view -> {
            // Lanza la galería de imágenes para seleccionar una imagen
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });
        subirButton = findViewById(R.id.subirButton);
        subirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameRoutine = trainName.getText().toString();
                String nameExer = exerNameEditText.getText().toString();
                Exercice exercice = new Exercice(nameExer,4, Integer.parseInt(kgEditText.getText().toString()), Integer.parseInt(recEditText.getText().toString()),Integer.parseInt(rirEditText.getText().toString()), detailsEditText.getText().toString(),imageName);

                    referenceExer = FirebaseDatabase.getInstance().getReference().child("Routines").child(coach).child(nameRoutine);

                    referenceExer.child(nameExer).setValue(exercice).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Ejercicio guardado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada
            imageUri = data.getData();


            imageName = "image_" + System.currentTimeMillis() + ".jpg";
            StorageReference imageRef = storageReference.child(imageName);


            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {

                // utilizando taskSnapshot.getDownloadUrl() y guardarla en la base de datos, etc.
            }).addOnFailureListener(e -> {


            });
        }
    }
}