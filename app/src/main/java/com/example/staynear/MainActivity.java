package com.example.staynear;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private final int DURATION_SPLASH = 2000;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            };
        }, DURATION_SPLASH);
    }
    public void changeToRegisterActivity(View v){
        Intent change=new Intent(this,Register.class);
        startActivityForResult(change,1);
    }

    public void changeToUploadRoomActivity(View v){
        Intent intent = new Intent(this,uploadRoom.class);
        startActivity(intent);
    }

}
