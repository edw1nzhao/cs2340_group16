package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.Enums.OverallCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

@SuppressWarnings({"unused", "CyclicClassDependency", "JavaDoc"})
public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference dbRef = db.getReference();
    final DatabaseReference dbRefUser = db.getReference("users");
    DatabaseReference dbRefPurity = db.getReference("purity_report");
    DatabaseReference dbRefSource = db.getReference("source_report");
    private static User user;
    private String currentDateTimeString;
    private Spinner waterType;
    private Spinner waterCondition;
    private Spinner overallCondition;
    private EditText waterLocationLatitude;
    private EditText waterLocationLongitude;
    private EditText waterVirusPPM;
    private EditText waterContaminantPPM;
    private TextView reportTitle;
    private TextView contaminantTitle;
    private TextView waterTypeAndVirusPPMTitle;
    private TextView waterConditionAndOverallConditionTitle;
    private Switch switchButton;
    private String oldEmail;
    private String uid;
    private final List<User> users = new ArrayList<>();
    private int reportNumber;

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
        setContentView(R.layout.activity_new_ws_report);
        user = (User) getIntent().getSerializableExtra("USER");

        uiSetup();
        dataSetup();
    }

    private void uiSetup() {
        //Shared UI
        switchButton = (Switch) findViewById(R.id.report_switch);
        reportTitle = (TextView) findViewById(R.id.text_report_title);
        waterLocationLatitude = (EditText) findViewById(R.id.text_latitude);
        waterLocationLongitude = (EditText) findViewById(R.id.text_longitude);
        waterTypeAndVirusPPMTitle = (TextView) findViewById(R.id.title_water_type_and_virus_ppm);
        waterConditionAndOverallConditionTitle
                = (TextView) findViewById(R.id.title_water_condition_and_overall_condition);

        //Water Source Report UI
        waterType = (Spinner) findViewById(R.id.spinner_water_type);
        waterCondition = (Spinner) findViewById(R.id.spinner_water_condition);

        //Water Purity Report UI
        overallCondition = (Spinner) findViewById(R.id.spinner_overall_condition);
        waterVirusPPM = (EditText) findViewById(R.id.text_virus_ppm);
        contaminantTitle = (TextView) findViewById(R.id.title_contaminant_ppm);
        waterContaminantPPM = (EditText) findViewById(R.id.text_contaminant_ppm);

        //Toolbar initialisation
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_report_toolbar);
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
                Intent intent = new Intent(NewWaterSourceReport.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        //noinspection ChainedMethodCall
        findViewById(R.id.button_submit).setOnClickListener(this);
        //Hides Toggle Button if the account type is USER
        if (user.getAccountType() == AccountType.USER) {
            switchButton.setVisibility(View.INVISIBLE);
        }
        //Setting initial state of switch button
        switchButton.setChecked(false);

        //Attaching changeListener() to switch button
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Updates UI for Water Purity Report
                    reportTitle.setText("Water Purity Report");

                    waterConditionAndOverallConditionTitle.setText("Overall Condition");
                    waterCondition.setVisibility(View.INVISIBLE);
                    overallCondition.setVisibility(View.VISIBLE);

                    waterTypeAndVirusPPMTitle.setText("Virus PPM");
                    waterType.setVisibility(View.INVISIBLE);
                    waterVirusPPM.setVisibility(View.VISIBLE);

                    contaminantTitle.setVisibility(View.VISIBLE);
                    waterContaminantPPM.setVisibility(View.VISIBLE);
                } else {
                    //Updates UI for Water Source Report
                    reportTitle.setText("Water Source Report");

                    waterConditionAndOverallConditionTitle.setText("Water Condition");
                    overallCondition.setVisibility(View.INVISIBLE);
                    waterCondition.setVisibility(View.VISIBLE);

                    waterTypeAndVirusPPMTitle.setText("Water Type");
                    waterVirusPPM.setVisibility(View.INVISIBLE);
                    waterType.setVisibility(View.VISIBLE);

                    contaminantTitle.setVisibility(View.INVISIBLE);
                    waterContaminantPPM.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    private void dataSetup() {
        SpinnerAdapter adaptWaterCondition
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterSourceReport.legalConditions);
        SpinnerAdapter adaptWaterType
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterSourceReport.legalTypes);
        @SuppressWarnings("unchecked") SpinnerAdapter adaptOverallCondition
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterPurityReport.legalOverallConditions);
        uid = user.getUid();
        waterType.setAdapter(adaptWaterType);
        waterCondition.setAdapter(adaptWaterCondition);
        overallCondition.setAdapter(adaptOverallCondition);
    }

    /**
     * OnClick method that will listen for clicks on the
     * view that is taken in and proceed with actions.
     *
     *
     * @param v Takes in a view that will contain buttons
     *          for the onClick method to listen to.
     */
    @SuppressWarnings("FeatureEnvy")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int i = v.getId();
        final boolean reportBoolean = switchButton.isChecked();

        if ((i == R.id.button_submit) && validCoordinate()) {
            if (!reportBoolean) {
                addWaterSourceReport();
            } else {
               addWaterPurityReport();
            }

            //noinspection ChainedMethodCall

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addWaterPurityReport() {
        final Intent home_activity = new Intent(this, HomeActivity.class);

        final WaterPurityReport newRep = compileWaterPurityReport();


        if (validPPM()) {
            List<WaterPurityReport> wpReports = user.getWaterPurityReport();

            if (wpReports == null) {
                wpReports = new ArrayList<>();
            }

            wpReports.add(newRep);
            user.setWaterPurityReports(wpReports);

            dbRefPurity.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        reportNumber = dataSnapshot.getValue(int.class);
                        reportNumber++;
                    } catch (NullPointerException e) {
                        reportNumber = 1;
                    }
                    newRep.setReportNumber(reportNumber);

                    dbRefPurity.child("count").setValue(reportNumber);
                    dbRefPurity.child(String.valueOf(newRep.getReportNumber())).setValue(newRep);
                    dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String uid = user.getUid();

                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                User temp = postSnapshot.getValue(User.class);

                                if (temp.getUid().equals(uid)) {
                                    List<WaterPurityReport> newList;
                                    try {
                                        newList = (List<WaterPurityReport>) postSnapshot.child("waterPurityReport").getValue();
                                        newList.add(newRep);
                                    } catch (NullPointerException e) {
                                        newList = new ArrayList<>();
                                        newList.add(newRep);
                                    }
                                    user.setWaterPurityReports(newList);

                                    dbRefUser.child(uid).child("waterPurityReport").setValue(newList);
                                }
                            }

                            home_activity.putExtra("USER", user);
                            startActivity(home_activity);
                            NewWaterSourceReport.this.finish();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addWaterSourceReport() {
        final Intent home_activity = new Intent(NewWaterSourceReport.this, HomeActivity.class);
        final WaterSourceReport newRep = compileWaterSourceReport();

        dbRefSource.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    reportNumber = dataSnapshot.getValue(int.class);
                    reportNumber++;
                } catch (NullPointerException e) {
                    reportNumber = 1;
                }
                newRep.setReportNumber(reportNumber);

                dbRefSource.child("count").setValue(reportNumber);
                dbRefSource.child(String.valueOf(newRep.getReportNumber())).setValue(newRep);
                dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String uid = user.getUid();

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            User temp = postSnapshot.getValue(User.class);

                            if (temp.getUid().equals(uid)) {
                                List<WaterSourceReport> newList;
                                try {
                                    newList = (List<WaterSourceReport>) postSnapshot.child("waterSourceReport").getValue();
                                    newList.add(newRep);
                                } catch (NullPointerException e) {
                                    newList = new ArrayList<>();
                                    newList.add(newRep);
                                }

                                user.setWaterSourceReports(newList);
                                dbRefUser.child(uid).child("waterSourceReport").setValue(newList);
                            }
                        }

                        home_activity.putExtra("USER", user);
                        startActivity(home_activity);
                        NewWaterSourceReport.this.finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     * compileReport method which will create a new report and put
     * all of the
     *
     * @return wsReport a newly created water source report
     */
    @RequiresApi(api = Build.VERSION_CODES.N)

    private WaterSourceReport compileWaterSourceReport() {
        Date currentDate = new Date();
        @SuppressWarnings("ChainedMethodCall") String location
                = waterLocationLatitude.getText().toString() + ","
                + waterLocationLongitude.getText().toString();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();

        return new WaterSourceReport(
                reportNumber, currentDate, location, type, condition, submittedBy, uid);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private WaterPurityReport compileWaterPurityReport() {
        Date currentDate = new Date();
        @SuppressWarnings("ChainedMethodCall") String location
                = waterLocationLatitude.getText().toString()
                + "," + waterLocationLongitude.getText().toString();
        OverallCondition condition = (OverallCondition) overallCondition.getSelectedItem();
        @SuppressWarnings("ChainedMethodCall") int vPPM
                = Integer.parseInt(waterVirusPPM.getText().toString());
        @SuppressWarnings("ChainedMethodCall") int cPPM
                = Integer.parseInt(waterContaminantPPM.getText().toString());
        String submittedBy = user.getName();

        return new WaterPurityReport(
                reportNumber, currentDate, location, condition, submittedBy, vPPM, cPPM, uid);
    }


    /**
     * Location method that gets the user's location
     * Currently returns a fake location.
     *
     * @return Location returns the location of the user.
     */
    @SuppressWarnings("unused")
    public static Location getUserLocation() {
        //TO DO: Retrieve actual location from phone.
        Location location = new Location("Temp Location");
        //noinspection MagicNumber
        location.setLatitude(1.2345d);
        //noinspection MagicNumber
        location.setLongitude(1.2345d);
        location.setAccuracy(100);
        location.setElapsedRealtimeNanos(0);
        return location;
    }

    private boolean validCoordinate() {
        @SuppressWarnings("ChainedMethodCall") String latitude
                = waterLocationLatitude.getText().toString();
        @SuppressWarnings("ChainedMethodCall") String longitude
                = waterLocationLongitude.getText().toString();
        boolean valid = true;
        if (latitude.isEmpty()) {
            waterLocationLatitude.setError("Required.");
            valid = false;
        } else if (latitude.matches(".*[a-z].*")) {
            waterLocationLatitude.setError("Incorrect format.");
            valid = false;
        } else if (latitude.contains("+") || latitude.contains("*") || latitude.contains("#")
                || latitude.contains(";") || latitude.contains(",") || latitude.contains("(")
                || latitude.contains(")") || latitude.contains("/")) {
            waterLocationLatitude.setError("Contains invalid character");
            valid = false;
        } else //noinspection MagicNumber,MagicNumber
            if ((Double.parseDouble(latitude) < -90) || (Double.parseDouble(latitude) > 90)) {
            waterLocationLatitude.setError("Must be between 0° and (+/–)90°.");
            valid = false;
        } else {
            waterLocationLatitude.setError(null);
        }

        if (longitude.isEmpty()) {
            waterLocationLongitude.setError("Required.");
            valid = false;
        } else if (longitude.matches(".*[a-z].*")) {
            waterLocationLongitude.setError("Incorrect format.");
            valid = false;
         } else if (longitude.contains("+") || longitude.contains("*") || longitude.contains("#")
                || longitude.contains(";") || longitude.contains(",") || longitude.contains("(")
                || longitude.contains(")") || longitude.contains("/")) {
            waterLocationLongitude.setError("Contains invalid character");
            valid = false;
        } else //noinspection MagicNumber,MagicNumber
            if ((Double.parseDouble(longitude) < -180) || (Double.parseDouble(longitude) > 180)) {
            waterLocationLongitude.setError("Must be between 0° and (+/–)180°.");
            valid = false;
        } else {
            waterLocationLongitude.setError(null);
        }
        return valid;
    }

    private boolean validPPM() {
        String virusPPM = waterVirusPPM.getText().toString();
        String contaminantPPM = waterContaminantPPM.getText().toString();
        boolean valid = true;

        if (virusPPM.isEmpty()) {
            waterVirusPPM.setError("Required.");
            valid = false;
        } else if (virusPPM.matches(".*[a-zA-Z].*")) {
            waterVirusPPM.setError("Incorrect format.");
            valid = false;
        } else if (virusPPM.contains("+") || virusPPM.contains("*") || virusPPM.contains("#")
                || virusPPM.contains(";") || virusPPM.contains(",") || virusPPM.contains("(")
                || virusPPM.contains(")") || virusPPM.contains("/")) {
            waterVirusPPM.setError("Contains invalid character.");
            valid = false;
        } else if (Double.parseDouble(virusPPM) < 0) {
            waterVirusPPM.setError("Virus PPM cannot be negative.");
            valid = false;
        } else {
            waterVirusPPM.setError("Error");
        }

        if (contaminantPPM.isEmpty()) {
            waterContaminantPPM.setError("Required.");
            valid = false;
        } else if (contaminantPPM.matches(".*[a-zA-Z].*")) {
            waterContaminantPPM.setError("Incorrect format.");
            valid = false;
        } else if (contaminantPPM.contains("+") || contaminantPPM.contains("*") || contaminantPPM.contains("#")
                || contaminantPPM.contains(";") || contaminantPPM.contains(",") || contaminantPPM.contains("(")
                || contaminantPPM.contains(")") || contaminantPPM.contains("/")) {
            waterContaminantPPM.setError("Contains invalid character.");
            valid = false;
        } else if (Double.parseDouble(contaminantPPM) < 0) {
            waterContaminantPPM.setError("Virus PPM cannot be negative.");
            valid = false;
        } else {
            waterContaminantPPM.setError("Error");
        }
        return valid;
    }
}
