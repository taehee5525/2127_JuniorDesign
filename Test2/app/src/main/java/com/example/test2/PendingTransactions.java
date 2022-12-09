package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test2.ui.login.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class PendingTransactions extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_transactions);

        LinearLayout currLayout = findViewById(R.id.pendingTransactionLayout);

        Button backToMainBtn = findViewById(R.id.backToMainBtn);
        backToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });


        CustomTask task = new CustomTask();
        String res = new String();
        JSONArray arr = new JSONArray();
        try {
            res = task.execute().get();
            arr = new JSONArray(res);
        } catch (Exception e) {
            return;
            //do nothing
        }

        if (arr.length() == 0) {
            TextView noPending = new TextView(this);
            noPending.setText("You don't have any pending transactions");
            noPending.setTextColor(Color.RED);
            noPending.setGravity(Gravity.CENTER);
            currLayout.addView(noPending);
            return;
        }

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = new JSONObject();
            try {
                obj = arr.getJSONObject(i);

                String amount = obj.get("amount").toString();
                amount = "$" + amount;
                String payerEmail = obj.get("payerEmail").toString();
                String payeeEmail = obj.get("payeeEmail").toString();
                double amountD = Double.parseDouble(amount.substring(1,amount.length()));

                Utility.payerEmailAddr = payerEmail;
                Utility.amountReq = amountD;

                GradientDrawable drawable = new GradientDrawable();
                drawable.setStroke(2, Color.parseColor("#2998ff"));

                Button each_pending = new Button(this);
                each_pending.setText("Payer: " + payerEmail + "\n" + "Payee: " + payeeEmail + "\n" + "Amount: " + amount);
                each_pending.setTextSize(18);
                each_pending.setGravity(Gravity.CENTER);
                each_pending.setBackground(drawable);
                each_pending.setPadding(35, 15, 0, 30);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                params.setMargins(0, 0, 0, 3);
//                each_pending.setLayoutParams(params);
                currLayout.addView(each_pending);


                JSONObject finalObj = obj;
                each_pending.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String this_transaction = null;
                        try {
                            this_transaction = finalObj.get("transactionId").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Utility.transactionID = this_transaction;
                        Log.w("transId", Utility.transactionID);
                        openAccpetDTransaction();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openAccpetDTransaction() {
        Intent intent = new Intent(this, AcceptDeclineTransaction.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
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
                res = apicall.callGet("http://techpay.eastus.cloudapp.azure.com:8080/transactions/getPendingTransactionListThatUserNeedToConfirm", headerMap, paramMap);
                result = res.get("pendingTransaction").toString();
                Log.w("pending transactions", result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}