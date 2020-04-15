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

public class Profile extends AppCompatActivity {

    private TextView name, matricula, mail, telefono;
    private DatabaseReference reff;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);;

        name = findViewById(R.id.name);
        matricula = findViewById(R.id.matricula);
        mail = findViewById(R.id.mail);
        telefono = findViewById(R.id.numero);

        reff = FirebaseDatabase.getInstance().getReference().child("user").child("53c65fcd-01a6-4085-a00b-d97c74de3501");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                String correo = dataSnapshot.child("correo").getValue().toString();
                String num = dataSnapshot.child("telefono").getValue().toString();

                name.setText(nombre);
                matricula.setText("A00451398");
                mail.setText(correo);
                telefono.setText(num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void changeToMainMenuActivity(View v){
        Intent change = new Intent(this, MainActivity.class);
        startActivity(change);
    }
}
