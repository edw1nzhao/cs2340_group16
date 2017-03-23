package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

public class HistoricalReportActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView graphInfo;
    private User user;
    private String selectedPPM, latitude, longitude;
    private int selectedYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report);
        user = (User) getIntent().getSerializableExtra("USER");
        latitude = getIntent().getExtras().getString("LATITUDE");
        longitude = getIntent().getExtras().getString("LONGITUDE");
        selectedPPM = getIntent().getExtras().getString("PPM");
        selectedYear = Integer.parseInt(getIntent().getExtras().getString("YEAR"));

        toolbar = (Toolbar) findViewById(R.id.graph_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoricalReportActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        graphInfo = (TextView) findViewById(R.id.textview_graph_info);
        graphInfo.setText("\n\nLocation: " + latitude + "," + longitude
                + "\n\nSelected PPM: " + selectedPPM
                + "\n\nYear: " + selectedYear);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 25),
                new DataPoint(2, 10),
                new DataPoint(3, 5),
                new DataPoint(4, 13),
                new DataPoint(5, 6),
                new DataPoint(6, 6),
                new DataPoint(7, 10),
                new DataPoint(8, 3),
                new DataPoint(9, 1),
                new DataPoint(10, 8),
                new DataPoint(11, 11),
                new DataPoint(12, 20)
        });
        graph.addSeries(series);
    }

}
