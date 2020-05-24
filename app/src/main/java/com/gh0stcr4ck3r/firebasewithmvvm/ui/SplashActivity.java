package com.gh0stcr4ck3r.firebasewithmvvm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gh0stcr4ck3r.firebasewithmvvm.R;
import com.gh0stcr4ck3r.firebasewithmvvm.ui.SignUpActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();
    }
}
