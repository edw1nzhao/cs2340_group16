package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;


import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    private User user;
    private String currentDateTimeString;
    private List<WaterSourceReport> wsReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ws_report);
        // Fills the spinners with ENUM
        ArrayAdapter<AccountType> adaptWaterCondition
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalConditions);
        ArrayAdapter<AccountType> adaptWaterType
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);


        user = (User) getIntent().getSerializableExtra("USER");

        findViewById(R.id.edit_button_cancel).setOnClickListener(this);
        findViewById(R.id.edit_button_save).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.edit_button_cancel) {
            Intent cancelIntent = new Intent(this, HomeActivity.class);
            cancelIntent.putExtra("USER", user);
            startActivity(cancelIntent);
        } else if (i == R.id.edit_button_save) {
            Intent saveIntent = new Intent(this, HomeActivity.class);
            wsReports = user.getWaterSourceReport();
            wsReports.add(compileReport());
            user.setWaterSourceReports(wsReports);
            saveIntent.putExtra("USER", user);
            startActivity(saveIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WaterSourceReport compileReport() {
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        WaterSourceReport wsReport = new WaterSourceReport();
        return wsReport;
    }
}
