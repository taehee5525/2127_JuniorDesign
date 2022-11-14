package com.example.test2.ui.login;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test2.FriendListPage;
import com.example.test2.PayeeInfoActivity;
import com.example.test2.R;
import com.example.test2.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button transferBtn, friendBtn;
        TextView userName, userEmailAddress;

        userName = (TextView) findViewById(R.id.username);
        userName.setText(Utility.userName);
        userEmailAddress = (TextView) findViewById(R.id.userEmailAddr);
        userEmailAddress.setText(Utility.userEmailAddr);


        transferBtn = (Button) findViewById(R.id.payMoneyBtn);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayeeInfo();
            }
        });

        friendBtn = (Button) findViewById(R.id.friendListBtn);
        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendListPage();
            }
        });
    }

    public void openPayeeInfo() {
        Intent intent = new Intent(this, PayeeInfoActivity.class);
        startActivity(intent);
    }

    public void openFriendListPage() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

}