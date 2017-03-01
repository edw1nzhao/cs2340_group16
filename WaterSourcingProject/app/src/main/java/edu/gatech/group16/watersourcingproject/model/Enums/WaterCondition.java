package edu.gatech.group16.watersourcingproject.model.Enums;

import java.io.Serializable;

public enum WaterCondition implements Serializable {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String waterCondition;

    WaterCondition(String accountType) {
        this.waterCondition = accountType;
    }
    public String getWaterCondition() {
        return waterCondition;
    }
}
