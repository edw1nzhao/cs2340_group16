package edu.gatech.group16.watersourcingproject.model.Enums;

import java.io.Serializable;

/**
 * Created by Tomonari on 3/19/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public enum OverallCondition implements Serializable {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String overallCondition;

    @SuppressWarnings("unused")
    OverallCondition(String overallCondition) {

        this.overallCondition = overallCondition;
    }
    @SuppressWarnings({"unused", "JavaDoc"})
    public String getOverallCondition() {

        return overallCondition;
    }
}
