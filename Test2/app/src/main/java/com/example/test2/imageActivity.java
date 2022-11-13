package com.example.test2;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test2.R;
import android.Manifest;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
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


import com.example.test2.R;

public class imageActivity extends AppCompatActivity {

    TextView captureTxt;
    String path;
    Uri uri;
    private ImageView captureImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_picker);
        captureTxt = findViewById(R.id.idEventBrowse);
        captureImage = findViewById(R.id.my_avatar);
        captureTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                ImagePicker.Companion.with(imageActivity.this)
                        .crop()
                        .maxResultSize(1080,1080)
                        .start(101);

                 */
            }
        });
    }
}