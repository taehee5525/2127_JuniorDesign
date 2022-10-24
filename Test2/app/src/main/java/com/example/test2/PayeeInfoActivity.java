package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class PayeeInfoActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payee_info);

        button = (Button) findViewById(R.id.completeTransaction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openTransactionSuccess(); }
        });
    }

    public void openTransactionSuccess() {
        Intent intent = new Intent(this, TransactionSuccess.class);
        startActivity(intent);
    }
}