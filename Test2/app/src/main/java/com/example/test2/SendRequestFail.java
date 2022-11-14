package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SendRequestFail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_req_fail);

        Button button = (Button) findViewById(R.id.backToAddFriendBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddFriend();
            }
        });
    }

    private void openAddFriend() {
        Intent intent = new Intent(this, SendFriendRequestPage.class);
        startActivity(intent);
    }
}