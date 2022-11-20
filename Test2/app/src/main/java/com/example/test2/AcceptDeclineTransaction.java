package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AcceptDeclineTransaction extends AppCompatActivity {
    private final ApiCallMaker apicall = new ApiCallMaker();
    private final Map<String, String> headerMap = new HashMap<>();
    boolean confirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_decline_transaction);

        Button acceptBtn, declineBtn;

        acceptBtn = findViewById(R.id.acceptBtn);
        declineBtn = findViewById(R.id.declineBtn);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmed = true;
                CustomTask task = new CustomTask();
                try {
                    String acceptResult = task.execute(Utility.token, Utility.transactionID, "Y").get();
                    Log.w("accept result", acceptResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmed = false;
                CustomTask task = new CustomTask();
                try {
                    String declinedResult = task.execute(Utility.token, Utility.transactionID, "n").get();
                    Log.w("decline result", declinedResult);
                } catch (Exception e) {
                    e.printStackTrace();
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
                req.put("token", Utility.token);
                req.put("transactionId", Utility.transactionID);
                req.put("confirmed", confirmed);

                res = apicall.callPut("http://10.0.2.2:8080/transactions/confirmTransaction", headerMap, req);
                str = res.getBoolean("confirmed");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str + "";
        }
    }
}