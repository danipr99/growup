package com.example.growup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Client_calendar extends AppCompatActivity {
    private Button start;
    private String coach;
    private String clientUid;
    private ImageButton backButton;
    private int selectedDayOfMonth;
    private int selectedDayOfWeek;
    private int selectedMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_calendar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        clientUid = mAuth.getCurrentUser().getUid();
        coach = getIntent().getStringExtra("coach_uid");

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        CalendarView calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDayOfMonth = dayOfMonth;
                selectedDayOfWeek = getDayOfWeek(year, month, dayOfMonth);
                selectedMonth = month;
            }
        });

        start = findViewById(R.id.goToRoutine);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_calendar.this, Client_Training.class);
                intent.putExtra("dayOfMonth", selectedDayOfMonth);
                intent.putExtra("dayOfWeek", selectedDayOfWeek);
                intent.putExtra("month", selectedMonth);
                intent.putExtra("coach_uid", coach);
                startActivity(intent);
            }
        });
    }

    private int getDayOfWeek(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}