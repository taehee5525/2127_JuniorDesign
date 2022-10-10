package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import com.example.test2.ui.login.LoginActivity;

import org.json.JSONException;

public class CreateAccActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        //This is the sign in button at the bottom of the page
        button = (Button) findViewById(R.id.btnSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

        button = (Button) findViewById(R.id.createAccount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    HiServer.reqSignUp("asd", "asd");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Backend- needs to use the database and connect it to the sever
                // But first, let the user always see the success page first
                // That way, we can go to the other pages.
                if (true) {
                    openCreateAccSuccess();
                } else {
                    openCreateAccFail();
                }
            }
        });
    }
    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openCreateAccSuccess() {
        Intent intent = new Intent(this, CreateAccSuccess.class);
        startActivity(intent);
    }

    public void openCreateAccFail() {
        Intent intent = new Intent(this, CreateAccFail.class);
        startActivity(intent);
    }
}