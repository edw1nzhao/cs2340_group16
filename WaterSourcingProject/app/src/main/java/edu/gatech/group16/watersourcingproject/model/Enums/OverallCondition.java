package edu.gatech.group16.watersourcingproject.model.Enums;

import java.io.Serializable;

/**
 * Created by Tomonari on 3/19/2017.
 */

public enum OverallCondition implements Serializable {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private String overallCondition;

    OverallCondition(String overallCondition) {

        this.overallCondition = overallCondition;
    }
    public String getOverallCondition() {

        return overallCondition;
    }
}
