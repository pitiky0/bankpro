package com.projects.bankpro.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projects.bankpro.data.model.Account;
import com.projects.bankpro.data.model.Transaction;
import com.projects.bankpro.presentation.contracts.MainContract;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;

    public static void initialize(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
            database = databaseHelper.getWritableDatabase();
        }
    }

    public static void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null; // Reset the database object
        }
    }

    private static SQLiteDatabase getDatabaseInstance() {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    public Account getAccountDetails(long accountId) {
        Account account = null;

        try (Cursor cursor = queryAccountByAccountId(accountId)) {
            if (cursor != null && cursor.moveToFirst()) {
                account = extractAccountFromCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return account;
    }

    private Cursor queryAccountByAccountId(long accountId) {
        SQLiteDatabase db = getDatabaseInstance();
        return db.query(
                "Account",
                null,
                "accountId = ?",
                new String[]{String.valueOf(accountId)},
                null,
                null,
                null
        );
    }

    private Account extractAccountFromCursor(Cursor cursor) {
        long accountId = cursor.getLong(cursor.getColumnIndex("accountId"));
        String accountName = cursor.getString(cursor.getColumnIndex("accountName"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        return new Account(accountId, accountName, password, email);
    }

    public List<Transaction> getTransactionHistory(long accountId) {
        List<Transaction> transactionHistory = new ArrayList<>();

        try (Cursor cursor = queryTransactionsByAccountId(accountId)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    transactionHistory.add(extractTransactionFromCursor(cursor, accountId));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactionHistory;
    }

    private Cursor queryTransactionsByAccountId(long accountId) {
        SQLiteDatabase db = getDatabaseInstance();
        return db.query(
                "Transactions",
                new String[]{"transactionId", "date", "amount"},
                "accountId = ?",
                new String[]{String.valueOf(accountId)},
                null,
                null,
                "date DESC"
        );
    }

    private Transaction extractTransactionFromCursor(Cursor cursor, long accountId) {
        long transactionId = cursor.getLong(cursor.getColumnIndex("transactionId"));
        String date = cursor.getString(cursor.getColumnIndex("date"));
        double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
        return new Transaction(transactionId, date, amount, accountId);
    }

    public Account getAccountByEmail(String email) {
        Account account = null;

        try {
            Cursor cursor = queryAccountByEmail(email);
            if (cursor != null && cursor.moveToFirst()) {
                long accountId = cursor.getLong(cursor.getColumnIndex("accountId"));
                String accountName = cursor.getString(cursor.getColumnIndex("accountName"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                account = new Account(accountId, accountName, password, email);
            }
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    private Cursor queryAccountByEmail(String email) {
        return database.query(
                "Account",
                new String[]{"accountId", "accountName", "password"},
                "email = ?",
                new String[]{email},
                null,
                null,
                null
        );
    }

    public long createAccount(String accountName, String password, String email) {
        ContentValues values = new ContentValues();
        values.put("accountName", accountName);
        values.put("password", password);
        values.put("email", email);

        SQLiteDatabase db = getDatabaseInstance();
        return db.insert("Account", null, values);
    }

    public List<Transaction> getTransactionsForAccount(long accountId) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = getDatabaseInstance();

        try (Cursor cursor = db.query(
                "Transactions",
                new String[]{"transactionId", "date", "amount"},
                "accountId =?",
                new String[]{String.valueOf(accountId)},
                null,
                null,
                null)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex("transactionId"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    double amount = cursor.getDouble(cursor.getColumnIndex("amount"));

                    Transaction transaction = new Transaction(id, date, amount, accountId);
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<String> getAccountNames() {
        List<String> accountNames = new ArrayList<>();
        SQLiteDatabase db = getDatabaseInstance();

        try (Cursor cursor = db.query(
                "Account",
                new String[]{"accountName"},
                null,
                null,
                null,
                null,
                null)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String accountName = cursor.getString(cursor.getColumnIndex("accountName"));

                    accountNames.add(accountName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accountNames;
    }

    public void performTransaction(Account account, int amount, String transactionType, MainContract.TransactionCallback transactionCallback) {
    }

    public double calculateBalanceForAccount(Account account) {
        return 0;
    }
}
