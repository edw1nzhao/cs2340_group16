package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

@SuppressWarnings({"unused", "CyclicClassDependency", "JavaDoc"})
public class ViewWaterSourcesActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private final List<String> list = new ArrayList<>();

    private ListView listView ;
    private User user;
    private Spinner viewingOptionSpinner;
    private final List<String> reportOptions = new ArrayList<>();

    private List<WaterSourceReport> sourceReportList;
    private List<WaterPurityReport> purityReportList;

    private List<String> sourceReportTitles;
    private List<String> purityReportTitles;

    private BottomNavigationView bottomNav;
    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @SuppressWarnings({"FeatureEnvy", "OverlyLongMethod"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_water_sources);
        //noinspection ChainedMethodCall
        user = (User) getIntent().getSerializableExtra("USER");

        uiSetup();
        collectReports();

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, sourceReportTitles);
        listView.setAdapter(adapter);

        viewingOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //noinspection StringEquality,ChainedMethodCall
                if (viewingOptionSpinner.getSelectedItem().toString() == "Water Source Reports") {
                    @SuppressWarnings("NullableProblems") ListAdapter sourceAdapter
                            = new ArrayAdapter<String>(ViewWaterSourcesActivity.this,
                            android.R.layout.simple_list_item_2,
                            android.R.id.text1, sourceReportTitles) {
                        @NonNull
                        @SuppressLint("SetTextI18n")
                        @Override
                        public View getView(
                                int position, View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                            text1.setText(sourceReportTitles.get(position));
                            text2.setText("Water Source Report");
                            return view;
                        }
                    };
                    listView.setAdapter(sourceAdapter);
                } else //noinspection StringEquality,ChainedMethodCall
                    if (viewingOptionSpinner.getSelectedItem().toString()
                            == "Water Purity Reports") {
                    @SuppressWarnings("NullableProblems") ListAdapter purityAdapter
                            = new ArrayAdapter<String>(ViewWaterSourcesActivity.this,
                            android.R.layout.simple_list_item_2, android.R.id.text1,
                            purityReportTitles) {
                        @NonNull
                        @SuppressLint("SetTextI18n")
                        @Override
                        public View getView(
                                int position, View convertView, @NonNull ViewGroup parent) {
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



        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
                //noinspection StringEquality,ChainedMethodCall
                if (viewingOptionSpinner.getSelectedItem().toString() == "Water Source Reports") {
                    WaterSourceReport clickedItem = sourceReportList.get(position);

                    Intent newActivity = new Intent(
                            ViewWaterSourcesActivity.this, ReportDetailsActivity.class);
                    newActivity.putExtra("USER", user);
                    newActivity.putExtra("POSITION", position);
                    newActivity.putExtra("REPORT", clickedItem);
                    newActivity.putExtra("REPORT TYPE", "WaterSourceReport");
                    startActivity(newActivity);
                    ViewWaterSourcesActivity.this.finish();
                } else //noinspection StringEquality,ChainedMethodCall
                    if (viewingOptionSpinner.getSelectedItem().toString()
                            == "Water Purity Reports") {
                    WaterPurityReport clickedItem = purityReportList.get(position);

                    Intent newActivity2 = new Intent(
                            ViewWaterSourcesActivity.this, ReportDetailsActivity.class);
                    newActivity2.putExtra("USER", user);
                    newActivity2.putExtra("POSITION", position);
                    newActivity2.putExtra("REPORT", clickedItem);
                    newActivity2.putExtra("REPORT TYPE", "WaterPurityReport");
                    startActivity(newActivity2);
                    ViewWaterSourcesActivity.this.finish();
                }
            }
        });
    }

    private void uiSetup() {
        reportOptions.add("Water Source Reports");
        reportOptions.add("Water Purity Reports");

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.report_list);
        bottomNav = (BottomNavigationView) findViewById(R.id.view_ws_bottom_navbar);
        viewingOptionSpinner = (Spinner) findViewById(R.id.spinner_report_options);

        //Sets Spinner in for report viewing options
        @SuppressWarnings("unchecked") SpinnerAdapter adaptReportOptions
                = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, this.reportOptions);
        viewingOptionSpinner.setAdapter(adaptReportOptions);

        //Hides "Historical Report" tab if the accountType is not MANAGER
        if (user.getAccountType() != AccountType.MANAGER) {
            //noinspection ChainedMethodCall,ChainedMethodCall
            bottomNav.getMenu().findItem(R.id.action_empty).setTitle("");
            //noinspection ChainedMethodCall,ChainedMethodCall
            bottomNav.getMenu().findItem(R.id.action_empty).setIcon(null);
        }

        //Sets toolbar functionality on top of activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_ws_toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions,ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewWaterSourcesActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                ViewWaterSourcesActivity.this.finish();
            }
        });
    }

    private void collectReports() {
        //Collecting WaterSourceReports for the user
        //noinspection ProhibitedExceptionCaught
        String TAG = "ViewWaterSources";
        //noinspection ProhibitedExceptionCaught
        try {
            sourceReportList = user.getWaterSourceReport();
            sourceReportTitles = new ArrayList<>();

            for (WaterSourceReport item: sourceReportList) {
                sourceReportTitles.add("Report Number: " + item.getReportNumber());

            }
        } catch (NullPointerException e){
            Log.d(TAG, "No Water Source Reports");
        }

        //Collecting WaterPurityReports for the user
        //noinspection ProhibitedExceptionCaught
        try {
            purityReportList = user.getWaterPurityReport();
            purityReportTitles = new ArrayList<>();
            for (WaterPurityReport item: purityReportList) {
                purityReportTitles.add("Report Number: " + item.getReportNumber());
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "No Water Purity Reports");
        }

    }

    private void bottomNav() {
        //Sets BottomNavigationView functionality
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_all_reports:
                                viewingOptionSpinner.setSelection(0);
                                @SuppressWarnings("NullableProblems") ListAdapter sourceAdapter1
                                        = new ArrayAdapter<String>(ViewWaterSourcesActivity.this,
                                        android.R.layout.simple_list_item_2, android.R.id.text1,
                                        sourceReportTitles) {
                                    @NonNull
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public View getView(int position, View convertView,
                                                        @NonNull ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text1
                                                = (TextView) view.findViewById(android.R.id.text1);
                                        TextView text2
                                                = (TextView) view.findViewById(android.R.id.text2);
                                        text1.setText(sourceReportTitles.get(position));
                                        text2.setText("Water Source Report");
                                        return view;
                                    }
                                };
                                listView.setAdapter(sourceAdapter1);
                                //noinspection ChainedMethodCall
                                Toast.makeText(getApplicationContext(),
                                        "Currently displaying all reports." , Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case R.id.action_empty:
                                Intent intent = new Intent(
                                        ViewWaterSourcesActivity.this,
                                        HistoricalReportParametersActivity.class);
                                intent.putExtra("USER", user);
                                startActivity(intent);
                                ViewWaterSourcesActivity.this.finish();
                                break;
                            case R.id.action_my_reports:
                                viewingOptionSpinner.setSelection(0);
                                @SuppressWarnings("NullableProblems") ListAdapter sourceAdapter2
                                        = new ArrayAdapter<String>(ViewWaterSourcesActivity.this,
                                        android.R.layout.simple_list_item_2, android.R.id.text1,
                                        sourceReportTitles) {
                                    @NonNull
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public View getView(int position, View convertView,
                                                        @NonNull ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text1
                                                = (TextView) view.findViewById(android.R.id.text1);
                                        TextView text2
                                                = (TextView) view.findViewById(android.R.id.text2);
                                        text1.setText(sourceReportTitles.get(position));
                                        text2.setText("Water Source Report");
                                        return view;
                                    }
                                };
                                listView.setAdapter(sourceAdapter2);
                                //noinspection ChainedMethodCall
                                Toast.makeText(getApplicationContext(),
                                        "Currently displaying only your " +
                                                "reports.", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }

                });
    }
}
