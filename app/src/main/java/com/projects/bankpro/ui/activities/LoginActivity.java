package com.projects.bankpro.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.projects.bankpro.R;
import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.presentation.contracts.LoginContract;
import com.projects.bankpro.presentation.presenters.LoginPresenter;
import com.projects.bankpro.utils.helpers.ValidationHelper;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter loginPresenter;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setUpListeners();
        initPresenterAndDBManager();
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    private void setUpListeners() {
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView textViewSignUp = findViewById(R.id.textViewSignUp);

        if (getIntent().hasExtra("email_extra")) {
            editTextEmail.setText(getIntent().getStringExtra("email_extra"));
        }

        buttonLogin.setOnClickListener(v -> login());
        textViewSignUp.setOnClickListener(v -> navigateToSignUpActivity());
    }

    private void initPresenterAndDBManager() {
        // Initialize the DatabaseManager with required parameters
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseManager.initialize(this);

        // Initialize the LoginPresenter with this activity as the view and the DatabaseManager
        loginPresenter = new LoginPresenter(this, databaseManager);
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (isValidCredentials(email, password)) {
            loginPresenter.login(email, password);
        } else {
            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidCredentials(String email, String password) {
        return ValidationHelper.isValidEmail(email) && ValidationHelper.isValidPassword(password);
    }

    @Override
    public void navigateToMain(Account account) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("loged_account_extra", account);
        startActivity(intent);
        finish();
    }

    private void navigateToSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
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
    public void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
