package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Report;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class ReportDetailsActivity extends AppCompatActivity {
    private User user;
    private TextView reportNumber;
    private TextView reportDate;
    private TextView reportLocation;
    private TextView reportAuthor;
    private TextView reportWaterCondition;
    private TextView reportWaterConditionLabel;
    private TextView reportWaterType;
    private TextView reportWaterTypeLabel;
    private TextView reportContaminantPPM;
    private TextView reportContaminantPPMLabel;
    private TextView title;
    private int position;
    private Toolbar toolbar;
    private WaterSourceReport wsReport;
    private WaterPurityReport wpReport;
    private String reportType;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        user = (User) getIntent().getSerializableExtra("USER");
        position =  (int) getIntent().getSerializableExtra("POSITION");
        reportType = (String) getIntent().getSerializableExtra("REPORT TYPE");
       

        //Shared UI
        reportNumber = (TextView) findViewById(R.id.val_reportNum);
        reportDate = (TextView) findViewById(R.id.val_reportDate);
        reportLocation = (TextView) findViewById(R.id.val_reportLoc);
        reportWaterConditionLabel = (TextView) findViewById(R.id.label_reportWC);
        reportWaterCondition = (TextView) findViewById(R.id.val_reportWC);
        reportWaterTypeLabel = (TextView) findViewById(R.id.label_reportWT);
        reportWaterType = (TextView) findViewById(R.id.val_reportWT);
        reportAuthor = (TextView) findViewById(R.id.val_reportAuth);
        reportContaminantPPM = (TextView) findViewById(R.id.val_reportContaminantPPM);
        reportContaminantPPMLabel = (TextView) findViewById(R.id.label_reportContaminantPPM);
        title = (TextView) findViewById(R.id.label_report);

        if (reportType.equals("WaterSourceReport")) {
            wsReport = (WaterSourceReport) getIntent().getSerializableExtra("REPORT");
            title.setText("Water Source Report #" + Integer.toString(wsReport.getReportNumber()));
            reportNumber.setText(Integer.toString(wsReport.getReportNumber()));
            reportDate.setText(wsReport.getDate().toString());
            reportLocation.setText(wsReport.getLocation());
            reportWaterCondition.setText(wsReport.getWaterCondition().toString());
            reportWaterType.setText(wsReport.getWaterType().toString());
            reportAuthor.setText(user.getName());

        } else if (reportType.equals("WaterPurityReport")){
            wpReport = (WaterPurityReport) getIntent().getSerializableExtra("REPORT");
            title.setText("Water Purity Report #" + Integer.toString(wpReport.getReportNumber()));
            reportNumber.setText(Integer.toString(wpReport.getReportNumber()));
            reportDate.setText(wpReport.getDate().toString());
            reportLocation.setText(wpReport.getLocation());
            reportWaterConditionLabel.setText("Overall Condition");
            reportWaterCondition.setText(wpReport.getOverallCondition().toString());
            reportWaterTypeLabel.setText("Virus PPM");
            reportWaterType.setText(Integer.toString(wpReport.getVirusPPM()));
            reportContaminantPPM.setVisibility(View.VISIBLE);
            reportContaminantPPMLabel.setVisibility(View.VISIBLE);
            reportContaminantPPM.setText(Integer.toString(wpReport.getContaminantPPM()));
            reportAuthor.setText(user.getName());
        }


        toolbar = (Toolbar) findViewById(R.id.view_report_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportDetailsActivity.this, ViewWaterSourcesActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);

            }
        });
    }
}
