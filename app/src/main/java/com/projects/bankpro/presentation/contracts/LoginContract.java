package com.projects.bankpro.presentation.contracts;

import com.projects.bankpro.data.model.Account;

public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showLoginError(String error);
        void navigateToMain(Account account);
    }

    interface Presenter {
        void login(String email, String password);
        void onDestroy();
    }
}

