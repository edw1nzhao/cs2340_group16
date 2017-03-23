package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

public class HistoricalReportParametersActivity extends AppCompatActivity implements View.OnClickListener{
    private User user;
    private Spinner ppmSpinner, yearSpinner;
    private List<String> ppmOptions = new ArrayList<>();
    private List<Integer> yearOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report_parameters);
        user = (User) getIntent().getSerializableExtra("USER");
        findViewById(R.id.submit_button).setOnClickListener(this);

        ppmOptions.add("Virus PPM");
        ppmOptions.add("Contaminant PPM");
        ppmSpinner = (Spinner) findViewById(R.id.spinner_ppm);
        ArrayAdapter<String> adaptPPM = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ppmOptions);
        ppmSpinner.setAdapter(adaptPPM);

        for (int i = 0; i <= 10; i++) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            yearOptions.add(currentYear - i);
        }
        yearSpinner = (Spinner) findViewById(R.id.spinner_year);
        ArrayAdapter<Integer> adaptYear = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearOptions);
        yearSpinner.setAdapter(adaptYear);

    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submit_button) {
            Intent intent = new Intent(this, HistoricalReportActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        }
    }
}
