package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test2.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendFriendRequestPage extends AppCompatActivity {
    private EditText email;

    private Map<String, String> headerMap = new HashMap<>();
    private ApiCallMaker apicall = new ApiCallMaker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend_request_page);

        email = (EditText) findViewById(R.id.username);

        Button sendFriendReqBtn, backToFriendsListBtn;

        backToFriendsListBtn = (Button) findViewById(R.id.backToFriendsListBtn);
        backToFriendsListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendsList();
            }
        });


        sendFriendReqBtn = (Button) findViewById(R.id.sendFriendReqBtn);
        sendFriendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    try {
                        CustomTask task = new CustomTask();
                        String result = task.execute(email.getText().toString()).get();
                        Log.w("add friend", result);

                        if (result.contains("t")) {
                            openSendReqSuc();
                        } else {
                            openSendReqFail();
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        });
    }

    private void openFriendsList() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

    private void openSendReqSuc() {
        Intent intent = new Intent(this, SendRequestSucc.class);
        startActivity(intent);
    }

    private void openSendReqFail() {
        Intent intent = new Intent(this, SendRequestFail.class);
        startActivity(intent);
    }

    private void openTimeExpMsg() {
        Intent intent = new Intent(this, TimeExpireMsg.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();
            boolean str = false;

            try {
                req.put("email", email.getText().toString());
                req.put("token", Utility.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/friends/request", headerMap, req);
                str = res.getBoolean("isSuccess");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str + "";
        }
    }
}