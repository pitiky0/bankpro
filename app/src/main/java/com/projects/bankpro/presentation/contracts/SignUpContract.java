package com.projects.bankpro.presentation.contracts;

public interface SignUpContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showSignUpError(String error);
        void navigateToLogin(String email);
    }

    interface Presenter {
        void signUp(String username, String password, String email);
        void onDestroy();
    }
}

