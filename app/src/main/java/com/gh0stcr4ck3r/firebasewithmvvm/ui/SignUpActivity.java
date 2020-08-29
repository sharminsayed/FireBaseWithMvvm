package com.gh0stcr4ck3r.firebasewithmvvm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gh0stcr4ck3r.firebasewithmvvm.R;
import com.gh0stcr4ck3r.firebasewithmvvm.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText userEmail,userPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser fUser;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userEmail=findViewById(R.id.user_email);
        userPassword=findViewById(R.id.user_password);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        fUser = mAuth.getCurrentUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private boolean registerValidUser() {
        if (userEmail.getText().toString().isEmpty()) {
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText()).matches()) {
            userEmail.setError("Please enter a valid email");
            userEmail.requestFocus();
            return false;
        }

        if (userPassword.getText().toString().isEmpty()) {
            userPassword.setError("Password is required");
            userPassword.requestFocus();
            return false;
        }

        if (userPassword.length() < 4) {
            userPassword.setError("Minimum lenght of password should be 4");
            userPassword.requestFocus();
            return false;
        }
        return true;
    }


    private void saveProfile(UserModel user, String uid) {
        databaseReference.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }
    public void signUpMethod(View view) {
        Bundle params = new Bundle();
       // params.putString("image_name", name);
        params.putString("text", String.valueOf(view.getId()));

       // register();
        if (registerValidUser()) {

            String email = userEmail.getText().toString().trim();
            String pass = userPassword.getText().toString().trim();

            UserModel user = new UserModel();

            user.setEmail(email);
            user.setPassword(pass);


            createUser(user);
        }
        mFirebaseAnalytics.logEvent("textname", params);


    }

    private void createUser(final UserModel user) {

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignUpActivity.this, "Sign up complete",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            saveProfile(user, firebaseUser.getUid());
                            Log.d("+++++",String.valueOf(firebaseUser.getUid()));
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                        } else {

                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void gotLoginActivity(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }
}
