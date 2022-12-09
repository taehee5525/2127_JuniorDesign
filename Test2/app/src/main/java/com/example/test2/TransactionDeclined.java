package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test2.ui.login.MainActivity;

public class TransactionDeclined extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_declined);

        Button backToPendingTransactionBtn = findViewById(R.id.backToPendingTransactionBtn);
        backToPendingTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendingTransaction();
            }
        });
    }

    private void openPendingTransaction() {
        Intent intent = new Intent(this, PendingTransactions.class);
        startActivity(intent);
    }
}