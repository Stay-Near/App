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

    private String userName, mat, cor, tel;
    private TextView name, name2, matricula, mail, telefono;
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
        name2 = (EditText) findViewById(R.id.name2);
        matricula = (EditText) findViewById(R.id.matricula2);
        mail = (EditText) findViewById(R.id.correo);
        telefono = (EditText) findViewById(R.id.numero);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        user_id = currentUser.getUid();

        reff = FirebaseDatabase.getInstance().getReference().child("user").child(user_id);
        mReff = FirebaseDatabase.getInstance().getReference().child("user").child(user_id);

        showData();
    }

    public void showData() {
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

                userName = nombre;
                cor = correo;
                tel = num;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void updateData(View view) {
        if(isNameChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
            showData();
        }
        else if(isMailChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
            showData();
        }
        else if(isPhoneChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
            showData();
        }
        else {
            Toast.makeText(this, "Data is the same and can not be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPhoneChanged() {
        if(!tel.equals(telefono.getText().toString())) {
            mReff.child("telefono").setValue(telefono.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isMailChanged() {
        if(!cor.equals(mail.getText().toString())) {
            mReff.child("correo").setValue(mail.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!userName.equals(name2.getText().toString())) {
            mReff.child("nombre").setValue(name2.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    public void changeToMainMenuActivity(View v) {
        Intent change = new Intent(this, MainActivity.class);
        startActivity(change);
    }

}
