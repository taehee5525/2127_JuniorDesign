package com.example.test2.ui.login;

import com.example.test2.R;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.AsyncTask;

import com.example.test2.CreateAccActivity;
import com.example.test2.InstructionsPage;
import com.example.test2.LoginFail;
import com.example.test2.ApiCallMaker;
import com.example.test2.Utility;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private ImageButton imgButton;
    private Button signupBtn, loginBtn;
    private EditText username, password;
    private boolean usernameState, passwordState;

    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgButton = (ImageButton) findViewById(R.id.helpBtn);
        signupBtn = (Button) findViewById(R.id.btnSignUp);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        usernameState = false;
        passwordState = false;

        // Help Page
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHelpPage();
            }
        });

        // Sign Up Page
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignup();
            }
        });

        // watch inputs of Email and Password fields
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                    usernameState = true;
                }
                if (password.getText().toString().length() > 5) {
                    passwordState = true;
                }
            }
        };
        username.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);


        // Login Page
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to check if email and password are in correct format
                if (usernameState && passwordState) {
                    Log.w("login", "login started...");

                    try {
                        Utility.userEmailAddr = username.getText().toString();
                        String pwd = password.getText().toString();

                        Log.w("user email and password", Utility.userEmailAddr + ", " + pwd);

                        CustomTask task = new CustomTask();
                        String result = task.execute(Utility.userEmailAddr, pwd).get();

                        Log.w("login token", result);

                        if (result.length() > 2) {
                            CustomTask_getBalance task2 = new CustomTask_getBalance();
                            String balance = task2.execute().get();
                            Utility.userBalance = Double.parseDouble(balance);

                            Log.w("user balance", Utility.userBalance + "");

                            openMain();
                        } else {
                            openLoginFail();
                        }

                    } catch (Exception ignored) {

                    }


                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                        username.setError("Please enter a valid email address");
                    }

                    if (password.getText().toString().length() <= 5) {
                        password.setError("Please enter at least 6 characters");
                    }

                    // Error message set for email and password
                    TextWatcher afterTextChangedListener = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // ignore
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // ignore
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                                username.setError("Please enter a valid email address");
                            } else if (password.getText().toString().length() <= 5) {
                                password.setError("Please enter at least 6 characters");
                            } else {
                                usernameState = true;
                                passwordState = true;
                            }
                        }
                    };
                    username.addTextChangedListener(afterTextChangedListener);
                    password.addTextChangedListener(afterTextChangedListener);
                }
            }
        });
    }

    private void openHelpPage() {
        Intent intent = new Intent(this, InstructionsPage.class);
        startActivity(intent);
    }

    private void openSignup() {
        Intent intent = new Intent(this, CreateAccActivity.class);
        startActivity(intent);
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openLoginFail() {
        Intent intent = new Intent(this, LoginFail.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();

            try {
                req.put("email", username.getText().toString());
                req.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPost("http://10.0.2.2:8080/users/userlogin", headerMap, req);
                Utility.token = res.get("token").toString();
                Utility.userName = res.get("name").toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Utility.token;
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
                res = apicall.callGet("http://10.0.2.2:8080/transactions/getUserBalance", headerMap, paramMap);
                result = res.get("userBalance").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

}