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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);

        mAuth = FirebaseAuth.getInstance();

    }

    public void changeToRegisterActivity(View v){
        Intent change = new Intent(this, Register.class);
        startActivity(change);
    }

    public void changeToMainMenuActivity(View v) {
        String userEmail = email.getText().toString().trim();
        //Log.wtf("data", email.getText().toString().trim());
        String userPassword = password.getText().toString().trim();
        //Log.wtf("data", password.getText().toString().trim());

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(Login.this, "Email is required", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(Login.this, "Password is required", Toast.LENGTH_SHORT).show();
        }

        // Authenticate the user

        mAuth.signInWithEmailAndPassword(userEmail, userPassword).
                addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Log.wtf("error", task.getException() + "");
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Error !", Toast.LENGTH_SHORT).show();
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                    }
                    catch (FirebaseAuthEmailException e){
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                    }
                    catch (FirebaseAuthException e){
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(Login.this, "Logged in successfully",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Profile.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
