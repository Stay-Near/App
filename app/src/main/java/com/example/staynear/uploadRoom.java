package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staynear.model.Room;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class uploadRoom extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private StorageReference firebaseStorage;
    private static final int GALLERY_SUCCESS = 1;
    private Uri fileUri;

    private EditText title,price,location,description;
    private Button btnUploadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room);
        title = findViewById(R.id.tTitle);
        price = findViewById(R.id.tPrice);
        location = findViewById(R.id.tLocation);
        description = findViewById(R.id.tDescription);
        btnUploadImage = findViewById(R.id.btnUploadImageForRoom);
        inicializarFirebase();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
    }

    public void selectImage(View v){
        Intent openGallery = new Intent(Intent.ACTION_PICK);
        openGallery.setType("image/*");
        startActivityForResult(openGallery,GALLERY_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_SUCCESS && resultCode == RESULT_OK){
            fileUri = data.getData();
        }else{
            Toast.makeText(this,"No se ha podido obtener la imagen", Toast.LENGTH_LONG).show();
        }
    }

    public void saveRoomInFirebase(View v){
        try{
            final StorageReference filePath = firebaseStorage.child("roomPhotos").child(fileUri.getLastPathSegment());
            filePath.putFile(fileUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Toast.makeText(uploadRoom.this,"Su cuarto ha sido registrado exitosamente", Toast.LENGTH_LONG).show();
                        Room newUser = new Room(UUID.randomUUID().toString(), title.getText().toString(), Float.parseFloat(price.getText().toString()), location.getText().toString(), description.getText().toString(),"d9c654a7-a0e9-4bb2-b3cc-a2e2c1bcfe13",downloadUri.toString());
                        databaseReference.child("room").child(newUser.getId()).setValue(newUser);
                        Toast.makeText(uploadRoom.this,"Su cuarto ha sido registrado correctamente", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(uploadRoom.this, "Uploading image failed, check you have selected an image", Toast.LENGTH_LONG).show();
                    }
                }
            });
            /*Room newUser = new Room(UUID.randomUUID().toString(), title.getText().toString(), Float.parseFloat(price.getText().toString()), location.getText().toString(), description.getText().toString(),"d9c654a7-a0e9-4bb2-b3cc-a2e2c1bcfe13",null);
                    databaseReference.child("room").child(newUser.getId()).setValue(newUser);
                    Toast.makeText(uploadRoom.this,"Su cuarto ha sido registrado correctamente", Toast.LENGTH_LONG).show();*/
        } catch(Exception e){
            Log.d("d", e.getMessage());
            Toast.makeText(this,"No se ha podido agregar el cuarto", Toast.LENGTH_LONG).show();
        }
    }
}
