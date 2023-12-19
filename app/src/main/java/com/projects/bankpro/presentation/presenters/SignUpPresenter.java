package com.projects.bankpro.presentation.presenters;

import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.presentation.contracts.SignUpContract;
import com.projects.bankpro.utils.helpers.ValidationHelper;

public class SignUpPresenter implements SignUpContract.Presenter {
    private SignUpContract.View view;
    private DatabaseManager databaseManager; // Assuming you have a database manager to handle queries

    public SignUpPresenter(SignUpContract.View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    @Override
    public void signUp(String accountName, String password, String email) {
        // Validate inputs
        if (!ValidationHelper.isValidEmail(email)) {
            view.showSignUpError("Invalid email address");
            return;
        }

        if (!ValidationHelper.isValidPassword(password)) {
            view.showSignUpError("Invalid password");
            return;
        }

        // Check if the email is already registered
        Account existingAccount = databaseManager.getAccountByEmail(email);
        if (existingAccount != null) {
            view.showSignUpError("Email already exists");
            return;
        }

        // Create a new account in the database
        long accountId = databaseManager.createAccount(accountName, password, email);
        if (accountId != -1) {
            view.navigateToLogin(email);
        } else {
            view.showSignUpError("Failed to create account");
        }
    }


    @Override
    public void onDestroy() {
        view = null;
    }
}
