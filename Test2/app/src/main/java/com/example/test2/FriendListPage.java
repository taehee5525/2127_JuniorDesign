package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

        Button sendFriendReq, friendReqListBtn, backToMain;
        CustomTask task = new CustomTask();

        int totalFriends = 0;

        try {
            String friendList = task.execute().get();
            String[] eachEmail = friendList.split(",");

            if (friendList.contains("\"")) {
                for (int i = 0; i < eachEmail.length; i++) {
                    TextView emailAddr = new TextView(this);

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
                    emailAddr.setPadding(35, 0, 0, 30);
                    currLayout.addView(emailAddr);

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
                openSendReqPage();
            }
        });

        friendReqListBtn = findViewById(R.id.friendReqListBtn);
        friendReqListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendReqListPage();
            }
        });

        backToMain = findViewById(R.id.backToMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });
    }

    public void openSendReqPage() {
        Intent intent = new Intent(this, SendFriendRequestPage.class);
        startActivity(intent);
    }

    public void openFriendReqListPage() {
        Intent intent = new Intent(this, FriendRequestLists.class);
        startActivity(intent);
    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            String friendEmails = "";
            String friendNames = "";
            String friendList = "";

            try {
                req.put("token", Utility.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", Utility.token);

            try {
                res = apicall.callGet("http://10.0.2.2:8080/friends/getFriendList", headerMap, paramMap);
                friendEmails = res.get("friendEmails").toString();
                friendNames = res.get("friendNames").toString();
                friendList = res.get("friendList").toString();

                Log.w("friendEmails", friendEmails);
                Log.w("friendNames", friendNames);
                Log.w("friendList", friendList);
            }  catch (Exception e) {
                e.printStackTrace();
            }
            return friendList;
        }
    }

}