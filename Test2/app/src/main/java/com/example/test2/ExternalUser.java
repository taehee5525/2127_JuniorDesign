package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test2.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ExternalUser extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();
    String extUserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_user);

        Button backToMainBtn;
        backToMainBtn = findViewById(R.id.backToMainBtn);

        EditText extUserEmailTextField;
        extUserEmailTextField = findViewById(R.id.extUserEmail);


        backToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extUserEmail = extUserEmailTextField.getText().toString();
                CustomTask task = new CustomTask();

                if (Utility.isExpiredToken(Utility.token)) {
                    openTimeExpMsg();
                } else {
                    try {
                        String state = task.execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    openMain();
                }
            }
        });


    }

    private void openTimeExpMsg() {
        Intent intent = new Intent(this, TimeExpireMsg.class);
        startActivity(intent);
    }
    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            String state = "";

            try {
                req.put("email", extUserEmail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String, String> paramMap = new HashMap<>();

            try {
                res = apicall.callGet("http://techpay.eastus.cloudapp.azure.com:8080/extuserlookup/ACCOUNT_ID/" + extUserEmail,
                        headerMap, paramMap);
                state = res.get("name").toString();
                Log.w("state", state);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return state;
        }
    }

}