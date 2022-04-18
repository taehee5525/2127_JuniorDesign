package com.example.moneytransfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
    }

    public void userClickSignIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginPageActivity.class);
        startActivity(intent);
        finish();
    }
}