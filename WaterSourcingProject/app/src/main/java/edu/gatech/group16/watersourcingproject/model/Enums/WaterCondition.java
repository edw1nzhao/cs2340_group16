package edu.gatech.group16.watersourcingproject.model.Enums;

public enum WaterCondition {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String waterCondition;

    WaterCondition(String accountType) {
        this.waterCondition = accountType;
    }
    public String getWaterQuality() {
        return waterCondition;
    }
}
