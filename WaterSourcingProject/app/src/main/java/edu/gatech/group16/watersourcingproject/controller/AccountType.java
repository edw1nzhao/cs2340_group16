package edu.gatech.group16.watersourcingproject.controller;

/**
 * Created by Tomonari on 2/17/2017.
 */

public enum AccountType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator");

    private String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getAccountType() {
        return accountType;
    }
}
