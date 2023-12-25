package com.projects.bankpro.presentation.presenters;

import com.projects.bankpro.data.database.DatabaseManager;
import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.presentation.contracts.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private DatabaseManager databaseManager;

    public MainPresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void onViewAttached(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void performTransaction(Account account, int amount, String transactionType) {
        if (view != null) {
            databaseManager.performTransaction(account, amount, transactionType, new MainContract.TransactionCallback() {
                @Override
                public void onTransactionSuccess() {
                    view.showTransactionSuccessMessage();
                }

                @Override
                public void onTransactionFailure() {
                    view.showTransactionFailureMessage();
                }
            });
        }
    }

    @Override
    public void calculateBalance(Account account) {
        if (view != null) {
            double balance = databaseManager.calculateBalanceForAccount(account);
            view.displayAccountBalance(balance);
        }
    }
}
