package com.example.hecto.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    private EditText nombre,telefono,correo,contra,repcontra;
    private Button btnRegistro;
    private SharedPreferences prefs;
    private static final String PREFS_FILE="file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre = findViewById(R.id.edtNombre);
        telefono = findViewById(R.id.edtTelefono);
        correo = findViewById(R.id.edtCorreo);
        contra = findViewById(R.id.edtContra);
        repcontra = findViewById(R.id.edtRepContra);
    }

    private void load(){
        prefs=getSharedPreferences(PREFS_FILE,MODE_PRIVATE);
    }

    public void guardarEnPrefs(View v){
        SharedPreferences.Editor editor= prefs.edit();
        editor.putString("nombre",nombre.getText().toString());
        editor.putString("telefono",telefono.getText().toString());
        editor.putString("correo",correo.getText().toString());
        editor.putString("contra",contra.getText().toString());
        editor.commit();
    }

    public void changeActivityToLoginActivity(View v){
        //Intent change=new Intent(this,login.class);
        //startActivityForResult(change,1);
    }

}
