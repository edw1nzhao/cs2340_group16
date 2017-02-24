package edu.gatech.group16.watersourcingproject.model.Enums;

/**
 * Created by Edwin Zhao on 2017/02/23.
 */

public enum WaterQuality {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String waterQuality;

    WaterQuality(String accountType) {
        this.waterQuality = accountType;
    }
    public String getWaterQuality() {
        return waterQuality;
    }
}
