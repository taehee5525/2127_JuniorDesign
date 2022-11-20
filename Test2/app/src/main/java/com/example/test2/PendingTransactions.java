package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test2.ui.login.MainActivity;

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

        Button backToMain = findViewById(R.id.backToMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });

        LinearLayout currLayout = findViewById(R.id.pendingTransactionLayout);

        CustomTask task = new CustomTask();

        try {
            String pendingList = task.execute().get();
            String[] pending = pendingList.split("\\},");
            String email = "";

            for (int i = 0; i < pending.length; i++) {
                Button each_pending = new Button(this);

                String[] pending_comma = pending[i].split(",");
                String amt = pending_comma[1];
                double amount = Double.parseDouble(amt.replaceAll("[^0-9]", ""));

                if (i == 0) {
                    pending[i] = pending[i].replace("[", "");
                }

                if (i == pending.length - 1) {
                    pending[i] = pending[i].replace("]", "");
                }

                Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(pending[i]);
                while (matcher.find()) {
                    int matchStart = matcher.start(0);
                    int matchEnd = matcher.end(0);
                    email = pending[i].substring(matchStart, matchEnd);
                }

                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(20);
                drawable.setStroke(2, Color.DKGRAY);

                each_pending.setText("Email:" + email + "\n" + "Amount: " + amount );
                each_pending.setTextSize(18);
                each_pending.setTextColor(Color.BLACK);
                each_pending.setBackground(drawable);
                each_pending.setPadding(35, 10, 0, 30);

                currLayout.addView(each_pending);

                each_pending.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String this_transaction = pending_comma[7];
                        this_transaction = this_transaction.replace("\"", "");
                        this_transaction = this_transaction.replace(":", "");
                        this_transaction = this_transaction.replace("transactionId", "");
                        Utility.transactionID = this_transaction;
                        Log.w("transId", Utility.transactionID);
                        openAccpetDTransaction();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openMainPage() {
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
                res = apicall.callGet("http://10.0.2.2:8080/transactions/getPendingTransactionListThatUserNeedToConfirm", headerMap, paramMap);
                result = res.get("pendingTransaction").toString();
                Log.w("result", result);
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}