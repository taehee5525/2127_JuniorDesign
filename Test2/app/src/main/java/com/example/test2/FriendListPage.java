package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test2.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FriendListPage extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Button sendFriendReq;
        TextView friendList;

        friendList = (TextView) findViewById(R.id.textView12);

        CustomTask task = new CustomTask();
        try {
            String result = task.execute().get();
            friendList.setText(result);

            Log.w("result", result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        sendFriendReq = (Button) findViewById(R.id.addFriendBtn);
        sendFriendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendReqPage();
            }
        });

    }

    public void openSendReqPage() {
        Intent intent = new Intent(this, SendFriendRequestPage.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();
            List<String> friendList;

            String friend = new String();

            try {
                req.put("token", Utility.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", Utility.token);

            try {
                res = apicall.callGet("http://10.0.2.2:8080/friends/getFriendList", headerMap, paramMap);
                friend = res.get("friendList").toString();

                Log.w("friend", friend);
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return friend;
        }


    }

}