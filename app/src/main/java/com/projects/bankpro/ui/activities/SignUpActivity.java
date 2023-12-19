package com.projects.bankpro.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.bankpro.R;
import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.presentation.contracts.SignUpContract;
import com.projects.bankpro.presentation.presenters.SignUpPresenter;
import com.projects.bankpro.utils.helpers.ValidationHelper;
import com.projects.bankpro.utils.validators.InputValidator;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {
    private SignUpPresenter signUpPresenter;
    private EditText accountNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize the DatabaseManager
        DatabaseManager databaseManager = new DatabaseManager(/* pass required parameters */);

        // You can initialize the DatabaseManager here using 'this' as the context
        DatabaseManager.initialize(this);

        // Initialize the SignUpPresenter with this activity as the view and the DatabaseManager
        signUpPresenter = new SignUpPresenter(this, databaseManager);

        initializeViews();
        initializeListeners();
    }

    private void initializeViews() {
        accountNameEditText = findViewById(R.id.account_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
    }

    private void initializeListeners() {
        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(view -> signUp());

        TextView loginLink = findViewById(R.id.login_link);
        loginLink.setOnClickListener(view -> navigateToLogin(emailEditText.getText().toString()));
    }

    private void signUp() {
        String accountName = accountNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (isValidInput(accountName, email, password)) {
            signUpPresenter.signUp(accountName, password, email);
            navigateToLogin(email);
        } else {
            // Handle invalid inputs, show error messages, etc.
        }
    }

    private boolean isValidInput(String accountName, String email, String password) {
        return InputValidator.isValidUsername(accountName) &&
                ValidationHelper.isValidEmail(email) &&
                ValidationHelper.isValidPassword(password);
    }
    @Override
    public void navigateToLogin(String email) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        intent.putExtra("email_extra", email);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSignUpError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
