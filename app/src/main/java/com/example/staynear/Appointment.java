package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Appointment extends AppCompatActivity {

    private TextView title, user, description, date;
    private CalendarView calendar;
    String titulo, currentUser, fecha;

    DatabaseReference roomReference, appointReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        currentUser = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();

        user = findViewById(R.id.user);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        user.setText(currentUser);

        Intent intent = getIntent();

        roomReference = FirebaseDatabase.getInstance().getReference().child("romm").
                child(getIntent().getStringExtra("id"));

        roomReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                titulo = dataSnapshot.child("title").getValue().toString();
                String des = dataSnapshot.child("description").getValue().toString();

                title.setText(titulo);
                description.setText(des);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        appointReference = FirebaseDatabase.getInstance().getReference().child("appointment").
                child(getIntent().getStringExtra("idappointment"));

        appointReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fecha = dataSnapshot.child("date").getValue().toString();

                date.setText(fecha);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    public void openRating(View v){
        Intent historyIntent = new Intent(this, Rating.class);
        startActivity(historyIntent);
    }
}
