package edu.gatech.group16.watersourcingproject.model;

import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Edwin Zhao on 2017/02/21.
 */

public class User implements Serializable {
    public static List<AccountType> legalClass = Arrays.asList(AccountType.values());
    public String name;
    public String email;
    public String password;
    public AccountType accountType;
    public List<WaterSourceReport> wsReport;

    public User() {
    }
    public User(String email, String password, String name, AccountType accountType,
                List<WaterSourceReport> wsReport) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accountType = accountType;
        this.wsReport = wsReport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<WaterSourceReport> getWaterSourceReport() {
        return this.wsReport;
    }

    public void setWaterSourceReports(List<WaterSourceReport> wsReport) {
        this.wsReport = wsReport;
    }
}

