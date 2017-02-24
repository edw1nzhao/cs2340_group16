package edu.gatech.group16.watersourcingproject.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;


import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Fills the spinners with ENUM
        ArrayAdapter<AccountType> adaptWaterQuality
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalQuality);
        ArrayAdapter<AccountType> adaptWaterType
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);
    }
}
