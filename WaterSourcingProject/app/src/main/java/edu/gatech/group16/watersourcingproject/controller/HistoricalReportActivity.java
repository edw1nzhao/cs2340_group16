package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.OverallCondition;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;

public class HistoricalReportActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView graphInfo;
    private GraphView graphView;
    private User user;
    private String selectedPPM, latitude, longitude, selectedLocation;
    private int selectedYear;
    private List<Integer> january, february, march, april, may, june, july, august, september, october, november, december;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report);

        //Toolbar initialisation
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

        ////////////////////////////////TESTING////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////TESTING////////////////////////////////////////////////////////////////////////////
        List<WaterPurityReport> testList = new ArrayList<WaterPurityReport>();
        testList.add(new WaterPurityReport(1, new Date(2016, 1, 5), "45,45", OverallCondition.SAFE, "Tomonari", 50, 50));
        testList.add(new WaterPurityReport(1, new Date(2016, 1, 10), "45,45", OverallCondition.SAFE, "Tomonari", 70, 70));
        testList.add(new WaterPurityReport(1, new Date(2016, 2, 5), "45,45", OverallCondition.SAFE, "Tomonari", 90, 90));
        testList.add(new WaterPurityReport(1, new Date(2016, 2, 10), "45,45", OverallCondition.SAFE, "Tomonari", 110, 110));
        testList.add(new WaterPurityReport(1, new Date(2016, 3, 5), "45,45", OverallCondition.SAFE, "Tomonari", 130, 130));
        testList.add(new WaterPurityReport(1, new Date(2016, 3, 10), "45,45", OverallCondition.SAFE, "Tomonari", 150, 150));
        testList.add(new WaterPurityReport(1, new Date(2016, 4, 5), "45,45", OverallCondition.SAFE, "Tomonari", 170, 170));
        testList.add(new WaterPurityReport(1, new Date(2016, 4, 10), "45,45", OverallCondition.SAFE, "Tomonari", 190, 190));
        testList.add(new WaterPurityReport(1, new Date(2016, 5, 5), "45,45", OverallCondition.SAFE, "Tomonari", 210, 210));
        testList.add(new WaterPurityReport(1, new Date(2016, 5, 10), "45,45", OverallCondition.SAFE, "Tomonari", 230, 230));
        testList.add(new WaterPurityReport(1, new Date(2016, 6, 5), "45,45", OverallCondition.SAFE, "Tomonari", 250, 250));
        testList.add(new WaterPurityReport(1, new Date(2016, 6, 10), "45,45", OverallCondition.SAFE, "Tomonari", 270, 270));
        testList.add(new WaterPurityReport(1, new Date(2016, 7, 5), "45,45", OverallCondition.SAFE, "Tomonari", 290, 290));
        testList.add(new WaterPurityReport(1, new Date(2016, 7, 10), "45,45", OverallCondition.SAFE, "Tomonari", 310, 310));
        testList.add(new WaterPurityReport(1, new Date(2016, 8, 5), "45,45", OverallCondition.SAFE, "Tomonari", 330, 330));
        testList.add(new WaterPurityReport(1, new Date(2016, 8, 10), "45,45", OverallCondition.SAFE, "Tomonari", 350, 350));
        testList.add(new WaterPurityReport(1, new Date(2016, 9, 5), "45,45", OverallCondition.SAFE, "Tomonari", 370, 370));
        testList.add(new WaterPurityReport(1, new Date(2016, 9, 10), "45,45", OverallCondition.SAFE, "Tomonari", 390, 390));
        testList.add(new WaterPurityReport(1, new Date(2016, 10, 5), "45,45", OverallCondition.SAFE, "Tomonari", 410, 410));
        testList.add(new WaterPurityReport(1, new Date(2016, 10, 10), "45,45", OverallCondition.SAFE, "Tomonari", 430, 430));
        testList.add(new WaterPurityReport(1, new Date(2016, 11, 5), "45,45", OverallCondition.SAFE, "Tomonari", 450, 450));
        testList.add(new WaterPurityReport(1, new Date(2016, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", 470, 470));
        testList.add(new WaterPurityReport(1, new Date(2016, 12, 5), "45,45", OverallCondition.SAFE, "Tomonari", 490, 490));
        testList.add(new WaterPurityReport(1, new Date(2016, 12, 10), "45,45", OverallCondition.SAFE, "Tomonari", 510, 510));
        ////////////////////////////////TESTING////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////TESTING////////////////////////////////////////////////////////////////////////////

        Log.d("Test List", "" + testList);
        //Graph parameters chose by the MANAGER
        user = (User) getIntent().getSerializableExtra("USER");
        latitude = getIntent().getExtras().getString("LATITUDE");
        longitude = getIntent().getExtras().getString("LONGITUDE");
        selectedPPM = getIntent().getExtras().getString("PPM");
        selectedYear = Integer.parseInt(getIntent().getExtras().getString("YEAR"));
        selectedLocation = latitude + "," + longitude;

        //Sets graph x-axis and y-axis labels
        graphView = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Month");
        gridLabel.setVerticalAxisTitle("PPM");
        graphView.getViewport().setMaxX(13);
        graphView.getViewport().setXAxisBoundsManual(true);
//        if (selectedPPM == "Virus PPM") {
//            gridLabel.setVerticalAxisTitle("Virus PPM");
//        } else if (selectedPPM == "Contaminant PPM"){
//            gridLabel.setVerticalAxisTitle("Contaminant PPM");
//        }

        //PM arraylist initialization
        january = new ArrayList<Integer>();
        february = new ArrayList<Integer>();
        march = new ArrayList<Integer>();
        april = new ArrayList<Integer>();
        may = new ArrayList<Integer>();
        june = new ArrayList<Integer>();
        july = new ArrayList<Integer>();
        august = new ArrayList<Integer>();
        september = new ArrayList<Integer>();
        october = new ArrayList<Integer>();
        november = new ArrayList<Integer>();
        december = new ArrayList<Integer>();

        //Storing ppm values for each month
        if (selectedPPM == "Virus PPM") {
            for (WaterPurityReport item: user.getWaterPurityReport()) {
                //////////////////////////////////////////////////////////////////////////////
                //Note: Change the line above to: (WaterPurityReport item: testList)) to test.
                //////////////////////////////////////////////////////////////////////////////
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, selectedYear);

                Calendar itemDate = Calendar.getInstance();
                itemDate.setTime(item.getDate());
                if (selectedDate.get(Calendar.YEAR) == itemDate.get(Calendar.YEAR)) {
                    switch(itemDate.get(Calendar.MONTH)) {
                        case Calendar.JANUARY:
                            january.add(item.getVirusPPM());
                        case Calendar.FEBRUARY:
                            february.add(item.getVirusPPM());
                        case Calendar.MARCH:
                            march.add(item.getVirusPPM());
                        case Calendar.APRIL:
                            april.add(item.getVirusPPM());
                        case Calendar.MAY:
                            may.add(item.getVirusPPM());
                        case Calendar.JUNE:
                            june.add(item.getVirusPPM());
                        case Calendar.JULY:
                            july.add(item.getVirusPPM());
                        case Calendar.AUGUST:
                            august.add(item.getVirusPPM());
                        case Calendar.SEPTEMBER:
                            september.add(item.getVirusPPM());
                        case Calendar.OCTOBER:
                            october.add(item.getVirusPPM());
                        case Calendar.NOVEMBER:
                            november.add(item.getVirusPPM());
                        case Calendar.DECEMBER:
                            december.add(item.getVirusPPM());
                    }
                }
            }

        } else {
            for (WaterPurityReport item: user.getWaterPurityReport()) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, selectedYear);

                Calendar itemDate = Calendar.getInstance();
                itemDate.setTime(item.getDate());
                if (selectedDate.get(Calendar.YEAR) == itemDate.get(Calendar.YEAR)) {
                    switch(itemDate.get(Calendar.MONTH)) {
                        case Calendar.JANUARY:
                            january.add(item.getContaminantPPM());
                        case Calendar.FEBRUARY:
                            february.add(item.getContaminantPPM());
                        case Calendar.MARCH:
                            march.add(item.getContaminantPPM());
                        case Calendar.APRIL:
                            april.add(item.getContaminantPPM());
                        case Calendar.MAY:
                            may.add(item.getContaminantPPM());
                        case Calendar.JUNE:
                            june.add(item.getContaminantPPM());
                        case Calendar.JULY:
                            july.add(item.getContaminantPPM());
                        case Calendar.AUGUST:
                            august.add(item.getContaminantPPM());
                        case Calendar.SEPTEMBER:
                            september.add(item.getContaminantPPM());
                        case Calendar.OCTOBER:
                            october.add(item.getContaminantPPM());
                        case Calendar.NOVEMBER:
                            november.add(item.getContaminantPPM());
                        case Calendar.DECEMBER:
                            december.add(item.getContaminantPPM());
                    }
                }
            }
        }
        graphInfo = (TextView) findViewById(R.id.textview_graph_info);
        graphInfo.setText("\n\nLocation: " + latitude + "," + longitude
                + "\n\nSelected PPM: " + selectedPPM
                + "\n\nYear: " + selectedYear);
        Log.d("January average: ", "" + calculateAverage(january));
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                //Uncomment datapoints to test arraylists above
//                new DataPoint(1, calculateAverage(january)),
//                new DataPoint(2, calculateAverage(february)),
//                new DataPoint(3, calculateAverage(march)),
//                new DataPoint(4, calculateAverage(april)),
//                new DataPoint(5, calculateAverage(may)),
//                new DataPoint(6, calculateAverage(june)),
//                new DataPoint(7, calculateAverage(july)),
//                new DataPoint(8, calculateAverage(august)),
//                new DataPoint(9, calculateAverage(september)),
//                new DataPoint(10, calculateAverage(october)),
//                new DataPoint(11, calculateAverage(november)),
//                new DataPoint(12, calculateAverage(december))

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

    //Average PPM Calculator
    private double calculateAverage(List<Integer> list) {
        double sum = 0;
        if (list != null) {
            for (Integer item : list) {
                sum += item;
            }
            return sum / list.size();
        }
        return sum;
    }

}
