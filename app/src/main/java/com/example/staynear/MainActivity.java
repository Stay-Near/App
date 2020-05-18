package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Hooks
        image = findViewById(R.id.imageView7);
        name = findViewById(R.id.logo);

        image.setAnimation(topAnim);
        name.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            };
        }, SPLASH_SCREEN);
    }

    public void changeToRegisterActivity(View v){
        Intent change = new Intent(this, Register.class);
        startActivityForResult(change,1);
    }

    public void changeToLoginActivity(View v){
        Intent change = new Intent(this, Login.class);
        startActivityForResult(change,1);
    }

    public void changeToProfileActivity(View v){
        Intent change = new Intent(this, Profile.class);
        startActivityForResult(change,1);
    }

    public void changeToImgActivity(View v){
        Intent change = new Intent(this, UploadImage.class);
        startActivityForResult(change,1);
    }

    public void changeToUploadRoomActivity(View v){
        Intent intent = new Intent(this, uploadRoom.class);
        startActivity(intent);
    }

    public void changeToDesRoomActivity(View v){
        Intent intent = new Intent(this, DescriptionRomm.class);
        startActivity(intent);
    }

    public void changeToRoomsListActivity(View v){
        Intent intent = new Intent(this,RoomsList.class);
        startActivity(intent);
    }
}
