package com.example.test2.ui.login;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test2.ApiCallMaker;
import com.example.test2.FriendListPage;
import com.example.test2.PayeeInfoActivity;
import com.example.test2.R;
import com.example.test2.ChooseFriendPage;
import com.example.test2.Utility;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final ApiCallMaker apicall = new ApiCallMaker();
    private final Map<String, String> headerMap = new HashMap<>();
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button transferBtn, friendBtn, chooseFriendBtn, requestMoneyBtn;
        TextView userName, userEmailAddress;
        EditText moneyAmt;
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Request Sent", Snackbar.LENGTH_SHORT);

        userName = findViewById(R.id.username);
        userName.setText(Utility.userName);
        userEmailAddress = findViewById(R.id.userEmailAddr);
        userEmailAddress.setText(Utility.userEmailAddr);


        transferBtn = findViewById(R.id.payMoneyBtn);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayeeInfo();
            }
        });

        friendBtn = findViewById(R.id.friendListBtn);
        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendListPage();
            }
        });

        chooseFriendBtn = findViewById(R.id.chooseFriendBtn);
        chooseFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChooseFriendPage();
            }
        });

        moneyAmt = (EditText) findViewById(R.id.moneyAmt);


        requestMoneyBtn = findViewById(R.id.requestMoneyBtn);
        requestMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moneyAmount = moneyAmt.getText().toString();
                try {
                    amount = Double.parseDouble(moneyAmount);
                    Log.w("amount", amount + "");
                    CustomTask task = new CustomTask();
                    String result = task.execute(Utility.token, Utility.friendEmail, amount + "").get();
                    Log.w("result", result);
                    mySnackbar.show();
                    moneyAmt.setText(null);
                } catch (Exception ignored) {

                }
            }
        });

    }

    public void openPayeeInfo() {
        Intent intent = new Intent(this, PayeeInfoActivity.class);
        startActivity(intent);
    }

    public void openFriendListPage() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

    public void openChooseFriendPage() {
        Intent intent = new Intent(this, ChooseFriendPage.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();
            boolean result = false;

            try {
                req.put("token", Utility.token);
                req.put("email", Utility.friendEmail);
                req.put("amount", amount);
                res = apicall.callPost("http://10.0.2.2:8080/transactions/requestMoneyINT", headerMap, req);
                result = res.getBoolean("isSuccess");
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return result + "";
        }

    }

}