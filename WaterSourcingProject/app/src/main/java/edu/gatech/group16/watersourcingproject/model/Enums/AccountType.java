package edu.gatech.group16.watersourcingproject.model.Enums;

import java.io.Serializable;

/**
 * Created by Tomonari on 2/17/2017.
 */

public enum AccountType implements Serializable {
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
