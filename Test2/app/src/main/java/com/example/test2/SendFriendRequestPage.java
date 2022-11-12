package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendFriendRequestPage extends AppCompatActivity {
    private Button sendReq;
    private EditText email;

    private Map<String, String> headerMap = new HashMap<>();
    private ApiCallMaker apicall = new ApiCallMaker();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend_request_page);

        email = (EditText) findViewById(R.id.username);


        sendReq = (Button) findViewById(R.id.addFriendBtn);
        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CustomTask task = new CustomTask();
                    String result = task.execute(email.getText().toString()).get();
                    Log.w("add friend", result);
                } catch (Exception ignored) {

                }

            }
        });
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
                res = apicall.callPost("http://10.0.2.2:8080/friends/request", headerMap, req);
                str = res.getBoolean("isSuccess");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str + "";
        }


    }
}