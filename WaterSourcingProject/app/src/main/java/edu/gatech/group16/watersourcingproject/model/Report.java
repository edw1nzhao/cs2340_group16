package edu.gatech.group16.watersourcingproject.model;

import java.util.Date;

public class Report {
    private int reportNumber;
    private Date date;
    private String location;
    private String submittedBy;

    public Report() {

    }

    public Report(int reportNumber, Date date, String location, String submittedBy) {
        this.submittedBy = submittedBy;
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
    }

    /**
     * Getter for submittedBy
     *
     * @return submittedBy
     */
    public String getSubmittedBy() {
        return submittedBy;
    }

    /**
     * Setter for submittedBy
     *
     * @param submittedBy
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     * Getter for reportNumber
     *
     * @return reportNumber
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Setter for reportNumber
     *
     * @param reportNumber
     */
    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    /**
     * Getter for date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for date
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for location
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
