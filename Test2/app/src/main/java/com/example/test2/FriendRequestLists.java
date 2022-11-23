package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FriendRequestLists extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();
    public List<String> friendRequestsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        LinearLayout currLayout = findViewById(R.id.requestListLayout);

        Button backToFriendBtn, approveFriendBtn, declineBtn;

        backToFriendBtn = findViewById(R.id.backToFriendBtn);
        backToFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendList();
            }
        });

        CustomTask task = new CustomTask();
        try {
            String friendRequestStr = task.execute().get();
            String[] eachEmail = friendRequestStr.split(",");

            if (!friendRequestStr.contains("\"")) {
                TextView noFriendReq = new TextView(this);
                noFriendReq.setText("You don't have any friend requests");
                noFriendReq.setTextColor(Color.RED);
                noFriendReq.setGravity(Gravity.CENTER);
                currLayout.addView(noFriendReq);
            } else {
                for (int i = 0; i < eachEmail.length; i++) {
                    Button emailAddr = new Button(this);

                    if (i == 0) {
                        eachEmail[i] = eachEmail[i].replace("[", "");
                    }

                    if (i == eachEmail.length - 1) {
                        eachEmail[i] = eachEmail[i].replace("]", "");
                    }

                    eachEmail[i] = eachEmail[i].replaceAll("^\"|\"$", "");

                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    drawable.setCornerRadius(20);
                    drawable.setStroke(2, Color.DKGRAY);

                    emailAddr.setText(eachEmail[i]);
                    emailAddr.setTextSize(18);
                    emailAddr.setAllCaps(false);
                    emailAddr.setGravity(Gravity.CENTER);
                    emailAddr.setBackground(drawable);
                    emailAddr.setPadding(35, 10, 0, 20);
                    currLayout.addView(emailAddr);

                    int currReq = i;
                    emailAddr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Utility.friendReqEmail = eachEmail[currReq];
                            Log.w("selected request email", Utility.friendReqEmail);
                            openAccpetDFriendReq();
                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFriendList() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

    private void openAccpetDFriendReq() {
        Intent intent = new Intent(this, AcceptDeclineFriendReq.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            String friendRequestList = "";

            try {
                req.put("token", Utility.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", Utility.token);

            try {
                res = apicall.callGet("http://techpay.eastus.cloudapp.azure.com:8080/friends/getRequestList", headerMap, paramMap);
                friendRequestList = res.get("requestList").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return friendRequestList;
        }
    }
}