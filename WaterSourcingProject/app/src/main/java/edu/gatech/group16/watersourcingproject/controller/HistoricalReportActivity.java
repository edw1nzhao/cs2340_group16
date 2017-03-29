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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.OverallCondition;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;

public class HistoricalReportActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView graphInfo, graphParams;
    private GraphView graphView;
    private User user;
    private String selectedPPM, latitude, longitude, selectedLocation;
    private int selectedYear;
    private List<Integer> january, february, march, april, may, june, july, august, september, october, november, december, allMonths;

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
                Intent intent = new Intent(HistoricalReportActivity.this, HistoricalReportParametersActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        ////////////////////////////////TEST ONE////////////////////////////////////////////////////////////////////////////
        List<WaterPurityReport> testList = new ArrayList<WaterPurityReport>();
        testList.add(new WaterPurityReport(1, new Date(117, 0, 5), "45,45", OverallCondition.SAFE, "Tomonari", 10, 10));
        testList.add(new WaterPurityReport(1, new Date(117, 0, 10), "45,45", OverallCondition.SAFE, "Tomonari", 30, 30));
        testList.add(new WaterPurityReport(1, new Date(117, 1, 5), "45,45", OverallCondition.SAFE, "Tomonari", 50, 50));
        testList.add(new WaterPurityReport(1, new Date(117, 1, 10), "45,45", OverallCondition.SAFE, "Tomonari", 70, 70));
        testList.add(new WaterPurityReport(1, new Date(117, 2, 5), "45,45", OverallCondition.SAFE, "Tomonari", 90, 90));
        testList.add(new WaterPurityReport(1, new Date(117, 2, 10), "45,45", OverallCondition.SAFE, "Tomonari", 110, 110));
        testList.add(new WaterPurityReport(1, new Date(117, 3, 5), "45,45", OverallCondition.SAFE, "Tomonari", 130, 130));
        testList.add(new WaterPurityReport(1, new Date(117, 3, 10), "45,45", OverallCondition.SAFE, "Tomonari", 150, 150));
        testList.add(new WaterPurityReport(1, new Date(117, 4, 5), "45,45", OverallCondition.SAFE, "Tomonari", 170, 170));
        testList.add(new WaterPurityReport(1, new Date(117, 4, 10), "45,45", OverallCondition.SAFE, "Tomonari", 190, 190));
        testList.add(new WaterPurityReport(1, new Date(117, 5, 5), "45,45", OverallCondition.SAFE, "Tomonari", 210, 210));
        testList.add(new WaterPurityReport(1, new Date(117, 5, 10), "45,45", OverallCondition.SAFE, "Tomonari", 230, 230));
        testList.add(new WaterPurityReport(1, new Date(117, 6, 5), "45,45", OverallCondition.SAFE, "Tomonari", 250, 250));
        testList.add(new WaterPurityReport(1, new Date(117, 6, 10), "45,45", OverallCondition.SAFE, "Tomonari", 270, 270));
        testList.add(new WaterPurityReport(1, new Date(117, 7, 5), "45,45", OverallCondition.SAFE, "Tomonari", 290, 290));
        testList.add(new WaterPurityReport(1, new Date(117, 7, 10), "45,45", OverallCondition.SAFE, "Tomonari", 310, 310));
        testList.add(new WaterPurityReport(1, new Date(117, 8, 5), "45,45", OverallCondition.SAFE, "Tomonari", 330, 330));
        testList.add(new WaterPurityReport(1, new Date(117, 8, 10), "45,45", OverallCondition.SAFE, "Tomonari", 350, 350));
        testList.add(new WaterPurityReport(1, new Date(117, 9, 5), "45,45", OverallCondition.SAFE, "Tomonari", 370, 370));
        testList.add(new WaterPurityReport(1, new Date(117, 9, 10), "45,45", OverallCondition.SAFE, "Tomonari", 390, 390));
        testList.add(new WaterPurityReport(1, new Date(117, 10, 5), "45,45", OverallCondition.SAFE, "Tomonari", 410, 410));
        testList.add(new WaterPurityReport(1, new Date(117, 10, 10), "45,45", OverallCondition.SAFE, "Tomonari", 430, 430));
        testList.add(new WaterPurityReport(1, new Date(117, 11, 5), "45,45", OverallCondition.SAFE, "Tomonari", 450, 450));
        testList.add(new WaterPurityReport(1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", 470, 470));
        testList.add(new WaterPurityReport(1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", 470, 470));
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////TEST TWO////////////////////////////////////////////////////////////////////////////
        List<WaterPurityReport> testList2 = new ArrayList<WaterPurityReport>();
        Random randypants = new Random();
        testList2.add(new WaterPurityReport(1, new Date(117, 0, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 0, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 1, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 1, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 2, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 2, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 3, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 3, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 4, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 4, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 5, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 5, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 6, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 6, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 7, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 7, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 8, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 8, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 9, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 9, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 10, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 10, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 11, 5), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        testList2.add(new WaterPurityReport(1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Print the lists of test PPM values for reference
        Log.d("TestList", "" + testList);
        Log.d("TestList2", "" + testList2);

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
        gridLabel.setVerticalAxisTitle(selectedPPM);

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
        allMonths = new ArrayList<Integer>();

        //Storing ppm values for each month
        if (selectedPPM == "Virus PPM") {
            for (WaterPurityReport item: user.getWaterPurityReport()) {
                //////////////////////////////////////////////////////////////////////////////
                //Note: To test change the line above to one of the options below:
                //- for (WaterPurityReport item: testList))
                //- for (WaterPurityReport item: testList2))
                //- for (WaterPurityReport item: user.getWaterPurityReport()))
                //////////////////////////////////////////////////////////////////////////////
                Log.d("BOOLEAN", "" + selectedLocation.equals(item.getLocation()));
                Log.d("Boolean2", "" + (selectedYear  == convertYearToCorrectFormat((item.getDate().getYear()))));
                Log.d("YEAR", "" + selectedYear);
                Log.d("RN", "" + item.getReportNumber());

                if (selectedYear == convertYearToCorrectFormat(item.getDate().getYear()) && selectedLocation.equals("" + item.getLocation())) {
                    if (item.getDate().getMonth() == 0) {
                        january.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 1) {
                        february.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 2) {
                        march.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 3) {
                        april.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 4) {
                        may.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 5) {
                        june.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 6) {
                        july.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 7) {
                        august.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 8) {
                        september.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 9) {
                        october.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 10) {
                        november.add(item.getVirusPPM());
                    } else if (item.getDate().getMonth() == 11) {
                        december.add(item.getVirusPPM());
                    }
                }
            }

        } else {
            for (WaterPurityReport item: user.getWaterPurityReport()) {
                Log.d("BOOLEAN", "" + selectedLocation.equals(item.getLocation()));
                Log.d("Boolean2", "" + (selectedYear  == convertYearToCorrectFormat(item.getDate().getYear())));
                Log.d("YEAR", "" + selectedYear);
                Log.d("RN", "" + item.getReportNumber());
                if (selectedYear == convertYearToCorrectFormat(item.getDate().getYear()) && selectedLocation.equals("" + item.getLocation())) {
                    //////////////////////////////////////////////////////////////////////////////
                    //Note: To test change the line above to one of the options below:
                    //- for (WaterPurityReport item: testList))
                    //- for (WaterPurityReport item: testList2))
                    //- for (WaterPurityReport item: user.getWaterPurityReport()))
                    //////////////////////////////////////////////////////////////////////////////
                    if (item.getDate().getMonth() == 0) {
                        january.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 1) {
                        february.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 2) {
                        march.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 3) {
                        april.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 4) {
                        may.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 5) {
                        june.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 6) {
                        july.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 7) {
                        august.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 8) {
                        september.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 9) {
                        october.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 10) {
                        november.add(item.getContaminantPPM());
                    } else if (item.getDate().getMonth() == 11) {
                        december.add(item.getContaminantPPM());
                    }
                }
            }
        }

        allMonths.addAll(january); allMonths.addAll(february); allMonths.addAll(march);
        allMonths.addAll(april); allMonths.addAll(june); allMonths.addAll(july);
        allMonths.addAll(august); allMonths.addAll(september); allMonths.addAll(october);
        allMonths.addAll(november); allMonths.addAll(december);

        graphInfo = (TextView) findViewById(R.id.textview_graph_info);
        try {
            graphView.getViewport().setMaxY(Math.ceil(Collections.max(allMonths)));
            graphView.getViewport().setYAxisBoundsManual(true);
        } catch (NoSuchElementException e){
            graphView.getViewport().setMaxY(300);
            graphView.getViewport().setYAxisBoundsManual(true);
        }
        graphParams = (TextView) findViewById(R.id.textview_parameters);
        graphParams.setText("\nLocation: " + latitude + "," + longitude);
        graphInfo.setText("\nJanuary: " + calculateAverage(january)
                + "\t\t\t\t\t\t\tJuly: " + calculateAverage(july)
                + "\n\t\t\tFebruary: " + calculateAverage(february)
                + "\t\t\t\t\t\t\tAugust: " + calculateAverage(july)
                + "\n\t\t\t\tMarch: " + calculateAverage(march)
                + "\t\t\t\t\t\t\t\tSeptember: " + calculateAverage(september)
                + "\nApril: " + calculateAverage(april)
                + "\t\t\t\t\t\t\t\t\tOctober: " + calculateAverage(october)
                + "\n\t\t\tMay: " + calculateAverage(may)
                + "\t\t\t\t\t\t\t\t\tNovember: " + calculateAverage(november)
                + "\n\t\t\tJune: " + calculateAverage(june)
                + "\t\t\t\t\t\t\t\t\tDecember: " + calculateAverage(december));

        //Test average is correct
        Log.d("JanuaryAvg ", "" + calculateAverage(january));
        Log.d("FebruaryAvg ", "" + calculateAverage(february));
        Log.d("MarchAvg ", "" + calculateAverage(march));
        Log.d("AprilAvg ", "" + calculateAverage(april));
        Log.d("MayAvg ", "" + calculateAverage(may));
        Log.d("JuneAvg ", "" + calculateAverage(june));
        Log.d("JulyAvg ", "" + calculateAverage(july));
        Log.d("AugustAvg ", "" + calculateAverage(august));
        Log.d("SeptemberAvg ", "" + calculateAverage(september));
        Log.d("OctoberAvg ", "" + calculateAverage(october));
        Log.d("NovemberAvg ", "" + calculateAverage(november));
        Log.d("DecemberAvg ", "" + calculateAverage(december));

        //Test ppm values are added correctly for each month
        Log.d("January ", "" + january);
        Log.d("February ", "" + february);
        Log.d("March ", "" + march);
        Log.d("April ", "" + april);
        Log.d("May ", "" + may);
        Log.d("June ", "" + june);
        Log.d("July ", "" + july);
        Log.d("August ", "" + august);
        Log.d("September ", "" + september);
        Log.d("October ", "" + october);
        Log.d("November ", "" + november);
        Log.d("December ", "" + december);
        Log.d("All months ", "" + allMonths);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, calculateAverage(january)),
                new DataPoint(2, calculateAverage(february)),
                new DataPoint(3, calculateAverage(march)),
                new DataPoint(4, calculateAverage(april)),
                new DataPoint(5, calculateAverage(may)),
                new DataPoint(6, calculateAverage(june)),
                new DataPoint(7, calculateAverage(july)),
                new DataPoint(8, calculateAverage(august)),
                new DataPoint(9, calculateAverage(september)),
                new DataPoint(10, calculateAverage(october)),
                new DataPoint(11, calculateAverage(november)),
                new DataPoint(12, calculateAverage(december))
        });
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(12f);
        graphView.addSeries(series);
        graphView.setTitle("Monthly  " + selectedPPM + "  Averages in  " + selectedYear);
    }

    //Average PPM Calculator
    private double calculateAverage(List<Integer> list) {
        double sum = 0;
        if (list != null) {
            if (list.size() == 1) {
                return list.get(0);
            }
            for (Integer item : list) {
                sum += item;
            }
            return Math.round(sum / list.size());
        }
        return sum;
    }
    public int convertYearToCorrectFormat(int year) {
        return 1900 + year;
    }

}
