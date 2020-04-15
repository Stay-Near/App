package com.example.staynear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadImage extends AppCompatActivity {

    private Button uploadButton;
    private Button downloadButton;
    private  Uri fileUri;

    private StorageReference firebaseStorage;

    private static final int GALLERY_SUCCESS = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        firebaseStorage = FirebaseStorage.getInstance().getReference();

        uploadButton = findViewById(R.id.btnUploadImageForRoom);
        downloadButton = findViewById(R.id.btnDownloadImage);

    }

    public void uploadImage(View v){

        Intent openGallery = new Intent(Intent.ACTION_PICK);
        openGallery.setType("image/*");
        startActivityForResult(openGallery,GALLERY_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_SUCCESS && resultCode == RESULT_OK){
            fileUri = data.getData();
            StorageReference filePath = firebaseStorage.child("roomPhotos").child(fileUri.getLastPathSegment());
            filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadImage.this,"Imagen subida de manera exitosa", Toast.LENGTH_LONG).show();
                }
            });

        } else{
            Toast.makeText(this,"No se ha podido obtener la imagen", Toast.LENGTH_LONG).show();
        }
    }
}
