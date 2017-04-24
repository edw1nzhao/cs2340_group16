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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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
    private String TAG = "ViewWaterSources";

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = db.getReference();
    private DatabaseReference purityDb = db.getReference("purity_report");
    private DatabaseReference sourceDb = db.getReference("source_report");
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

        userReportCollect();

        try {
            ListAdapter adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, sourceReportTitles);
            listView.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.d("Null", "Something");
        }

        uiSetup();

        viewingOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //noinspection StringEquality,ChainedMethodCall
                sourceReportTitles.clear();
                purityReportTitles.clear();

                userReportCollect();
                if (viewingOptionSpinner.getSelectedItem().toString() == "Water Source Reports") {
                    ListAdapter sourceAdapter = new ArrayAdapter<String>(
                            ViewWaterSourcesActivity.this, android.R.layout.simple_list_item_2,
                            android.R.id.text1, sourceReportTitles) {

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

                    listView.setAdapter(null);
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
                        listView.setAdapter(null);
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

    private void userReportCollect() {
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
    }

    private void uiSetup() {
        purityReportList = null;
        sourceReportList = null;
        reportOptions.add("Water Source Reports");
        reportOptions.add("Water Purity Reports");

        bottomNav();

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.report_list);
        bottomNav = (BottomNavigationView) findViewById(R.id.view_ws_bottom_navbar);
        viewingOptionSpinner = (Spinner) findViewById(R.id.spinner_report_options);

        //Sets Spinner in for report viewing options
        @SuppressWarnings("unchecked") SpinnerAdapter adaptReportOptions
                = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, this.reportOptions);
        viewingOptionSpinner.setAdapter(null);
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


    private void bottomNav() {
        //Sets BottomNavigationView functionality

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                bottomNav.setOnNavigationItemSelectedListener(
                        new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                purityReportTitles.clear();
                                sourceReportTitles.clear();

                                switch (item.getItemId()) {
                                    case R.id.action_all_reports:
                                        purityReportTitles.clear();
                                        sourceReportTitles.clear();
                                        viewingOptionSpinner.setSelection(0);
                                        for (DataSnapshot snapshot: dataSnapshot.child("source_report").getChildren()) {
                                            if (!snapshot.getValue().getClass().equals(java.lang.Long.class)) {
                                                try {
                                                    sourceReportList.add(snapshot.getValue(WaterSourceReport.class));
                                                } catch (NullPointerException e) {
                                                    sourceReportList = new ArrayList<WaterSourceReport>();
                                                    sourceReportList.add(snapshot.getValue(WaterSourceReport.class));
                                                }
                                            }
                                        }

                                        for (DataSnapshot snapshot: dataSnapshot.child("purity_report").getChildren()) {
                                            if (!snapshot.getValue().getClass().equals(java.lang.Long.class)) {
                                                try {
                                                    purityReportList.add(snapshot.getValue(WaterPurityReport.class));
                                                } catch (NullPointerException e) {
                                                    purityReportList = new ArrayList<WaterPurityReport>();
                                                    purityReportList.add(snapshot.getValue(WaterPurityReport.class));
                                                }
                                            }
                                        }

                                        try {
                                            for (WaterPurityReport pReport : purityReportList) {
                                                purityReportTitles.add("Report Number: " + pReport.getReportNumber());
                                            }
                                        } catch (NullPointerException e) {
                                        }


                                        try {

                                            for (WaterSourceReport sReport : sourceReportList) {
                                                sourceReportTitles.add("Report Number: " + sReport.getReportNumber());
                                            }
                                        } catch (NullPointerException e) {
                                            Log.d("ViewWaterSources", "No WaterPurityReports");
                                        }
                                        Log.d("CHEERIO2", sourceReportTitles.size() + "");

                                        ListAdapter sourceAdapter1
                                                = new ArrayAdapter<String>(ViewWaterSourcesActivity.this,
                                                android.R.layout.simple_list_item_2, android.R.id.text1,
                                                sourceReportTitles) {
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
                                        Intent intent = new Intent(
                                                ViewWaterSourcesActivity.this,
                                                ViewWaterSourcesActivity.class);
                                        intent.putExtra("USER", user);
                                        startActivity(intent);

                                        Toast.makeText(getApplicationContext(),
                                                "Currently displaying all reports." , Toast.LENGTH_SHORT)
                                                .show();
                                        break;
                                    case R.id.action_empty:
                                        purityReportTitles.clear();
                                        sourceReportTitles.clear();
                                        intent = new Intent(
                                                ViewWaterSourcesActivity.this,
                                                ViewWaterSourcesActivity.class);
                                        intent.putExtra("USER", user);
                                        startActivity(intent);
                                        ViewWaterSourcesActivity.this.finish();
                                        break;
                                    case R.id.action_my_reports:
                                        purityReportTitles.clear();
                                        sourceReportTitles.clear();
                                        purityReportList = user.getWaterPurityReport();
                                        sourceReportList = user.getWaterSourceReport();
                                        try {
                                            for (WaterPurityReport pReport : purityReportList) {
                                                purityReportTitles.add("Report Number: " + pReport.getReportNumber());
                                            }
                                        } catch (NullPointerException e) {
                                        }

                                        try {
                                            for (WaterSourceReport sReport : sourceReportList) {
                                                sourceReportTitles.add("Report Number: " + sReport.getReportNumber());
                                            }
                                        } catch (NullPointerException e) {
                                            Log.d("ViewWaterSources", "No WaterPurityReports");
                                        }
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
                                        listView.setAdapter(null);
                                        listView.setAdapter(sourceAdapter2);
                                        //noinspection ChainedMethodCall
                                        Toast.makeText(getApplicationContext(),
                                                "Currently displaying only your " +
                                                        "reports.", Toast.LENGTH_SHORT).show();

                                        intent = new Intent(
                                                ViewWaterSourcesActivity.this,
                                                ViewWaterSourcesActivity.class);

                                        intent.putExtra("USER", user);
                                        startActivity(intent);
                                        break;
                                    default:
                                        break;
                                }
                                return true;
                            }

                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
