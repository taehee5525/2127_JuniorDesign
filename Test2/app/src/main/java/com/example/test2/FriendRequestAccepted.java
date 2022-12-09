package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test2.ui.login.MainActivity;

public class FriendRequestAccepted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request_accepted);

        Button gotoFriendsListPage = findViewById(R.id.gotoFriendsListPage);
        gotoFriendsListPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendList();
            }
        });
    }

    private void openFriendList() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }
}