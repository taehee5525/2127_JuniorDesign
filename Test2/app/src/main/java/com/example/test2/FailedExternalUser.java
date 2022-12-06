package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FailedExternalUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_external_user);

        Button backToExternalUser = findViewById(R.id.backToExternalUser);
        backToExternalUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExternalUser();
            }
        });
    }

    private void openExternalUser() {
        Intent intent = new Intent(this, ExternalUser.class);
        startActivity(intent);
    }
}