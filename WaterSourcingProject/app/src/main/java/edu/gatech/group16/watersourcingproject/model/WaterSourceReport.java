package edu.gatech.group16.watersourcingproject.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;

@SuppressWarnings("JavaDoc")
public class WaterSourceReport implements Serializable {
    public static final List<WaterType> legalTypes
            = Arrays.asList(WaterType.values());
    public static final List<WaterCondition> legalConditions
            = Arrays.asList(WaterCondition.values());

    private int reportNumber;
    private Date date;
    private String location;
    private WaterType waterType;
    private WaterCondition waterCondition;
    private String submittedBy;
    private String uid;

    /**
     * Empty constructor for Firebase
     *
     */
    @SuppressWarnings("unused")
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
    @SuppressWarnings("ConstructorWithTooManyParameters")
    public WaterSourceReport(int reportNumber, Date date, String location,
                             WaterType waterType, WaterCondition waterCondition,
                             String submittedBy, String uid) {
        //super(reportNumber, date, location, submittedBy);
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.submittedBy = submittedBy;
        this.uid = uid;
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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


    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUid() {
        return this.uid;
    }

    /**
     * Setter for waterCondition
     *
     * @param waterCondition
     */
    @SuppressWarnings({"unused", "JavaDoc"})
    public void setWaterCondition(WaterCondition waterCondition) {
        this.waterCondition = waterCondition;
    }

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
