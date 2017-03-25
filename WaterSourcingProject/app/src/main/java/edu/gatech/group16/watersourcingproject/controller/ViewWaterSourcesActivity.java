package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class ViewWaterSourcesActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private ListView listView ;
    private User user;
    private Spinner viewingOptionSpinner;
    private BottomNavigationView bottomNav;
    private List<String> reportOptions = new ArrayList<>();
    private Toolbar toolbar;
    private String TAG = "ViewWaterSources";
    private List<WaterSourceReport> sourceReportList;
    private List<WaterPurityReport> purityReportList;
    private List<String> sourceReportTitles;
    private List<String> purityReportTitles;
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
        user = (User) getIntent().getSerializableExtra("USER");
        reportOptions.add("Water Source Reports");
        reportOptions.add("Water Purity Reports");

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.report_list);
        bottomNav = (BottomNavigationView) findViewById(R.id.view_ws_bottom_navbar);
        viewingOptionSpinner = (Spinner) findViewById(R.id.spinner_report_options);

        //Sets Spinner in for report viewing options
        ArrayAdapter<String> adaptReportOptions = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.reportOptions);
        viewingOptionSpinner.setAdapter(adaptReportOptions);

        //Hides "Historical Report" tab if the accountType is not MANAGER
        if (user.getAccountType() != AccountType.MANAGER) {
            bottomNav.getMenu().findItem(R.id.action_empty).setTitle("");
            bottomNav.getMenu().findItem(R.id.action_empty).setIcon(null);
        }

        //Collecting WaterSourceReports for the user
        try {
            sourceReportList = user.getWaterSourceReport();
            sourceReportTitles = new ArrayList<String>();

            for (WaterSourceReport item: sourceReportList) {
                sourceReportTitles.add("Report Number: " + item.getReportNumber());

            }
        } catch (NullPointerException e){
            Log.d(TAG, "No Water Source Reports");
        }

        //Collecting WaterPurityReports for the user
        try {
            purityReportList = user.getWaterPurityReport();
            purityReportTitles = new ArrayList<String>();
            for (WaterPurityReport item: purityReportList) {
                purityReportTitles.add("Report Number: " + item.getReportNumber());
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "No Water Purity Reports");
        }


        //Sets toolbar functionality on top of activity
        toolbar = (Toolbar) findViewById(R.id.view_ws_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewWaterSourcesActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sourceReportTitles);
        listView.setAdapter(adapter);

        viewingOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (viewingOptionSpinner.getSelectedItem().toString() == "Water Source Reports") {
//                    ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sourceReportTitles);
//                    listView.setAdapter(sourceAdapter);
                    ArrayAdapter<String> sourceAdapter = new ArrayAdapter<String>(ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, sourceReportTitles) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                            text1.setText(sourceReportTitles.get(position));
                            text2.setText("Water Source Report");
                            return view;
                        }
                    };
                    listView.setAdapter(sourceAdapter);
                } else if (viewingOptionSpinner.getSelectedItem().toString() == "Water Purity Reports") {
//                    ArrayAdapter<String> purityAdapter = new ArrayAdapter<>(ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, purityReportTitles);
//                    listView.setAdapter(purityAdapter);
                    ArrayAdapter<String> purityAdapter = new ArrayAdapter<String>(ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, purityReportTitles) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                            text1.setText(purityReportTitles.get(position));
                            text2.setText("Water Purity Report");
                            return view;
                        }
                    };
                    listView.setAdapter(purityAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        //Sets BottomNavigationView functionality
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_all_reports:
                                viewingOptionSpinner.setSelection(0);
                                ArrayAdapter<String> sourceAdapter1 = new ArrayAdapter<String>(ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, sourceReportTitles) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                        text1.setText(sourceReportTitles.get(position));
                                        text2.setText("Water Source Report");
                                        return view;
                                    }
                                };
                                listView.setAdapter(sourceAdapter1);
                                Toast.makeText(getApplicationContext(),
                                        "Currently displaying all reports." , Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case R.id.action_empty:
                                Intent intent = new Intent(ViewWaterSourcesActivity.this, HistoricalReportParametersActivity.class);
                                intent.putExtra("USER", user);
                                startActivity(intent);
                                break;
                            case R.id.action_my_reports:
                                viewingOptionSpinner.setSelection(0);
                                ArrayAdapter<String> sourceAdapter2 = new ArrayAdapter<String>(ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, sourceReportTitles) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                        text1.setText(sourceReportTitles.get(position));
                                        text2.setText("Water Source Report");
                                        return view;
                                    }
                                };
                                listView.setAdapter(sourceAdapter2);
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
                if (viewingOptionSpinner.getSelectedItem().toString() == "Water Source Reports") {
                    WaterSourceReport clickedItem = sourceReportList.get(position);
                    Toast.makeText(getApplicationContext(),
                            "Submitted By: " + clickedItem.getSubmittedBy()
                                    + "\n\nDate: " + clickedItem.getDate()
                                    + "\n\nLocation: " + clickedItem.getLocation()
                                    + "\n\nWater Type: " + clickedItem.getWaterType()
                                    + "\n\nWater Condition: " + clickedItem.getWaterCondition() + "\n\n" , Toast.LENGTH_SHORT)
                            .show();
                } else if (viewingOptionSpinner.getSelectedItem().toString() == "Water Purity Reports") {
                    WaterPurityReport clickedItem = purityReportList.get(position);
                    Toast.makeText(getApplicationContext(),
                            "Submitted By: " + clickedItem.getSubmittedBy()
                                    + "\n\nDate: " + clickedItem.getDate()
                                    + "\n\nLocation: " + clickedItem.getLocation()
                                    + "\n\nOverall Condition: " + clickedItem.getOverallCondition()
                                    + "\n\nVirus PPM: " + clickedItem.getVirusPPM()
                                    + "\n\nContaminant PPM: " + clickedItem.getContaminantPPM() + "\n\n" , Toast.LENGTH_SHORT)
                            .show();
                }

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