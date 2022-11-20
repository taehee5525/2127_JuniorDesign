package com.example.test2.ui.login;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.test2.TransactionFail;
import com.example.test2.Utility;
import com.google.android.material.snackbar.Snackbar;

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

        Button friendBtn, chooseFriendBtn, sendMoneyBtn, requestMoneyBtn, signOutBtn;
        TextView userName, userEmailAddress;
        EditText moneyAmt;

        userName = findViewById(R.id.username);
        userName.setText("Hi, " + Utility.userName);
        userEmailAddress = findViewById(R.id.userEmailAddr);
        userEmailAddress.setText(Utility.userEmailAddr);


        friendBtn = (Button) findViewById(R.id.friendListBtn);
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

        sendMoneyBtn = findViewById(R.id.sendMoneyBtn);
        sendMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = Utility.token;
                String email = Utility.friendEmail;
                String amt = moneyAmt.getText().toString();
                CustomTask2_sendMoney task = new CustomTask2_sendMoney();
                String result = null;
                if (amt.isEmpty()) {
                    moneyAmt.setError("Please enter amount");
                } else {
                    try {
                        amount = Double.parseDouble(amt);
                        result = task.execute(token, email, amount + "").get();
                        Log.w("Valid transaction check", result);
                    } catch (Exception ignored) {

                    }

                    if (result.contains("true")) {
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Send money request Sent", Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                        moneyAmt.setText(null);
                    } else {
                        openTransactionFail();
                    }
                }
            }
        });


        requestMoneyBtn = findViewById(R.id.requestMoneyBtn);
        requestMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moneyAmount = moneyAmt.getText().toString();

                if (moneyAmount.isEmpty()) {
                    moneyAmt.setError("Please enter amount");
                } else {
                    try {
                        amount = Double.parseDouble(moneyAmount);
                        Log.w("amount", amount + "");
                        CustomTask_requestMoney task = new CustomTask_requestMoney();
                        String result = task.execute(Utility.token, Utility.friendEmail, amount + "").get();
                        Log.w("result", result);

                        if (result.contains("true")) {
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Request money sent", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                            moneyAmt.setText(null);
                        } else {
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Failed to request money. Please choose a friend", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        });

        signOutBtn = findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });

    }

    public void openFriendListPage() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }

    public void openChooseFriendPage() {
        Intent intent = new Intent(this, ChooseFriendPage.class);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openTransactionFail() {
        Intent intent = new Intent(this, TransactionFail.class);
        startActivity(intent);
    }

    class CustomTask_requestMoney extends AsyncTask<String, Void, String> {
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

    class CustomTask2_sendMoney extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res;
            boolean str = false;

            try {
                req.put("token", Utility.token);
                req.put("email", Utility.friendEmail);
                req.put("amt", amount);

                res = apicall.callPost("http://10.0.2.2:8080/transactions/requestMoneyINT", headerMap, req);
                str = res.getBoolean("isSuccess");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return str + "";
        }

    }

}