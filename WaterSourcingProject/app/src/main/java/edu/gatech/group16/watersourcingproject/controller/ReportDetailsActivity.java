package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

@SuppressWarnings({"unused", "CyclicClassDependency", "JavaDoc"})
public class ReportDetailsActivity extends AppCompatActivity {
    private User user;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @SuppressWarnings({"FeatureEnvy", "OverlyLongMethod"})
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        //noinspection ChainedMethodCall
        user = (User) getIntent().getSerializableExtra("USER");
        @SuppressWarnings({"UnusedAssignment", "ChainedMethodCall"}) int position
                = (int) getIntent().getSerializableExtra("POSITION");
        @SuppressWarnings("ChainedMethodCall") String reportType
                = (String) getIntent().getSerializableExtra("REPORT TYPE");
       

        //Shared UI
        TextView reportNumber = (TextView) findViewById(R.id.val_reportNum);
        TextView reportDate = (TextView) findViewById(R.id.val_reportDate);
        TextView reportLocation = (TextView) findViewById(R.id.val_reportLoc);
        TextView reportWaterConditionLabel = (TextView) findViewById(R.id.label_reportWC);
        TextView reportWaterCondition = (TextView) findViewById(R.id.val_reportWC);
        TextView reportWaterTypeLabel = (TextView) findViewById(R.id.label_reportWT);
        TextView reportWaterType = (TextView) findViewById(R.id.val_reportWT);
        TextView reportAuthor = (TextView) findViewById(R.id.val_reportAuth);
        TextView reportContaminantPPM = (TextView) findViewById(R.id.val_reportContaminantPPM);
        TextView reportContaminantPPMLabel
                = (TextView) findViewById(R.id.label_reportContaminantPPM);
        TextView title = (TextView) findViewById(R.id.label_report);

        if ("WaterSourceReport".equals(reportType)) {
            @SuppressWarnings("ChainedMethodCall") WaterSourceReport wsReport
                    = (WaterSourceReport) getIntent().getSerializableExtra("REPORT");
            title.setText("Water Source Report #" + Integer.toString(wsReport.getReportNumber()));
            reportNumber.setText(Integer.toString(wsReport.getReportNumber()));
            //noinspection ChainedMethodCall
            reportDate.setText(wsReport.getDate().toString());
            reportLocation.setText(wsReport.getLocation());
            //noinspection ChainedMethodCall
            reportWaterCondition.setText(wsReport.getWaterCondition().toString());
            //noinspection ChainedMethodCall
            reportWaterType.setText(wsReport.getWaterType().toString());
            reportAuthor.setText(user.getName());

        } else if ("WaterPurityReport".equals(reportType)){
            @SuppressWarnings("ChainedMethodCall") WaterPurityReport wpReport
                    = (WaterPurityReport) getIntent().getSerializableExtra("REPORT");
            title.setText("Water Purity Report #" + Integer.toString(wpReport.getReportNumber()));
            reportNumber.setText(Integer.toString(wpReport.getReportNumber()));
            //noinspection ChainedMethodCall
            reportDate.setText(wpReport.getDate().toString());
            reportLocation.setText(wpReport.getLocation());
            reportWaterConditionLabel.setText("Overall Condition");
            //noinspection ChainedMethodCall
            reportWaterCondition.setText(wpReport.getOverallCondition().toString());
            reportWaterTypeLabel.setText("Virus PPM");
            reportWaterType.setText(Integer.toString(wpReport.getVirusPPM()));
            reportContaminantPPM.setVisibility(View.VISIBLE);
            reportContaminantPPMLabel.setVisibility(View.VISIBLE);
            reportContaminantPPM.setText(Integer.toString(wpReport.getContaminantPPM()));
            reportAuthor.setText(user.getName());
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.view_report_toolbar);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions,ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(ReportDetailsActivity.this, ViewWaterSourcesActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                ReportDetailsActivity.this.finish();
            }
        });
    }
}
