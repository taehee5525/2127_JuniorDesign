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
import com.example.test2.ExternalUser;
import com.example.test2.FriendListPage;
import com.example.test2.PendingTransactions;
import com.example.test2.R;
import com.example.test2.ChooseFriendPage;
import com.example.test2.TimeExpireMsg;
import com.example.test2.TransactionFail;
import com.example.test2.Utility;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private final ApiCallMaker apicall = new ApiCallMaker();
    private final Map<String, String> headerMap = new HashMap<>();
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button friendBtn, chooseFriendBtn, sendMoneyBtn, requestMoneyBtn, signOutBtn, checkPendingTransBtn, chooseExtUser;
        TextView userName, userEmailAddress, balanceAmount;
        EditText moneyAmt, noteText;

        userName = findViewById(R.id.username);
        userName.setText("Hi, " + Utility.userName);
        userEmailAddress = findViewById(R.id.userEmailAddr);
        userEmailAddress.setText(Utility.userEmailAddr);

        CustomTask_getBalance tk = new CustomTask_getBalance();
        try {
            String str = tk.execute().get();
            Utility.userBalance = Double.parseDouble(str);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        balanceAmount = findViewById(R.id.balanceAmount);
        balanceAmount.setText("$" + String.format("%.2f", Utility.userBalance));

        friendBtn = findViewById(R.id.friendListBtn);
        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendList();
            }
        });

        checkPendingTransBtn = findViewById(R.id.checkPendingTransBtn);
        checkPendingTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    openPendingTransactions();
                }
            }
        });

        chooseExtUser = findViewById(R.id.chooseExtUser);
        chooseExtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    openExternalUser();
                }
            }
        });

        chooseFriendBtn = findViewById(R.id.chooseFriendBtn);
        chooseFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    openChooseFriend();
                }
            }
        });

        moneyAmt = (EditText) findViewById(R.id.moneyAmt);
        noteText = findViewById(R.id.noteText);

        sendMoneyBtn = findViewById(R.id.sendMoneyBtn);
        sendMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTask_getBalance tk = new CustomTask_getBalance();
                try {
                    String str = tk.execute().get();
                    Utility.userBalance = Double.parseDouble(str);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balanceAmount.setText("$" + String.format("%.2f", Utility.userBalance));

                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    String moneyAmount = moneyAmt.getText().toString();

                    if (moneyAmount.isEmpty()) {
                        moneyAmt.setError("Please enter amount");
                    } else if (Double.parseDouble(moneyAmount) > Utility.userBalance) {
                        moneyAmt.setError("Not enough balance. Please check your balance");
                    } else {
                        try {
                            amount = Double.parseDouble(moneyAmount);
                            CustomTask2_sendMoney task2 = new CustomTask2_sendMoney();
                            String result = task2.execute(Utility.token, Utility.friendEmail, amount + "").get();
                            Log.w("Valid transaction check", result);

                            if (result.contains("true")) {
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Send money request sent", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                                moneyAmt.setText(null);
                                noteText.setText(null);
                            } else {
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Failed to send money. Please choose a friend", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }


            }
        });

        requestMoneyBtn = findViewById(R.id.requestMoneyBtn);
        requestMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomTask_getBalance tk = new CustomTask_getBalance();
                try {
                    String str = tk.execute().get();
                    Utility.userBalance = Double.parseDouble(str);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balanceAmount.setText("$" + String.format("%.2f", Utility.userBalance));

                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
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
                                noteText.setText(null);
                            } else {
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinateLayout), "Failed to request money. Please choose a friend", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        });

        signOutBtn = findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }

    private void openFriendList() {
        Intent intent = new Intent(this, FriendListPage.class);
        startActivity(intent);
    }
    private void openExternalUser() {
        Intent intent = new Intent(this, ExternalUser.class);
        startActivity(intent);
    }

    private void openPendingTransactions() {
        Intent intent = new Intent(this, PendingTransactions.class);
        startActivity(intent);
    }

    private void openChooseFriend() {
        Intent intent = new Intent(this, ChooseFriendPage.class);
        startActivity(intent);
    }

    private void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openTransactionFail() {
        Intent intent = new Intent(this, TransactionFail.class);
        startActivity(intent);
    }

    private void openTimeExpMsg() {
        Intent intent = new Intent(this, TimeExpireMsg.class);
        startActivity(intent);
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

                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/transactions/requestMoneyINT", headerMap, req);
                result = res.getBoolean("isSuccess");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result + "";
        }
    }

    class CustomTask2_sendMoney extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();
            boolean str = false;

            try {
                req.put("token", Utility.token);
                req.put("email", Utility.friendEmail);
                req.put("amount", amount);

                res = apicall.callPost("http://techpay.eastus.cloudapp.azure.com:8080/transactions/sendMoneyINT", headerMap, req);
                str = res.getBoolean("isSuccess");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return str + "";
        }

    }

}