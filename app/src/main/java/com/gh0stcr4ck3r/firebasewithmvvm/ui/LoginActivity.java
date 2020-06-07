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
import com.gh0stcr4ck3r.firebasewithmvvm.utils.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    FirebaseAuth mAuth;
    String uid;
    private FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.userLogin_email);
        loginPassword = findViewById(R.id.userLogin_password);
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
     //   uid = fUser.getUid();

    }

    private void loginMethod() {

        String lemail = loginEmail.getText().toString().trim();
        String lpassword = loginPassword.getText().toString().trim();
        if (lemail.isEmpty()) {
            loginEmail.setError("Email is required");
            loginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(lemail).matches()) {
            loginEmail.setError("Please enter a valid email");
            loginEmail.requestFocus();
            return;
        }

        if (lpassword.isEmpty()) {
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }

        if (lpassword.length() < 4) {
            loginPassword.setError("Minimum lenght of password should be 4");
            loginPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(lemail, lpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            saveId(uid);
                            Log.d("++++", String.valueOf(uid));


                            startActivity(new Intent(getApplicationContext(), NoteListActivity.class));

                        } else {

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            FirebaseUser currentUser = mAuth.getCurrentUser();
//            //startActivity(new Intent(this, LoginActivity.class));
//        }
//    }

    public void goToNoteActivity(View view) {
        loginMethod();
    }

    public void saveId(String id) {
        SharedPref sharedPref = new SharedPref(LoginActivity.this);
        sharedPref.saveuID(id);

    }

    public void forgetPw(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
    }
}
