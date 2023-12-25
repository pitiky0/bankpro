package com.projects.bankpro.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projects.bankpro.R;
import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.data.model.Transaction;

import java.time.Instant;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_HISTORY = 101;

    // Views
    private RadioButton verserRadioButton, debitRadioButton, creditRadioButton;
    private Spinner accountSpinner;
    private EditText transactionAmountEditText;
    private Button validateButton;

    // Data
    private DatabaseManager databaseManager;
    private Account account;
    private double accountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        retrieveLoggedInAccount();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        startHistoryActivity();

        if (requestCode == REQUEST_CODE_HISTORY && resultCode == RESULT_OK && data != null) {
            accountBalance = data.getDoubleExtra("BALANCE_EXTRA", 10.0);
            setAccountBalanceText();
        }
    }

    private void initializeViews() {
        databaseManager = new DatabaseManager();
        DatabaseManager.initialize(this);

        verserRadioButton = findViewById(R.id.verserRadioButton);
        debitRadioButton = findViewById(R.id.debitRadioButton);
        creditRadioButton = findViewById(R.id.creditRadioButton);
        accountSpinner = findViewById(R.id.accountSpinner);
        transactionAmountEditText = findViewById(R.id.transactionAmountEditText);
        validateButton = findViewById(R.id.validateButton);
    }

    private void retrieveLoggedInAccount() {
        if (getIntent().hasExtra("loged_account_extra")) {
            account = (Account) getIntent().getSerializableExtra("loged_account_extra");
            setWelcomeText(account != null ? account.getAccountName() : null);
        }
    }

    private void setupUI() {
        setListeners();
        validateButton.setEnabled(false);
        populateSpinner(account != null ? account.getAccountName() : "");
        setAccountBalanceText();
    }

    private void setWelcomeText(String accountName) {
        TextView welcomeTextView = findViewById(R.id.welcome);
        welcomeTextView.setText(String.format("Bienvenue: %s", accountName));
    }

    private void setListeners() {
        findViewById(R.id.transactionHistoryButton).setOnClickListener(v -> startHistoryActivity());

        findViewById(R.id.logout_button).setOnClickListener(v -> logout());

        transactionAmountEditText.addTextChangedListener(textWatcher);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> validateButtonState());

        if(verserRadioButton.isChecked()){
            findViewById(R.id.selectAccount).setVisibility(View.VISIBLE);
            accountSpinner.setVisibility(View.VISIBLE);
        }

        validateButton.setOnClickListener(v -> validateOperation());
    }

    private void validateOperation() {
        String amountString = transactionAmountEditText.getText().toString();
        new Transaction(1, "", 500.0, 1);
        if(creditRadioButton.isChecked()){
            long accountId = account.getAccountId();
            int amount = Integer.parseInt(amountString);
            String date = Instant.now().toString();

//            if (InputValidator.isValidAmount(amount)) {
//                mainPresenter.credit(date, amount, accountId);
//            } else {
//                // Handle invalid inputs, show error messages, etc.
//            }
        }
    }

    private final ActivityResultLauncher<Intent> historyLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Use the balance received from HistoryActivity
                    setAccountBalanceText();
                }
            }
    );

    private void startHistoryActivity() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        intent.putExtra("history_account_extra", account);
        historyLauncher.launch(intent);
    }


    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("email_extra", account != null ? account.getEmail() : "");
        startActivity(intent);
        finish();
    }

    private void setAccountBalanceText() {
        TextView accountBalanceTextView = findViewById(R.id.accountBalanceTextView);
        String amountText = "Account Balance: $" + accountBalance;
        accountBalanceTextView.setText(amountText);
    }

    private void populateSpinner(String username) {
        List<String> accountNames = databaseManager.getAccountNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);

        int position = accountNames.indexOf(username);
        if (position >= 0) {
            accountSpinner.setSelection(position);
        }
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void validateButtonState() {
        String amount = transactionAmountEditText.getText().toString();
        try {
            boolean isCreditChecked = creditRadioButton.isChecked();
            boolean isDebitChecked = debitRadioButton.isChecked();
            boolean isVerserChecked = verserRadioButton.isChecked();

            if (isCreditChecked) {
                validateButton.setEnabled(!amount.isEmpty() && Integer.parseInt(amount) > 0);
            }

            if (isDebitChecked || isVerserChecked) {
                boolean isAmountValid = !amount.isEmpty() && Integer.parseInt(amount) > 0;
                boolean isBalanceValid = accountBalance >= Integer.parseInt(amount);

                if (isDebitChecked) {
                    validateButton.setEnabled(isAmountValid && Integer.parseInt(amount) >= 100 && ((Integer.parseInt(amount) % 100) == 0) && isBalanceValid);
                }

                if (isVerserChecked) {
                    validateButton.setEnabled(isAmountValid && isBalanceValid);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
