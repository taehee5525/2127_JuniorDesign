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

public class CreateAccActivity extends AppCompatActivity {

    private EditText personName, username, password, phoneNumber;
    private CheckBox checkBox;
    private final ApiCallMaker apicall = new ApiCallMaker();
    private final Map<String, String> headerMap = new HashMap<>();
    private boolean nameState, usernameState, passwordState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        Button loginBtn = (Button) findViewById(R.id.btnSignIn);
        Button signupBtn = (Button) findViewById(R.id.createAccount);

        personName = (EditText) findViewById(R.id.personName);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        // openLogin() page
        loginBtn.setOnClickListener(view -> openLogin());

        /* watch inputs of Name, Email and Password fields */
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (personName.getText().toString().length() > 2) {
                    nameState = true;
                }

                if (Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                    usernameState = true;
                }

                if (password.getText().toString().length() > 5) {
                    passwordState = true;
                }
            }
        };

        personName.addTextChangedListener(afterTextChangedListener);
        username.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);

        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


        /* Call API & Show error message for incorrect inputs */
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to check if email and password are in correct format
                if (nameState & usernameState & passwordState & checkBox.isChecked()) {
                    try {
                        String name = personName.getText().toString();
                        String id = username.getText().toString();
                        String pwd = password.getText().toString();
                        String phoneN = phoneNumber.getText().toString();

                        Log.w("name id pwd phoneNumber", name + ", " + id + ", " + pwd + ", " + phoneN);

                        CustomTask task = new CustomTask();
                        String result = task.execute(name, id, pwd, phoneN).get();

                        Log.w("Valid email check", result);

                        if (result.contains("true")) {
                            openCreateAccSuccess();
                        } else {
                            openCreateAccFail();
                        }

                    } catch (Exception ignored) {
                    }
                } else {
                    if (personName.getText().toString().length() < 3) {
                        personName.setError("Full name is required");
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                        username.setError("Please enter a valid email address");
                    }

                    if (password.getText().toString().length() < 5) {
                        password.setError("Please enter at least 6 characters");
                    }

                    TextWatcher afterTextChangedListener = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (personName.getText().toString().length() < 3) {
                                personName.setError("Full name is required");
                            } else if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                                username.setError("Please enter a valid email address");
                            } else if (password.getText().toString().length() < 5) {
                                password.setError("Please enter at least 6 characters");
                            } else {
                                nameState = true;
                                usernameState = true;
                                passwordState = true;
                            }
                        }
                    };
                    personName.addTextChangedListener(afterTextChangedListener);
                    username.addTextChangedListener(afterTextChangedListener);
                    password.addTextChangedListener(afterTextChangedListener);
                }
            }
        });
    }

    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openCreateAccSuccess() {
        Intent intent = new Intent(this, CreateAccSuccess.class);
        startActivity(intent);
    }

    public void openCreateAccFail() {
        Intent intent = new Intent(this, CreateAccFail.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res;
            boolean str = false;

            try {
                req.put("email", username.getText().toString());
                req.put("name", personName.getText().toString());
                req.put("password", password.getText().toString());
                req.put("phone number", phoneNumber.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPost("http://10.0.2.2:8080/users/mkuser", headerMap, req);
                str = res.getBoolean("isSuccess");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return str + "";
        }

    }
}