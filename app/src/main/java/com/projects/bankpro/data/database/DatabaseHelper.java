package com.projects.bankpro.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bank_db";
    private static final int DATABASE_VERSION = 1;

    // Table creation SQL statements
    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE Account (accountId INTEGER PRIMARY KEY AUTOINCREMENT, accountName TEXT, password TEXT, email TEXT)";
    private static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE Transactions (transactionId INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, amount REAL, accountId INTEGER, FOREIGN KEY(accountId) REFERENCES Account(accountId))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(db);
    }
}

