package com.projects.bankpro.presentation.presenters;


import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.presentation.contracts.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private DatabaseManager databaseManager; // Assuming you have a database manager to handle queries

    public LoginPresenter(LoginContract.View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    @Override
    public void login(String email, String password) {
        // Retrieve account details from the database
        Account account = databaseManager.getAccountByEmail(email);

        // Check if the account exists and if the password matches
        if (account != null && account.getPassword().equals(password)) {
            view.navigateToMain(account); // Successful login, navigate to the main screen
        } else {
            view.showLoginError("Invalid credentials");
        }
    }


    @Override
    public void onDestroy() {
        view = null;
    }
}
