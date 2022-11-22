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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AcceptDeclineTransaction extends AppCompatActivity {
    private final ApiCallMaker apicall = new ApiCallMaker();
    private final Map<String, String> headerMap = new HashMap<>();
    boolean confirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_decline_transaction);

        Button backToPendTransBtn, acceptBtn, declineBtn;

        backToPendTransBtn = findViewById(R.id.backToPendTransBtn);
        acceptBtn = findViewById(R.id.acceptBtn);
        declineBtn = findViewById(R.id.declineBtn);

        backToPendTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendingTran();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmed = true;
                CustomTask task = new CustomTask();
                try {
                    String acceptResult = task.execute(Utility.token, Utility.transactionID, confirmed + "").get();
                    Log.w("accept result", acceptResult);

                    CustomTask_getBalance task2 = new CustomTask_getBalance();
                    String balance = task2.execute().get();
                    Utility.userBalance = Double.parseDouble(balance);
                    Log.w("user balance", Utility.userBalance + "");

                    openTransactionSuc();

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
                    String declinedResult = task.execute(Utility.token, Utility.transactionID, confirmed + "").get();
                    Log.w("decline result", declinedResult);

                    CustomTask_getBalance task2 = new CustomTask_getBalance();
                    String balance = task2.execute().get();
                    Utility.userBalance = Double.parseDouble(balance);
                    Log.w("user balance", Utility.userBalance + "");

                    openTransactionDeclined();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openTransactionSuc() {
        Intent intent = new Intent(this, TransactionSuccess.class);
        startActivity(intent);
    }

    private void openTransactionDeclined() {
        Intent intent = new Intent(this, TransactionDeclined.class);
        startActivity(intent);
    }

    private void openPendingTran() {
        Intent intent = new Intent(this, PendingTransactions.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();

            try {
                req.put("token", Utility.token);
                req.put("transactionId", Utility.transactionID);
                req.put("confirmed", confirmed);

                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/transactions/confirmTransaction", headerMap, req);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "confirmed";
        }
    }

    class CustomTask_getBalance extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();
            String result = "";

            try {
                req.put("token", Utility.token);
            } catch (Exception ignored) {

            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", Utility.token);

            try {
                res = apicall.callGet("http://techpay.eastus.cloudapp.azure.com:8080/transactions/getUserBalance", headerMap, paramMap);
                result = res.get("userBalance").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}