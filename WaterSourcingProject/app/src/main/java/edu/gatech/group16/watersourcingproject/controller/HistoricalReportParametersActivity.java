package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

@SuppressWarnings({"unused", "CyclicClassDependency", "JavaDoc"})
public class HistoricalReportParametersActivity
        extends AppCompatActivity implements View.OnClickListener{
    private User user;
    private Spinner ppmSpinner;
    private Spinner yearSpinner;
    private EditText latitude;
    private EditText longitude;
    private final List<String> ppmOptions = new ArrayList<>();
    private final List<Integer> yearOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report_parameters);
        //noinspection ChainedMethodCall
        user = (User) getIntent().getSerializableExtra("USER");
        //noinspection ChainedMethodCall
        findViewById(R.id.submit_button).setOnClickListener(this);
        latitude = (EditText) findViewById(R.id.text_latitude);
        longitude = (EditText) findViewById(R.id.text_longitude);
        ppmSpinner = (Spinner) findViewById(R.id.spinner_ppm);
        yearSpinner = (Spinner) findViewById(R.id.spinner_year);
        Toolbar toolbar = (Toolbar) findViewById(R.id.historical_report_toolbar);

        ppmOptions.add("Virus PPM");
        ppmOptions.add("Contaminant PPM");
        @SuppressWarnings("unchecked") SpinnerAdapter adaptPPM = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, ppmOptions);
        ppmSpinner.setAdapter(adaptPPM);

        for (int i = 0; i <= 10; i++) {
            @SuppressWarnings("ChainedMethodCall") int currentYear
                    = Calendar.getInstance().get(Calendar.YEAR);
            yearOptions.add(currentYear - i);
        }
        @SuppressWarnings("unchecked") SpinnerAdapter adaptYear = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, yearOptions);
        yearSpinner.setAdapter(adaptYear);

        //Sets toolbar functionality on top of activity
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions,ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        HistoricalReportParametersActivity.this, ViewWaterSourcesActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                HistoricalReportParametersActivity.this.finish();
            }
        });

    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submit_button) {
            @SuppressWarnings("ChainedMethodCall")
            String selectedLatitude = latitude.getText().toString();
            @SuppressWarnings("ChainedMethodCall") String selectedLongitude
                    = longitude.getText().toString();
            @SuppressWarnings("ChainedMethodCall")
            String selectedPPM = ppmSpinner.getSelectedItem().toString();
            @SuppressWarnings("ChainedMethodCall") String selectedYear
                    = yearSpinner.getSelectedItem().toString();
            Intent intent = new Intent(this, HistoricalReportActivity.class);
            intent.putExtra("USER", user);
            intent.putExtra("LATITUDE", selectedLatitude);
            intent.putExtra("LONGITUDE", selectedLongitude);
            intent.putExtra("PPM", selectedPPM);
            intent.putExtra("YEAR", selectedYear);
            startActivity(intent);
            HistoricalReportParametersActivity.this.finish();
        }
    }
}
