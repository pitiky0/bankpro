package com.projects.bankpro.data.model;

import java.io.Serializable;

public class Account implements Serializable {
    private long accountId;
    private String accountName;
    private String password;
    private String email;

    public Account(long accountId, String accountName, String password, String email) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.password = password;
        this.email = email;
    }


    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
