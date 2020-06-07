package com.example.staynear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staynear.model.Room;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class uploadRoom extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private String userID;

    private StorageReference firebaseStorage;
    private static final int GALLERY_SUCCESS = 1;
    private Uri fileUri;

    private FusedLocationProviderClient fusedLocationClient;
    private Location myLoc;

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
        userID = ""+ FirebaseAuth.getInstance().getCurrentUser().getUid();
        inicializarFirebase();
        inicializarLocation();
    }

    private void inicializarLocation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    myLoc = location;
                    Toast.makeText(uploadRoom.this,"Latitude : " + location.getLatitude() + "Longitude: " + location.getLongitude(),Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(uploadRoom.this,"No sacó la localización la hija de la verga",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadRoom.this,"Latitude : " + e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
            StorageReference filePath = firebaseStorage.child("roomPhotos").child(fileUri.getLastPathSegment());
            filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> taskUploaded = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    taskUploaded.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri linkUploadedUri) {
                            Room room = new Room(UUID.randomUUID().toString(),title.getText().toString(),Double.parseDouble(price.getText().toString()),location.getText().toString(),description.getText().toString(), userID,linkUploadedUri.toString(),myLoc.getLatitude(),myLoc.getLongitude());
                            databaseReference.child("room").child(room.getId()).setValue(room);
                            Toast.makeText(uploadRoom.this,"Su cuarto ha sido subido con éxito", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(uploadRoom.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(uploadRoom.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        } catch(Exception e){
            Log.d("d", e.getMessage());
            Toast.makeText(this,"No se ha podido agregar el cuarto", Toast.LENGTH_LONG).show();
        }
    }

    public void tempUpload(View v){
        String tempPhoto = "https://firebasestorage.googleapis.com/v0/b/staynear-88b6a.appspot.com/o/roomPhotos%2F1315115215?alt=media&token=518ee16e-053f-4546-85cf-21f9d96fd1dd";
        Room room = new Room(UUID.randomUUID().toString(),title.getText().toString(),Double.parseDouble(price.getText().toString()),location.getText().toString(),description.getText().toString(), userID,tempPhoto,0.5f,0.5f);
        databaseReference.child("room").child(room.getId()).setValue(room);
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }
}
