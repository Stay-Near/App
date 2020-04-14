package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staynear.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Register extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private EditText nombre, telefono, correo, contra, repcontra;
    private Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nombre = findViewById(R.id.edtNombre);
        telefono = findViewById(R.id.edtTelefono);
        correo = findViewById(R.id.edtCorreo);
        contra = findViewById(R.id.edtContra);
        repcontra = findViewById(R.id.edtRepContra);
        inicializarFirebase();
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    public void saveUserInFirebase(View v){
        try{
            User newUser = new User(UUID.randomUUID().toString(), nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString(), contra.getText().toString());
            databaseReference.child("user").child(newUser.getId()).setValue(newUser);
            Toast.makeText(this,"Su usuario ha sido registrado correctamente", Toast.LENGTH_LONG).show();
        } catch(Exception e){
            Log.d("d", e.getMessage());
            Toast.makeText(this,"No se ha podido agregar el usuario", Toast.LENGTH_LONG).show();
        }
    }

}
