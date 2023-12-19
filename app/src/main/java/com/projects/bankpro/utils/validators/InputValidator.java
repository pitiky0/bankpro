package com.projects.bankpro.utils.validators;

import android.text.TextUtils;

public class InputValidator {

    public static boolean isValidUsername(String accountName) {
        // Username validation criteria (customize as needed)
        // Example: accountName must not be empty and should not exceed a certain length
        return !TextUtils.isEmpty(accountName) && accountName.length() <= 40; // Example length limit
    }

    // Add more validation methods for other input fields as needed
}
