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
import android.widget.Spinner;

import java.util.Date;
import java.util.List;
import android.location.Location;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    private User user;
    private String currentDateTimeString;
    private List<WaterSourceReport> wsReports;
    private Spinner waterType;
    private Spinner waterCondition;

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

        waterType = (Spinner) findViewById(R.id.spinner_watertype);
        waterCondition = (Spinner) findViewById(R.id.spinner_watercondition);
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
        int reportNumber = getReportNumber();
        Date currentDate = new Date();
        Location location = getUserLocation();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();



        WaterSourceReport wsReport = new WaterSourceReport(reportNumber, currentDate, location, type, condition, submittedBy);
        return wsReport;
    }

    public static int getReportNumber() {
        //TO DO: Retrieve number of reports from Firebase variable.
        return 0;
    }

    public static Location getUserLocation() {
        //TO DO: Retrieve actual location from phone.
        Location location = new Location("Temp Location");
        location.setLatitude(1.2345d);
        location.setLongitude(1.2345d);
        return location;
    }
}
