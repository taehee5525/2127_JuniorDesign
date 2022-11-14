package com.example.test2.ui.login;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test2.PayeeInfoActivity;
import com.example.test2.R;
import com.example.test2.SendFriendRequestPage;

public class MainActivity extends AppCompatActivity {
    private Button transferBtn, sendFriendReq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transferBtn = (Button) findViewById(R.id.second_activity_transaction_button);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayeeInfo();
            }
        });

        sendFriendReq = (Button) findViewById(R.id.sendFriendReqBtn);
        sendFriendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendFriendReqPage();
            }
        });
    }

    private void openPayeeInfo() {
        Intent intent = new Intent(this, PayeeInfoActivity.class);
        startActivity(intent);
    }

    private void openSendFriendReqPage() {
        Intent intent = new Intent(this, SendFriendRequestPage.class);
        startActivity(intent);
    }

}