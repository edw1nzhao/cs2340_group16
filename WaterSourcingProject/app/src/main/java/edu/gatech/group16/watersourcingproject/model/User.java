package edu.gatech.group16.watersourcingproject.model;

import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"ClassWithTooManyDependents", "JavaDoc"})
public class User implements Serializable {
    public static final List<AccountType> legalClass = Arrays.asList(AccountType.values());
    private String name;
    private String email;
    private String password;
    private AccountType accountType;
    private List<WaterSourceReport> wsReport;
    private List<WaterPurityReport> wpReport;
    private String uid;


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
    @SuppressWarnings({"JavaDoc", "ConstructorWithTooManyParameters"})
    public User(String email, String password, String name, AccountType accountType,
                List<WaterSourceReport> wsReport, List<WaterPurityReport> wpReport, String uid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accountType = accountType;
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.wsReport = wsReport;
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
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
    @SuppressWarnings("JavaDoc")
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
    @SuppressWarnings("JavaDoc")
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
    @SuppressWarnings("JavaDoc")
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
    @SuppressWarnings("JavaDoc")
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
    @SuppressWarnings("JavaDoc")
    public void setWaterSourceReports(List<WaterSourceReport> wsReport) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
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
    @SuppressWarnings("JavaDoc")
    public void setWaterPurityReports(List<WaterPurityReport> wpReport) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
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
    @SuppressWarnings("JavaDoc")
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

