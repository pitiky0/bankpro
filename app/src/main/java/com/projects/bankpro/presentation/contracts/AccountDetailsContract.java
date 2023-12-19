package com.projects.bankpro.presentation.contracts;

import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.data.model.Transaction;

import java.util.List;

public interface AccountDetailsContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showAccountDetails(Account account);
        void showTransactionHistory(List<Transaction> transactions);

        void showAccountNotFoundError();

        void displayTransactionHistoryError();
    }

    interface Presenter {
        void loadAccountDetails(long accountId);
        void loadTransactionHistory(long accountId);
        void onDestroy();
    }
}

