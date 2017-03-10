package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class ViewWaterSourcesActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private ListView listView ;
    private User user;
    private String[] values;
    private Toolbar toolbar;
    private BottomNavigationView bottomNav;
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
        setContentView(R.layout.activity_view_water_sources);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.report_list);
        bottomNav = (BottomNavigationView) findViewById(R.id.view_ws_bottom_navbar);

        user = (User) getIntent().getSerializableExtra("USER");

        final List<WaterSourceReport> reportList = user.getWaterSourceReport();

        List<String> reportNums = new ArrayList<String>();

        for (WaterSourceReport item: reportList) {

            reportNums.add("Report Number: " + item.getReportNumber());

        }


        //Sets toolbar functionality on top of activity
        toolbar = (Toolbar) findViewById(R.id.view_ws_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewWaterSourcesActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });



        // Defined Array values to show in ListView
        try {
            values = new String[] {reportList.toString()};
        } catch (NullPointerException e) {
            values = new String[] {};
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, reportNums);
        listView.setAdapter(adapter);

        final List<String> finalReportNums = reportNums;
        //Sets BottomNavigationView functionality
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_all_reports:
                                //TO DO iterate through users and collect all reports; right now identical to MyReports tab

                                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(ViewWaterSourcesActivity.this,
                                        android.R.layout.simple_list_item_1, android.R.id.text1, finalReportNums);
                                listView.setAdapter(newAdapter);
                                Toast.makeText(getApplicationContext(),
                                        "Currently displaying all reports." , Toast.LENGTH_SHORT)
                                        .show();

                                break;
                            case R.id.action_my_reports:
                                ArrayAdapter<String> newAdapter2 = new ArrayAdapter<>(ViewWaterSourcesActivity.this,
                                        android.R.layout.simple_list_item_1, android.R.id.text1, finalReportNums);
                                listView.setAdapter(newAdapter2);
                                Toast.makeText(getApplicationContext(),
                                        "Currently displaying only your reports." , Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }

                });

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WaterSourceReport clickedItem = reportList.get(position);
                //int itemPosition = position;

                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Submitted By: " + clickedItem.getSubmittedBy()
                        + "\n\nDate: " + clickedItem.getDate()
                        + "\n\nLocation: " + clickedItem.getLocation()
                        + "\n\nWater Type: " + clickedItem.getWaterType()
                        + "\n\nWater Condition: " + clickedItem.getWaterCondition() + "\n\n" , Toast.LENGTH_LONG)
                        .show();

//                switch(position) {
//                    case 0:
//                        Intent newActivity1 = new Intent(ViewWaterSourcesActivity.this, ReportDetailsActivity.class);
//                        newActivity1.putExtra("USER", user);
//                        newActivity1.putExtra("POSITION", position);
//                        startActivity(newActivity1);
//                        break;
//                    default:
//                        Intent newActivity2 = new Intent(ViewWaterSourcesActivity.this, ReportDetailsActivity.class);
//                        newActivity2.putExtra("USER", user);
//                        newActivity2.putExtra("POSITION", position);
//                        startActivity(newActivity2);
//                        break;
//                }
            }
        });
    }
}