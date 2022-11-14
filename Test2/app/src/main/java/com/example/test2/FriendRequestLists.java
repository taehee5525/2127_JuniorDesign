package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendRequestLists extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        LinearLayout currLayout = findViewById(R.id.requestListLayout);

        CustomTask task = new CustomTask();
        try {
            String friendRequestList = task.execute().get();
            String[] eachEmail = friendRequestList.split(",");

            for (int i = 0; i < eachEmail.length; i++) {
                TextView emailAddr = new TextView(this);

                if (i == 0) {
                    eachEmail[i] = eachEmail[i].replace("[", "");
                }

                if (i == eachEmail.length - 1) {
                    eachEmail[i] = eachEmail[i].replace("]", "");
                }

                eachEmail[i] = eachEmail[i].replaceAll("^\"|\"$", "");
                emailAddr.setText(eachEmail[i]);
                emailAddr.setTextSize(18);
                emailAddr.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                currLayout.addView(emailAddr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                res = apicall.callGet("http://10.0.2.2:8080/friends/getRequestList", headerMap, paramMap);
                friendRequestList = res.get("requestList").toString();
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return friendRequestList;
        }
    }
}