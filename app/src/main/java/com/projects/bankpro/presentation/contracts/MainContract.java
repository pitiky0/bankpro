package com.projects.bankpro.presentation.contracts;

import com.projects.bankpro.data.model.Account;

public interface MainContract {
    interface View {
        void showWelcomeMessage(String message);
        void showAccountBalance(double balance);
        void enableValidateButton(boolean isEnabled);
        void showTransactionSuccessMessage();
        void showTransactionFailureMessage();

        void displayAccountBalance(double balance);
        // Add more methods for UI interactions as needed
    }

    interface Presenter {
        void onViewAttached(View view);
        void onViewDetached();
        void performTransaction(Account account, int amount, String transactionType);

        void calculateBalance(Account account);
        // Add more methods for handling business logic and view interactions
    }

    interface Repository {
        void performTransaction(Account account, int amount, String transactionType, TransactionCallback callback);
        // Add more methods for data operations like fetching account details, transactions, etc.
    }

    interface TransactionCallback {
        void onTransactionSuccess();
        void onTransactionFailure();
    }
}
