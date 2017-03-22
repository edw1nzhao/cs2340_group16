package edu.gatech.group16.watersourcingproject.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;

public class WaterSourceReport implements Serializable {
    public static List<WaterType> legalTypes = Arrays.asList(WaterType.values());
    public static List<WaterCondition> legalConditions = Arrays.asList(WaterCondition.values());

    private int reportNumber;
    private Date date;
    private String location;
    private WaterType waterType;
    private WaterCondition waterCondition;
    private String submittedBy;


    /**
     * Empty constructor for Firebase
     *
     */
    public WaterSourceReport() {

    }

    /**
     * Constructor for a WaterSourceReport
     *
     * @param reportNumber report number
     * @param date date
     * @param location location
     * @param waterType water type
     * @param waterCondition water condition
     * @param submittedBy submitted by
     */
    public WaterSourceReport(int reportNumber, Date date, String location,
                             WaterType waterType, WaterCondition waterCondition,
                             String submittedBy) {
        //super(reportNumber, date, location, submittedBy);
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.submittedBy = submittedBy;
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
     * @param submittedBy user submitted by
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     * Getter for reportNumber
     *
     * @return reportNumber number return
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Setter for reportNumber
     *
     * @param reportNumber given report number
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
     * @param date current date
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
     * @param location current location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for waterTypes
     *
     * @return waterType
     */
    public WaterType getWaterType() {
        return waterType;
    }

    /**
     * Setter for waterType
     *
     * @param waterType the watertype
     */
    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    /**
     * Getter for waterCondition
     *
     * @return waterCondition the water condition
     */
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    /**
     * Setter for waterCondition
     *
     * @param waterCondition
     */
    public void setWaterCondition(WaterCondition waterCondition) { this.waterCondition = waterCondition; }

    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "reportNumber=" + reportNumber +
                ", date=" + date +
                ", location=" + location +
                ", waterType=" + waterType +
                ", waterCondition=" + waterCondition +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }


}
