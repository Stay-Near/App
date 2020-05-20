package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staynear.model.Appointment;
import com.example.staynear.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class Schedule extends AppCompatActivity {
    private TextView title;
    private CalendarView calendar;
    String roomID,date,currentUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        currentUser = ""+FirebaseAuth.getInstance().getCurrentUser().getUid();
        title = findViewById(R.id.tvTitle);
        calendar = findViewById(R.id.calendarView);
        Intent intent = getIntent();
        roomID = intent.getStringExtra("roomID");
        title.setText("Booking for " + intent.getStringExtra("roomTitle"));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + month + "/"+ year ;
            }
        });

        inicializarFirebase();
    }

    public void Book(View v){
        try{
            Appointment newAppointment = new Appointment(UUID.randomUUID().toString(), roomID, currentUser, date);
            databaseReference.child("appointment").child(newAppointment.getId()).setValue(newAppointment);
            Toast.makeText(this,"Booked room for: " + date, Toast.LENGTH_SHORT).show();
            finish();
        } catch(Exception e){
            Log.d("d", e.getMessage());
            Toast.makeText(this,"Error booking room", Toast.LENGTH_LONG).show();
        }
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        //currentUser = fAuth.getCurrentUser().getUid();
    }

}
