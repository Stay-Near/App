package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staynear.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;

import java.util.UUID;

public class Register extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    private EditText nombre, telefono, correo, contra, repcontra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nombre = findViewById(R.id.edtNombre);
        telefono = findViewById(R.id.edtTelefono);
        correo = findViewById(R.id.edtCorreo);
        contra = findViewById(R.id.edtContra);
        repcontra = findViewById(R.id.edtRepContra);
        fAuth = FirebaseAuth.getInstance();
        inicializarFirebase();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    public void saveUserInFirebase(View v){
        try{
            String userName = nombre.getText().toString().trim();
            String userPhone = telefono.getText().toString().trim();
            String userEmail = correo.getText().toString().trim();
            String userPassword = contra.getText().toString().trim();
            String userRePassword = repcontra.getText().toString().trim();

            if (TextUtils.isEmpty(userEmail)) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(userPassword)) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(userName)) {
                Toast.makeText(this, "Name is requied", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(userPhone)) {
                Toast.makeText(this, "Phone is requied", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(userRePassword)) {
                Toast.makeText(this, "Password is requied", Toast.LENGTH_SHORT).show();
            }
            else if (!TextUtils.equals(userPassword,userRePassword)) {
                Toast.makeText(this, "Passwords must be the same", Toast.LENGTH_SHORT).show();
            }
            else{

                 fAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser currentUser = fAuth.getCurrentUser();
                            //Toast.makeText(Register.this, "User created successfully", Toast.LENGTH_LONG).show();
                            User newUser = new User(currentUser.getUid(), nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString(), contra.getText().toString());
                            databaseReference.child("user").child(newUser.getId()).setValue(newUser);
                            Toast.makeText(Register.this,"Su usuario ha sido registrado correctamente", Toast.LENGTH_LONG).show();
                            changeToLoginActivity();
                        } else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        } catch(Exception e){
            Toast.makeText(this,"No se ha podido agregar el usuario", Toast.LENGTH_LONG).show();
        }
    }

    public void changeToLoginActivity(View v){
        Intent change = new Intent(this, Login.class);
        startActivityForResult(change,1);
    }
    public void changeToLoginActivity(){
        Intent change = new Intent(this, Login.class);
        startActivityForResult(change,1);
    }

}
