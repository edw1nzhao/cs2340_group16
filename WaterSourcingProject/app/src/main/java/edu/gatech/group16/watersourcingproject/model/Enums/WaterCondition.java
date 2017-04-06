package edu.gatech.group16.watersourcingproject.model.Enums;

import java.io.Serializable;

@SuppressWarnings("JavaDoc")
public enum WaterCondition implements Serializable {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String waterCondition;

    @SuppressWarnings("unused")
    WaterCondition(String waterCondition) {
        this.waterCondition = waterCondition;
    }
    @SuppressWarnings({"unused", "JavaDoc"})
    public String getWaterCondition() {
        return waterCondition;
    }
}
