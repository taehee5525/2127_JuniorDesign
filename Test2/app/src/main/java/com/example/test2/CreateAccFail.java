package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test2.ui.login.LoginActivity;

public class CreateAccFail extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc_fail);

        button = (Button) findViewById(R.id.backToCreateAccBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAcc();
            }
        });
    }

    public void openCreateAcc() {
        Intent intent = new Intent(this, CreateAccActivity.class);
        startActivity(intent);
    }
}