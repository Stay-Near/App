package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView name, name2, matricula, matricula2, mail, telefono;
    private DatabaseReference reff, mReff;
    private FirebaseAuth fAuth;
    private FirebaseUser currentUser;
    private RatingBar ratingBar;
    private Button update_data;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);;

        name = findViewById(R.id.name);
        matricula = findViewById(R.id.matricula);
        name2 = (EditText) findViewById(R.id.name2);
        matricula2 = (EditText) findViewById(R.id.matricula2);
        mail = (EditText) findViewById(R.id.correo);
        telefono = (EditText) findViewById(R.id.numero);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        user_id = currentUser.getUid();


        reff = FirebaseDatabase.getInstance().getReference().child("user").child(user_id);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                String correo = dataSnapshot.child("correo").getValue().toString();
                String num = dataSnapshot.child("telefono").getValue().toString();

                name.setText(nombre);
                name2.setText(nombre);
                mail.setText(correo);
                telefono.setText(num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        update_data = findViewById(R.id.updateData);
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReff = FirebaseDatabase.getInstance().getReference().child("user").child(user_id);

                String nombre = name2.getText().toString();
                String matricula = matricula2.getText().toString();
                String correo = mail.getText().toString();
                String num = telefono.getText().toString();

                mReff.child("user").child(user_id).child("nombre").setValue(nombre);
                //mReff.child("user").child(user_id).child("matricula").setValue(nombre);
                mReff.child("user").child(user_id).child("correo").setValue(correo);
                mReff.child("user").child(user_id).child("telefono").setValue(num);
                Toast.makeText(Profile.this, "Pulsaste boton", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void updateData() {
        
    }

    public void changeToMainMenuActivity(View v) {
        Intent change = new Intent(this, MainActivity.class);
        startActivity(change);
    }

}
