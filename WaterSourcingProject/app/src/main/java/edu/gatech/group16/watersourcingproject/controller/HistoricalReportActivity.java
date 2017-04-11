package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.OverallCondition;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;

@SuppressWarnings({"unused", "CyclicClassDependency", "JavaDoc"})
public class HistoricalReportActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private TextView graphInfo;
    private User user;

    @SuppressWarnings({"FeatureEnvy", "OverlyComplexMethod", "OverlyLongMethod"})
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report);

        //Toolbar initialisation
        Toolbar toolbar = (Toolbar) findViewById(R.id.graph_toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions,ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        HistoricalReportActivity.this, HistoricalReportParametersActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });


        ////////////////////////////////TEST ONE////////////////////
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Collection<WaterPurityReport> testList = new ArrayList<>();
        //noinspection deprecation,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 0, 5), "45,45", OverallCondition.SAFE, "Tomonari", 10, 10));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 0, 10), "45,45", OverallCondition.SAFE, "Tomonari", 30, 30));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 1, 5), "45,45", OverallCondition.SAFE, "Tomonari", 50, 50));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 1, 10), "45,45", OverallCondition.SAFE, "Tomonari", 70, 70));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 2, 5), "45,45", OverallCondition.SAFE, "Tomonari", 90, 90));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 2, 10), "45,45", OverallCondition.SAFE, "Tomonari", 110, 110));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 3, 5), "45,45", OverallCondition.SAFE, "Tomonari", 130, 130));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 3, 10), "45,45", OverallCondition.SAFE, "Tomonari", 150, 150));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 4, 5), "45,45", OverallCondition.SAFE, "Tomonari", 170, 170));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 4, 10), "45,45", OverallCondition.SAFE, "Tomonari", 190, 190));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 5, 5), "45,45", OverallCondition.SAFE, "Tomonari", 210, 210));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 5, 10), "45,45", OverallCondition.SAFE, "Tomonari", 230, 230));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 6, 5), "45,45", OverallCondition.SAFE, "Tomonari", 250, 250));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 6, 10), "45,45", OverallCondition.SAFE, "Tomonari", 270, 270));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 7, 5), "45,45", OverallCondition.SAFE, "Tomonari", 290, 290));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 7, 10), "45,45", OverallCondition.SAFE, "Tomonari", 310, 310));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 8, 5), "45,45", OverallCondition.SAFE, "Tomonari", 330, 330));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 8, 10), "45,45", OverallCondition.SAFE, "Tomonari", 350, 350));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 9, 5), "45,45", OverallCondition.SAFE, "Tomonari", 370, 370));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 9, 10), "45,45", OverallCondition.SAFE, "Tomonari", 390, 390));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 10, 5), "45,45", OverallCondition.SAFE, "Tomonari", 410, 410));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 10, 10), "45,45", OverallCondition.SAFE, "Tomonari", 430, 430));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 11, 5), "45,45", OverallCondition.SAFE, "Tomonari", 450, 450));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", 470, 470));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber,MagicNumber
        testList.add(new WaterPurityReport(
                1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE, "Tomonari", 470, 470));
        /////////////////////////////////////////////////////
        /////////////////////////////////////////////////////

        ////////////////////////////////TEST TWO/////////////
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Collection<WaterPurityReport> testList2 = new ArrayList<>();
        Random randypants = new Random();
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 0, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 0, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 1, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 1, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 2, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 2, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 3, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 3, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 4, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 4, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 5, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 5, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 6, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 6, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 7, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 7, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 8, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 8, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 9, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 9, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 10, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 10, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 11, 5), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        //noinspection deprecation,MagicNumber,MagicNumber,MagicNumber,MagicNumber
        testList2.add(new WaterPurityReport(
                1, new Date(117, 11, 10), "45,45", OverallCondition.SAFE,
                "Tomonari", randypants.nextInt(500), randypants.nextInt(500)));
        ////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////

        //Print the lists of test PPM values for reference

        //Graph parameters chose by the MANAGER
        //noinspection ChainedMethodCall
        user = (User) getIntent().getSerializableExtra("USER");
        @SuppressWarnings("ChainedMethodCall") String latitude
                = getIntent().getExtras().getString("LATITUDE");
        @SuppressWarnings("ChainedMethodCall") String longitude
                = getIntent().getExtras().getString("LONGITUDE");
        @SuppressWarnings("ChainedMethodCall") String selectedPPM
                = getIntent().getExtras().getString("PPM");
        @SuppressWarnings("ChainedMethodCall") int selectedYear
                = Integer.parseInt(getIntent().getExtras().getString("YEAR"));
        String selectedLocation = latitude + "," + longitude;

        //Sets graph x-axis and y-axis labels
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Month");
        gridLabel.setVerticalAxisTitle("PPM");
        //noinspection MagicNumber,ChainedMethodCall
        graphView.getViewport().setMaxX(13);
        //noinspection ChainedMethodCall
        graphView.getViewport().setXAxisBoundsManual(true);
        gridLabel.setVerticalAxisTitle(selectedPPM);

        //Month PPM Textviews
        TextView janText = (TextView) findViewById(R.id.january);
        TextView febText = (TextView) findViewById(R.id.february);
        TextView marText = (TextView) findViewById(R.id.march);
        TextView aprText = (TextView) findViewById(R.id.april);
        TextView mayText = (TextView) findViewById(R.id.may);
        TextView junText = (TextView) findViewById(R.id.june);
        TextView julText = (TextView) findViewById(R.id.july);
        TextView augText = (TextView) findViewById(R.id.august);
        TextView sepText = (TextView) findViewById(R.id.september);
        TextView octText = (TextView) findViewById(R.id.october);
        TextView novText = (TextView) findViewById(R.id.november);
        TextView decText = (TextView) findViewById(R.id.december);



        //PM arraylist initialization
        List<Integer> january = new ArrayList<>();
        List<Integer> february = new ArrayList<>();
        List<Integer> march = new ArrayList<>();
        List<Integer> april = new ArrayList<>();
        List<Integer> may = new ArrayList<>();
        List<Integer> june = new ArrayList<>();
        List<Integer> july = new ArrayList<>();
        List<Integer> august = new ArrayList<>();
        List<Integer> september = new ArrayList<>();
        List<Integer> october = new ArrayList<>();
        List<Integer> november = new ArrayList<>();
        List<Integer> december = new ArrayList<>();
        Collection<Integer> allMonths = new ArrayList<>();

        //Storing ppm values for each month
        //noinspection StringEquality
        if (selectedPPM == "Virus PPM") {
            for (WaterPurityReport item: user.getWaterPurityReport()) {
                //////////////////////////////////////////////////////////////////////////////
                //Note: To test change the line above to one of the options below:
                //- for (WaterPurityReport item: testList))
                //- for (WaterPurityReport item: testList2))
                //- for (WaterPurityReport item: user.getWaterPurityReport()))
                //////////////////////////////////////////////////////////////////////////////


                //noinspection deprecation,ChainedMethodCall
                if ((selectedYear == convertYearToCorrectFormat(item.getDate().getYear()))
                        && selectedLocation.equals("" + item.getLocation())) {
                    //noinspection deprecation,ChainedMethodCall
                    if (item.getDate().getMonth() == 0) {
                        january.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                        if (item.getDate().getMonth() == 1) {
                        february.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 2) {
                        march.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                        if (item.getDate().getMonth() == 3) {
                        april.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 4) {
                        may.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 5) {
                        june.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 6) {
                        july.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                if (item.getDate().getMonth() == 7) {
                        august.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 8) {
                        september.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                if (item.getDate().getMonth() == 9) {
                        october.add(item.getVirusPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                    if (item.getDate().getMonth() == 10) {
                        november.add(item.getVirusPPM());
                    } else //noinspection deprecation,MagicNumber,ChainedMethodCall
                                        if (item.getDate().getMonth() == 11) {
                        december.add(item.getVirusPPM());
                    }
                }
            }

        } else {
            for (WaterPurityReport item: user.getWaterPurityReport()) {
                //noinspection deprecation,ChainedMethodCall
                if ((selectedYear == convertYearToCorrectFormat(item.getDate().getYear()))
                        && selectedLocation.equals("" + item.getLocation())) {
                    ////////////////////////////////////////////////
                    //Note: To test change the line above to one of the options below:
                    //- for (WaterPurityReport item: testList))
                    //- for (WaterPurityReport item: testList2))
                    //- for (WaterPurityReport item: user.getWaterPurityReport()))
                    /////////////////////////////////////////
                    //noinspection deprecation,ChainedMethodCall
                    if (item.getDate().getMonth() == 0) {
                        january.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                        if (item.getDate().getMonth() == 1) {
                        february.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 2) {
                        march.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 3) {
                        april.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                if (item.getDate().getMonth() == 4) {
                        may.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                        if (item.getDate().getMonth() == 5) {
                        june.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                        if (item.getDate().getMonth() == 6) {
                        july.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                            if (item.getDate().getMonth() == 7) {
                        august.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                if (item.getDate().getMonth() == 8) {
                        september.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                if (item.getDate().getMonth() == 9) {
                        october.add(item.getContaminantPPM());
                    } else //noinspection deprecation,ChainedMethodCall
                                    if (item.getDate().getMonth() == 10) {
                        november.add(item.getContaminantPPM());
                    } else //noinspection deprecation,MagicNumber,ChainedMethodCall
                                    if (item.getDate().getMonth() == 11) {
                        december.add(item.getContaminantPPM());
                    }
                }
            }
        }

        allMonths.addAll(january); allMonths.addAll(february); allMonths.addAll(march);
        allMonths.addAll(april); allMonths.addAll(june); allMonths.addAll(july);
        allMonths.addAll(august); allMonths.addAll(september); allMonths.addAll(october);
        allMonths.addAll(november); allMonths.addAll(december);

        try {
            //noinspection ChainedMethodCall
            graphView.getViewport().setMaxY(Math.ceil(Collections.max(allMonths)));
            //noinspection ChainedMethodCall
            graphView.getViewport().setYAxisBoundsManual(true);
        } catch (NoSuchElementException e){
            //noinspection MagicNumber,ChainedMethodCall
            graphView.getViewport().setMaxY(300);
            //noinspection ChainedMethodCall
            graphView.getViewport().setYAxisBoundsManual(true);
        }
        TextView graphParams = (TextView) findViewById(R.id.textview_parameters);
        graphParams.setText("\nLocation: " + latitude + "," + longitude);
        janText.setText("January: " + calculateAverage(january));
        febText.setText("February: " + calculateAverage(february));
        marText.setText("March: " + calculateAverage(march));
        aprText.setText("April: " + calculateAverage(april));
        mayText.setText("May: " + calculateAverage(may));
        junText.setText("June: " + calculateAverage(june));
        julText.setText("July: " + calculateAverage(july));
        augText.setText("August: " + calculateAverage(august));
        sepText.setText("September: " + calculateAverage(september));
        octText.setText("October: " + calculateAverage(october));
        novText.setText("November: " + calculateAverage(november));
        decText.setText("December: " + calculateAverage(december));

        // //Test average is correct
        // Log.d("JanuaryAvg ", "" + calculateAverage(january));
        // Log.d("FebruaryAvg ", "" + calculateAverage(february));
        // Log.d("MarchAvg ", "" + calculateAverage(march));
        // Log.d("AprilAvg ", "" + calculateAverage(april));
        // Log.d("MayAvg ", "" + calculateAverage(may));
        // Log.d("JuneAvg ", "" + calculateAverage(june));
        // Log.d("JulyAvg ", "" + calculateAverage(july));
        // Log.d("AugustAvg ", "" + calculateAverage(august));
        // Log.d("SeptemberAvg ", "" + calculateAverage(september));
        // Log.d("OctoberAvg ", "" + calculateAverage(october));
        // Log.d("NovemberAvg ", "" + calculateAverage(november));
        // Log.d("DecemberAvg ", "" + calculateAverage(december));

        // //Test ppm values are added correctly for each month
        // Log.d("January ", "" + january);
        // Log.d("February ", "" + february);
        // Log.d("March ", "" + march);
        // Log.d("April ", "" + april);
        // Log.d("May ", "" + may);
        // Log.d("June ", "" + june);
        // Log.d("July ", "" + july);
        // Log.d("August ", "" + august);
        // Log.d("September ", "" + september);
        // Log.d("October ", "" + october);
        // Log.d("November ", "" + november);
        // Log.d("December ", "" + december);
        // Log.d("All months ", "" + allMonths);

        @SuppressWarnings("MagicNumber") LineGraphSeries<DataPoint> series
                = new LineGraphSeries<>(new DataPoint[] {
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
        //noinspection MagicNumber
        series.setDataPointsRadius(12f);
        graphView.addSeries(series);
        graphView.setTitle("Monthly  " + selectedPPM + "  Averages in  " + selectedYear);
    }

    //Average PPM Calculator
    public static double calculateAverage(List<Integer> list) {
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
    private int convertYearToCorrectFormat(int year) {
        //noinspection MagicNumber
        return 1900 + year;
    }

}
