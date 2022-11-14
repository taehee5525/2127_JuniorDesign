package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendRequestLists extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        TextView tv = (TextView) findViewById(R.id.textView15);

        CustomTask task = new CustomTask();
        try {
            String result = task.execute().get();
            tv.setText(result);

            Log.w("result", result);
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

                Log.w("requestList", friendRequestList);
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return friendRequestList;
        }


    }
}