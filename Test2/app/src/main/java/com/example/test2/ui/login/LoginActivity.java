package com.example.test2.ui.login;
import com.example.test2.R;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.AsyncTask;

import com.example.test2.CreateAccActivity;
import com.example.test2.InstructionsPage;
import com.example.test2.LoginFail;
import com.example.test2.LoginSuccess;
import com.example.test2.ApiCallMaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private ImageButton imgButton;
    private Button signupBtn, loginBtn;
    private EditText username, password;
    private String usernameState, passwordState;

    static ApiCallMaker apicall = new ApiCallMaker();
    static Map<String, String> headerMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  checkDangerousPermissions();

        imgButton = (ImageButton) findViewById(R.id.helpBtn);
        signupBtn = (Button) findViewById(R.id.btnSignUp);
        loginBtn = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        usernameState = "0";
        passwordState = "0";

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

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (password.getText().toString().length() < 5 ||
                        password.getText().toString() == null) {
                    password.setError("password minimum contain 5 character");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                //String email = username.getText().toString().trim();

                if (username.getText().toString() == null ||
                        !Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches()) {
                    username.setError("email address form");

                } else if (password.getText().toString().length() < 5 ||
                        password.getText().toString() == null) {
                    password.setError("password minimum contain 5 character");

                } else {
                    usernameState = "1";
                    passwordState = "1";
                }
            }
        };
        username.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);


        // Login Page
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if login & password are in correct form, login button is clickable
                if (usernameState == "1" && passwordState == "1") {

                    Log.w("login", "login...");

                    try {
                        String id = username.getText().toString();
                        String pwd = password.getText().toString();

                        Log.w("id and password", id + ", " + pwd);

                        CustomTask task = new CustomTask();
                        String result = task.execute(id,pwd).get();

                        Log.w("login token", result);

                        if (result.length() > 2) {
                            openLoginSuccess();
                        } else {
                            openLoginFail();
                        }

                    } catch (Exception e) {
                    }

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

    private void openLoginSuccess() {
        Intent intent = new Intent(this, LoginSuccess.class);
        startActivity(intent);
    }

    private void openLoginFail() {
        Intent intent = new Intent(this, LoginFail.class);
        startActivity(intent);
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "authorized", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "no authorized", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "need help with authorization.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject req = new JSONObject();
            JSONObject res = new JSONObject();
            String str = new String();

            try {
                req.put("email", username.getText().toString());
                req.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                res = apicall.callPost("http://10.0.2.2:8080/users/userlogin", headerMap, req);
            }  catch (Exception e) {
                e.printStackTrace();
            }

            try {
                str = res.get("token").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return str;
        }


    }

}