package edu.gatech.group16.watersourcingproject.model.Enums;

/**
 * Created by Edwin Zhao on 2017/02/23.
 * Bottled, Well, Stream, Lake, Spring, Other
 */

public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String waterType;

    WaterType(String accountType) {
        this.waterType = accountType;
    }
    public String getWaterType() {
        return waterType;
    }
}
