package edu.gatech.group16.watersourcingproject.model;

import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User implements Serializable {
    public static List<AccountType> legalClass = Arrays.asList(AccountType.values());
    public String name;
    public String email;
    public String password;
    public AccountType accountType;
    public List<WaterSourceReport> wsReport;
    public List<WaterPurityReport> wpReport;
    public String uid;


    /**
     * Empty constructor for Firebase
     *
     */
    public User() {

    }

    /**
     * Normal constructor for a user
     *
     * @param accountType
     * @param email
     * @param name
     * @param password
     * @param wsReport
     * @param wpReport
     * @param uid
     */
    public User(String email, String password, String name, AccountType accountType,
                List<WaterSourceReport> wsReport, List<WaterPurityReport> wpReport, String uid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accountType = accountType;
        this.wsReport = wsReport;
        this.wpReport = wpReport;
        this.uid = uid;
    }


    /**
     * Getter for name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for AccountType
     *
     * @return AccountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Setter for AccountType
     *
     * @param accountType
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter for WaterSourceReports
     *
     * @return WaterSourceReport[]
     */
    public List<WaterSourceReport> getWaterSourceReport() {
        return this.wsReport;
    }

    /**
     * Setter for WaterSourceReports
     *
     * @param wsReport
     */
    public void setWaterSourceReports(List<WaterSourceReport> wsReport) {
        this.wsReport = wsReport;
    }

    /**
     * Getter for WaterPurityReports
     *
     * @return WaterPurityReport[]
     */
    public List<WaterPurityReport> getWaterPurityReport() {
        return wpReport;
    }

    /**
     * Setter for WaterPurityReports
     *
     * @param wpReport
     */
    public void setWaterPurityReports(List<WaterPurityReport> wpReport) {
        this.wpReport = wpReport;
    }

    /**
     * Getter for User ID
     *
     * @return uid
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * Setter for User ID
     *
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountType=" + accountType +
                ", wsReport=" + wsReport +
                ", wpReport=" + wpReport +
                '}';
    }
}

