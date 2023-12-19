package com.projects.bankpro.utils.helpers;

import android.util.Patterns;

public class ValidationHelper {

    public static boolean isValidPassword(String password) {
        // Password validation criteria (customize as needed)
        // Example: Password must have at least 8 characters
        return password != null && password.length() >= 8;
    }

    public static boolean isValidEmail(String email) {
        // Check for null or empty email
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Use Android's built-in Patterns class to check email format
        // Returns true if the email matches the standard email pattern, false otherwise
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
