package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staynear.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView name, matricula, mail, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        name = findViewById(R.id.name);
        matricula = findViewById(R.id.matricula);
        mail = findViewById(R.id.mail);
        telefono = findViewById(R.id.numero);

        name.setText(user.getDisplayName());
        matricula.setText(user.getUid());
        mail.setText(user.getEmail());
        telefono.setText(user.getPhoneNumber());

    }

    public void changeToMainMenuActivity(View v){
        Intent change = new Intent(this, MainActivity.class);
        startActivityForResult(change,1);
    }
}
