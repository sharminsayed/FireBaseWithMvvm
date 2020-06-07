package com.gh0stcr4ck3r.firebasewithmvvm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gh0stcr4ck3r.firebasewithmvvm.R;
import com.gh0stcr4ck3r.firebasewithmvvm.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText forgetpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forgetpw=findViewById(R.id.forgetEmail);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
    private boolean registerValidUser() {
        if (forgetpw.getText().toString().isEmpty()) {
            forgetpw.setError("Email is required");
            forgetpw.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(forgetpw.getText()).matches()) {
            forgetpw.setError("Please enter a valid email");
            forgetpw.requestFocus();
            return false;

        }
        return true;
    }


    public void sendForgetRequest(View view) {
        if (registerValidUser()) {

            String email = forgetpw.getText().toString().trim();

            UserModel user = new UserModel();

            user.setEmail(email);

            // createUser(user);
            sendRequest(user);
        }


    }
    public void sendRequest(final UserModel user){

        mAuth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgetPasswordActivity.this, "Check your email",
                            Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ForgetPasswordActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
