package com.projects.bankpro.data.model;

public class Transaction {
    private long transactionId;
    private String date;
    private double amount;
    private long accountId;

    public Transaction(long transactionId, String date, double amount, long accountId) {
        this.transactionId = transactionId;
        this.date = date;
        this.amount = amount;
        this.accountId = accountId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
