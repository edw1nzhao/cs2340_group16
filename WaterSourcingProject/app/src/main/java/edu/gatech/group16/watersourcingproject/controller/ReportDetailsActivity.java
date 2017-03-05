package edu.gatech.group16.watersourcingproject.controller;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class ReportDetailsActivity extends AppCompatActivity {
    private User user;
    private TextView reportNumber;
    private TextView reportDate;
    private TextView reportLocation;
    private TextView reportWaterType;
    private TextView reportAuthor;
    private int position;
    private Toolbar toolbar;

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

        reportNumber = (TextView) findViewById(R.id.val_reportNum);
        reportDate = (TextView) findViewById(R.id.val_reportDate);
        reportLocation = (TextView) findViewById(R.id.val_reportLoc);
        reportWaterType = (TextView) findViewById(R.id.val_reportWT);
        reportAuthor = (TextView) findViewById(R.id.val_reportAuth);

        WaterSourceReport relevantReport = user.getWaterSourceReport().get(position - 1);
        reportDate.setText(relevantReport.getDate().toString());
        reportLocation.setText(relevantReport.getLocation().toString());
        reportWaterType.setText(relevantReport.getWaterType().toString());
        reportNumber.setText(position - 1);
        reportAuthor.setText(user.getName());
        toolbar = (Toolbar) findViewById(R.id.view_report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change to correct screen
                Intent intent = new Intent(ReportDetailsActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });
    }
}
