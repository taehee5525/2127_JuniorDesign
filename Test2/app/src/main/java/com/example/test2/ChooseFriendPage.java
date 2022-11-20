package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.test2.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooseFriendPage extends AppCompatActivity {
    private ApiCallMaker apicall = new ApiCallMaker();
    private Map<String, String> headerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_friend_page);

        LinearLayout currLayout = findViewById(R.id.chooseFriendListLayout);
        int drawableRoundBtn = R.drawable.rounded_button;

        Button main = findViewById(R.id.backToMain);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });

        CustomTask task = new CustomTask();
        try {
            String friendList = task.execute().get();
            String[] eachEmail = friendList.split(",");

            for (int i = 0; i < eachEmail.length; i++) {
                int currAddr = i;
                Button emailAddr = new Button(this);

                eachEmail[i] = eachEmail[i].replace("}", "");
                eachEmail[i] = eachEmail[i].replaceAll("\"", "");
                eachEmail[i] = eachEmail[i].replace(":", " \nName: ");
                eachEmail[i] = eachEmail[i].replace("{", "Email: ");

                if (i == 0) {
                    eachEmail[i] = eachEmail[i].replace("[", "");
                }

                if (i == eachEmail.length - 1) {
                    eachEmail[i] = eachEmail[i].replace("]", "");
                }

                eachEmail[i] = eachEmail[i].replaceAll("^\"|\"$", "");

                emailAddr.setText(eachEmail[i]);
                emailAddr.setTextSize(18);
                emailAddr.setTextColor(Color.DKGRAY);
                emailAddr.setGravity(Gravity.CENTER);
                emailAddr.setAllCaps(false);
                emailAddr.setBackgroundColor(Color.parseColor("#f6f6f6"));
                emailAddr.setPadding(35, 10, 0, 20);
                currLayout.addView(emailAddr);

                emailAddr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(eachEmail[currAddr]);
                        while (matcher.find()) {
                            int matchStart = matcher.start(0);
                            int matchEnd = matcher.end(0);
                            Utility.friendEmail = eachEmail[currAddr].substring(matchStart, matchEnd);
                        }
                        Log.w("friend email", Utility.friendEmail);
                        openMainPage();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONObject res = new JSONObject();
            JSONObject req = new JSONObject();

            String friendList = "";

            try {
                req.put("token", Utility.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", Utility.token);

            try {
                res = apicall.callGet("http://10.0.2.2:8080/friends/getFriendList", headerMap, paramMap);
                friendList = res.get("friendList").toString();

                Log.w("friendList", friendList);
            }  catch (Exception e) {
                e.printStackTrace();
            }

            return friendList;
        }


    }
}