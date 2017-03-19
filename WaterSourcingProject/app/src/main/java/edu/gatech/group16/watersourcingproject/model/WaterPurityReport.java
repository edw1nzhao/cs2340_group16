package edu.gatech.group16.watersourcingproject.model;

import android.location.Location;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.model.Enums.OverallCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;

public class WaterPurityReport implements Serializable {
    public static List<OverallCondition> legalOveralConditions = Arrays.asList(OverallCondition.values());

    private int reportNumber;
    private Date date;
    private String location;
    private OverallCondition overallCondition;
    private String submittedBy;
    private int virusPPM;
    private int contaminantPPM;

    /**
     * Empty constructor for Firebase
     *
     */
    public WaterPurityReport() {
    }

    /**
     * Normal constructor for a WaterPurityReport
     *
     * @param reportNumber
     * @param date
     * @param location
     * @param overallCondition
     * @param submittedBy
     * @param virusPPM
     * @param contaminantPPM
     */
    public WaterPurityReport(int reportNumber, Date date, String location,
                             OverallCondition overallCondition, String submittedBy,
                             int virusPPM, int contaminantPPM) {
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
        this.overallCondition = overallCondition;
        this.submittedBy = submittedBy;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
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

    /**
     * Getter for waterCondition
     *
     * @return overallCondition
     */
    public OverallCondition getOverallCondition() {
        return overallCondition;
    }

    /**
     * Setter for waterCondition
     *
     * @param overallCondition
     */
    public void setOverallCondition(OverallCondition overallCondition) { this.overallCondition = overallCondition; }

    /**
     * Getter for virusPPM
     *
     * @return virusPPM
     */
    public int getVirusPPM() { return virusPPM; }

    /**
     * Setter for virusPPM
     *
     * @param virusPPM
     */
    public void setVirusPPM(int virusPPM) { this.virusPPM = virusPPM; }

    /**
     * Getter for contaminantPPM
     *
     * @return contaminantPPM
     */
    public int getContaminantPPM() { return contaminantPPM; }

    /**
     * Setter for contaminantPPM
     *
     * @param contaminantPPM
     */
    public void setContaminantPPM(int contaminantPPM) { this.contaminantPPM = contaminantPPM; }

    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "reportNumber=" + reportNumber +
                ", date=" + date +
                ", location=" + location +
                ", waterCondition=" + overallCondition +
                ", virusPPM=" + virusPPM +
                ", contaminantPPM=" + contaminantPPM +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }
}
