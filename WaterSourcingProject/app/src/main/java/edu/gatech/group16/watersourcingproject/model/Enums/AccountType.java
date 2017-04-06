package edu.gatech.group16.watersourcingproject.model.Enums;

import java.io.Serializable;

/**
 * Created by Tomonari on 2/17/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public enum AccountType implements Serializable {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator");

    private final String accountType;

    @SuppressWarnings("unused")
    AccountType(String accountType) {
        this.accountType = accountType;
    }
    @SuppressWarnings({"unused", "JavaDoc"})
    public String getAccountType() {
        return accountType;
    }
}
