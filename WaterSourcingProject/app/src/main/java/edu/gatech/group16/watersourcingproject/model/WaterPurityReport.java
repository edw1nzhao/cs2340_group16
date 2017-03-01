package edu.gatech.group16.watersourcingproject.model;

import android.location.Location;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;

public class WaterPurityReport implements Serializable {
    public static List<WaterCondition> legalConditions = Arrays.asList(WaterCondition.values());

    private int reportNumber;
    private Date date;
    private Location location;
    private WaterCondition waterCondition;
    private String submittedBy;
    private int virusPPM;
    private int contaminantPPM;


    public WaterPurityReport() {
    }

    public WaterPurityReport(int reportNumber, Date date, Location location,
                             WaterCondition waterCondition, String submittedBy,
                             int virusPPM, int contaminantPPM) {
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
        this.waterCondition = waterCondition;
        this.submittedBy = submittedBy;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }



    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    public void setWaterCondition(WaterCondition waterQuality) { this.waterCondition = waterCondition; }

    public int getVirusPPM() { return virusPPM; }

    public void setVirusPPM(int virusPPM) { this.virusPPM = virusPPM; }

    public int getContaminantPPM() { return contaminantPPM; }

    public void setContaminantPPM(int contaminantPPM) { this.contaminantPPM = contaminantPPM; }

    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "reportNumber=" + reportNumber +
                ", date=" + date +
                ", location=" + location +
                ", waterCondition=" + waterCondition +
                ", virusPPM=" + virusPPM +
                ", contaminantPPM=" + contaminantPPM +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }


}
