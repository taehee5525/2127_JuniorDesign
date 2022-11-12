package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.test2.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;

public class FriendList extends AppCompatActivity {
    private final ApiCallMaker apicall = new ApiCallMaker();
    private final Map<String, String> headerMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        CustomTask task = new CustomTask();
        try {
            String result = task.execute().get();
            Log.w("result", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();
            String friendList = new String();

            try {
                res = apicall.callGet("http://10.0.2.2:8080/friends/getFriendList", headerMap);
                friendList = res.get("requestList").toString();
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return friendList;

        }
    }
}