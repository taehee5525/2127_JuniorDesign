package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

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

        CustomTask task = new CustomTask();
        try {
            String friendRequestStr = task.execute().get();
            String[] eachEmail = friendRequestStr.split(",");

            for (int i = 0; i < eachEmail.length; i++) {
                TextView emailAddr = new TextView(this);

                if (i == 0) {
                    eachEmail[i] = eachEmail[i].replace("[", "");
                }

                if (i == eachEmail.length - 1) {
                    eachEmail[i] = eachEmail[i].replace("]", "");
                }

                eachEmail[i] = eachEmail[i].replaceAll("^\"|\"$", "");

                friendRequestsList.add(eachEmail[i]);

                emailAddr.setText(eachEmail[i]);
                emailAddr.setTextSize(18);
                emailAddr.setPadding(35, 10, 0, 20);
                currLayout.addView(emailAddr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Button approveFriendBtn, declineBtn;

        approveFriendBtn = findViewById(R.id.approveFriendBtn);
        approveFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //api call while passing true as boolean parameter
                CustomTask2accept task2 = new CustomTask2accept();

                try {
                    String friendRequestResult = task2.execute(Utility.token, friendRequestsList.get(0), "decline").get();
                    //need to also add refreshing the page here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        declineBtn = findViewById(R.id.declineBtn);
        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //api call while passing true as boolean parameter
                CustomTask3decline task3 = new CustomTask3decline();
                try {
                    String friendRequestResult = task3.execute(Utility.token, friendRequestsList.get(0), "decline").get();
                    //need to also add refreshing the page here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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

    class CustomTask2accept extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            try {
                req.put("token", Utility.token);
                req.put("email", friendRequestsList.get(0));
                req.put("accept", "accept");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPut("http://techpay.eastus.cloudapp.azure.com:8080/friends/requestAccept", headerMap, req);
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
                req.put("email", friendRequestsList.get(0));
                req.put("accept", "decline");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPut("http://techpay.eastus.cloudapp.azure.com:8080/friends/requestAccept", headerMap, req);
                friendRequestsList.remove(0);
                //System.out.println(res + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "declined"; //acceptFriendReq() in Main.java is a void method and doesn't return anything
        }
    }

}