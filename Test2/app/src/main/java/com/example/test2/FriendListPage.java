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

import com.example.test2.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendListPage extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        LinearLayout currLayout = findViewById(R.id.friendListLayout);
        LinearLayout remBtnLayout = findViewById(R.id.removeBtnLayout);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        drawable.setStroke(2, Color.RED);

        Button sendFriendReq, friendReqListBtn, backToMainBtn;
        CustomTask task = new CustomTask();

        int totalFriends = 0;

        try {
            String friendList = task.execute().get();
            String[] eachEmail = friendList.split(",");

            if (friendList.contains("\"")) {
                for (int i = 0; i < eachEmail.length; i++) {
                    TextView emailAddr = new TextView(this);
                    Button removeBtn = new Button(this);

                    eachEmail[i] = eachEmail[i].replace("}", "");
                    eachEmail[i] = eachEmail[i].replaceAll("\"", "");
                    eachEmail[i] = eachEmail[i].replace(":", " \nName: ");
                    eachEmail[i] = eachEmail[i].replace("{", "Email: ");

                    if (i == 0) {
                        eachEmail[i] = eachEmail[i].replace("[", "");
                    }

                    if (i == eachEmail.length - 1) {
                        eachEmail[i] = eachEmail[i].replace("]", "");
                    }

                    emailAddr.setText(eachEmail[i]);
                    emailAddr.setTextSize(18);
                    emailAddr.setTextColor(Color.BLACK);
                    emailAddr.setGravity(Gravity.CENTER);
                    emailAddr.setBackgroundColor(Color.parseColor("#f6f6f6"));
                    emailAddr.setPadding(35, 10, 0, 25);
                    currLayout.addView(emailAddr);

                    removeBtn.setText("remove");
                    removeBtn.setTextSize(18);
                    removeBtn.setGravity(Gravity.CENTER_HORIZONTAL);
                    removeBtn.setTextColor(Color.RED);
                    removeBtn.setBackground(drawable);
                    removeBtn.setPadding(0, 10, 0, 25);
                    remBtnLayout.addView(removeBtn);

                    int curr = i;

                    removeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CustomTask_remFri task2 = new CustomTask_remFri();
                            Utility.friendDelEmail = eachEmail[curr];
                            try {
                                String removeFriend = task2.execute().get();
                                openFriendList();
                            } catch (Exception ignored) {

                            }
                        }
                    });
                    totalFriends = i + 1;
                }
            }
            Utility.numOfFriends = totalFriends;

        } catch (Exception e) {
            e.printStackTrace();
        }

        sendFriendReq = findViewById(R.id.addFriendBtn);
        sendFriendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendReq();
            }
        });

        friendReqListBtn = findViewById(R.id.friendReqListBtn);
        friendReqListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendReqList();
            }
        });

        backToMainBtn = findViewById(R.id.backToMainBtn);
        backToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });
    }

    private void openSendReq() {
        Intent intent = new Intent(this, SendFriendRequestPage.class);
        startActivity(intent);
    }

    private void openFriendList() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

    private void openFriendReqList() {
        Intent intent = new Intent(this, FriendRequestLists.class);
        startActivity(intent);
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            String friendList = "";

            try {
                req.put("token", Utility.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", Utility.token);

            try {
                res = apicall.callGet("http://techpay.eastus.cloudapp.azure.com:8080/friends/getFriendList", headerMap, paramMap);
                friendList = res.get("friendList").toString();

                Log.w("friendList", friendList);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return friendList;
        }
    }

    class CustomTask_remFri extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();

            try {
                req.put("token", Utility.token);
                req.put("email", Utility.friendDelEmail);
                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/friends/removeFriend", headerMap, req);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "removed";
        }
    }
}