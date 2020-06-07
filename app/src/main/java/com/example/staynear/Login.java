package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);

        fAuth = FirebaseAuth.getInstance();

    }

    public void changeToRegisterActivity(View v){
        Intent change = new Intent(this, Register.class);
        startActivity(change);
    }

    public  void tryLogin(View v){
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(Login.this, "Email is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(Login.this, "Password is required", Toast.LENGTH_SHORT).show();
        }
        else {
            fAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "Welcome to StayNear", Toast.LENGTH_LONG).show();
                        changeToMainMenuActivity();
                    }else{
                        Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
    public void changeToMainMenuActivity(View v) {
        Intent intent = new Intent(Login.this, list_room.class);
        startActivity(intent);
    }
    public void changeToMainMenuActivity() {
        Intent intent = new Intent(Login.this, list_room.class);
        startActivity(intent);
    }
}