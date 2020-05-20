package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DescriptionRomm extends AppCompatActivity {

    private TextView title, description, location, price, owner;
    private DatabaseReference reff;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_romm);

        title = findViewById(R.id.titulo);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        price = findViewById(R.id.precio);
        owner = findViewById(R.id.contacto);

        reff = FirebaseDatabase.getInstance().getReference().child("room").
                child(getIntent().getStringExtra("id"));
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String titulo = dataSnapshot.child("title").getValue().toString();
                String des = dataSnapshot.child("description").getValue().toString();
                String loc = dataSnapshot.child("location").getValue().toString();
                String pre = dataSnapshot.child("price").getValue().toString();
                String con = dataSnapshot.child("owner").getValue().toString();

                title.setText(titulo);
                description.setText(des);
                location.setText(loc);
                price.setText("$ " + pre);
                owner.setText(con);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
