package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staynear.model.Room;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class uploadRoom extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private EditText title,price,location,description, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room);
        title = findViewById(R.id.tTitle);
        price = findViewById(R.id.tPrice);
        location = findViewById(R.id.tLocation);
        description = findViewById(R.id.tDescription);
        image = findViewById(R.id.tImage);
        inicializarFirebase();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void saveRoomInFirebase(View v){
        try{
            Room newUser = new Room(UUID.randomUUID().toString(), title.getText().toString(), Integer.parseInt(price.getText().toString()), location.getText().toString(), description.getText().toString(),"d9c654a7-a0e9-4bb2-b3cc-a2e2c1bcfe13",image.getText().toString());
            databaseReference.child("room").child(newUser.getId()).setValue(newUser);
            Toast.makeText(this,"Su cuarto ha sido registrado correctamente", Toast.LENGTH_LONG).show();
        } catch(Exception e){
            Log.d("d", e.getMessage());
            Toast.makeText(this,"No se ha podido agregar el cuarto", Toast.LENGTH_LONG).show();
        }
    }
}
