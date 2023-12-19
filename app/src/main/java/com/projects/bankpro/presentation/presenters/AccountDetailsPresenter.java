package com.projects.bankpro.presentation.presenters;

import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.data.model.Transaction;
import com.projects.bankpro.presentation.contracts.AccountDetailsContract;

import java.util.List;

public class AccountDetailsPresenter implements AccountDetailsContract.Presenter {
    private AccountDetailsContract.View view;
    private DatabaseManager databaseManager; // Assuming you have a database manager to handle queries

    public AccountDetailsPresenter(AccountDetailsContract.View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    @Override
    public void loadAccountDetails(long accountId) {
        // Load account details from the database using the accountId
        Account account = databaseManager.getAccountDetails(accountId);

        // Notify the view with the retrieved account details
        if (account != null) {
            view.showAccountDetails(account);
        } else {
            view.showAccountNotFoundError();
        }
    }

    @Override
    public void loadTransactionHistory(long accountId) {
        // Load transaction history for the account from the database using the accountId
        List<Transaction> transactionHistory = databaseManager.getTransactionHistory(accountId);

        // Notify the view with the transaction history
        if (transactionHistory != null) {
            view.showTransactionHistory(transactionHistory);
        } else {
            view.displayTransactionHistoryError(); // Handle error in case of failure to retrieve data
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
