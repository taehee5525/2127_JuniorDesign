package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AcceptDeclineFriendReq extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_decline_friend_req);

        TextView text;
        Button acceptBtn, declineBtn, backToFriendReqListBtn;

        text = findViewById(R.id.acceptFriendText);
        text.setText("Accept " + Utility.friendReqEmail + "'s friend request?");

        backToFriendReqListBtn = findViewById(R.id.backToFriendReqListBtn);
        backToFriendReqListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendReqList();
            }
        });

        acceptBtn = findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    CustomTask2accept task2 = new CustomTask2accept();
                    try {
                        String friendRequestResult = task2.execute(Utility.token, Utility.friendReqEmail, "decline").get();
                        openFriendAccepted();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        declineBtn = findViewById(R.id.declineBtn);
        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    //api call while passing true as boolean parameter
                    CustomTask3decline task3 = new CustomTask3decline();
                    try {
                        String friendRequestResult = task3.execute(Utility.token, Utility.friendReqEmail, "decline").get();

                        openFriendDeclined();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openFriendReqList() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

    private void openFriendAccepted() {
        Intent intent = new Intent(this, FriendRequestAccepted.class);
        startActivity(intent);
    }

    private void openFriendDeclined() {
        Intent intent = new Intent(this, FriendRequestDeclined.class);
        startActivity(intent);
    }

    private void openTimeExpMsg() {
        Intent intent = new Intent(this, TimeExpireMsg.class);
        startActivity(intent);
    }

    class CustomTask2accept extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            try {
                req.put("token", Utility.token);
                req.put("email", Utility.friendReqEmail);
                req.put("accept", "accept");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/friends/requestAccept", headerMap, req);
                //System.out.println(res + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "accepted"; //acceptFriendReq() in Main.java is a void method and doesn't return anything
        }
    }

    class CustomTask3decline extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();
            try {
                req.put("token", Utility.token);
                req.put("email", Utility.friendReqEmail);
                req.put("accept", "decline");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/friends/requestAccept", headerMap, req);
                //System.out.println(res + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "declined"; //acceptFriendReq() in Main.java is a void method and doesn't return anything
        }
    }
}