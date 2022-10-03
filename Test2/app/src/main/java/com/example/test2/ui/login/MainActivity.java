package com.example.test2.ui.login;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test2.InstructionsPage;
import com.example.test2.PayeeInfoActivity;
import com.example.test2.R;

public class MainActivity extends AppCompatActivity {
    Button transferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        button = (Button) findViewById(R.id.second_activity_transaction_button);
        transferBtn = (Button) findViewById(R.id.second_activity_transaction_button);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Here");
                openPayeeInfo();
            }
        });
    }

    private void openPayeeInfo() {
        Intent intent = new Intent(this, PayeeInfoActivity.class);
        startActivity(intent);
    }
}