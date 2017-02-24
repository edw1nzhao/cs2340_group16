package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;


import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ws_report);
        // Fills the spinners with ENUM
        ArrayAdapter<AccountType> adaptWaterCondition
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalConditions);
        ArrayAdapter<AccountType> adaptWaterType
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);

        findViewById(R.id.edit_button_cancel).setOnClickListener(this);
        findViewById(R.id.edit_button_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.edit_button_cancel) {
            Intent cancelIntent = new Intent(this, HomeActivity.class);
            startActivity(cancelIntent);
        } else if (i == R.id.edit_button_save) {
            Intent saveIntent = new Intent(this, HomeActivity.class);
            startActivity(saveIntent);
        }
    }
}
