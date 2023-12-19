package com.projects.bankpro.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.bankpro.R;
import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.data.model.Transaction;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private TableLayout tableLayoutTransactions;
    private Account selectedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initializeComponents();
        retrieveSelectedAccount();
        calculateBalanceAndPopulateTransactions();
    }

    private void initializeComponents() {
        databaseManager = new DatabaseManager();
        DatabaseManager.initialize(this);
        tableLayoutTransactions = findViewById(R.id.tableLayoutTransactions);
    }

    private void retrieveSelectedAccount() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("history_account_extra")) {
            selectedAccount = (Account) intent.getSerializableExtra("history_account_extra");
        }
    }

    private void calculateBalanceAndPopulateTransactions() {
        if (selectedAccount != null && tableLayoutTransactions != null) {
            List<Transaction> transactions = databaseManager.getTransactionsForAccount(selectedAccount.getAccountId());
            double balance = 0.0;

            for (Transaction transaction : transactions) {
                balance += transaction.getAmount();
                addTransactionRow(transaction);
            }

            setResultAndFinish(balance);
        }
    }

    private void setResultAndFinish(double balance) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("BALANCE_EXTRA", balance);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void addTransactionRow(Transaction transaction) {
        if (tableLayoutTransactions != null) {
            TableRow row = new TableRow(this);

            String[] transactionDetails = {
                    String.valueOf(transaction.getTransactionId()),
                    transaction.getDate(),
                    String.valueOf(transaction.getAmount()),
                    selectedAccount != null ? selectedAccount.getAccountName() : ""
            };

            for (String detail : transactionDetails) {
                TextView textView = createTextView(detail);
                row.addView(textView);
            }

            tableLayoutTransactions.addView(row);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setBackgroundResource(R.color.black);
        textView.setGravity(android.view.Gravity.CENTER);
        return textView;
    }
}
