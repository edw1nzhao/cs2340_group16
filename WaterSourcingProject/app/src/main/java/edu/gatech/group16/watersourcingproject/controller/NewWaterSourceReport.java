package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    private static User user;
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    private String oldEmail;
    private final List<User> users = new ArrayList<>();

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
        //noinspection AssignmentToStaticFieldFromInstanceMethod,ChainedMethodCall
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
                    //noinspection ChainedMethodCall
                    Toast.makeText(getApplicationContext(),
                            "Toggle is ON", Toast.LENGTH_SHORT).show();
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
                    //noinspection ChainedMethodCall
                    Toast.makeText(getApplicationContext(),
                            "Toggle is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dataSetup() {
        // Fills the spinners with ENUM
        @SuppressWarnings("unchecked") SpinnerAdapter adaptWaterCondition
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                WaterSourceReport.legalConditions);
        @SuppressWarnings("unchecked") SpinnerAdapter adaptWaterType
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                WaterSourceReport.legalTypes);
        @SuppressWarnings("unchecked") SpinnerAdapter adaptOverallCondition
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                WaterPurityReport.legalOverallConditions);

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
        boolean reportBoolean = switchButton.isChecked();

        if ((i == R.id.button_submit) && validCoordinate()) {
            @SuppressWarnings("UnusedAssignment") Intent home_activity
                    = new Intent(this, HomeActivity.class);
            if (!reportBoolean) {
                List<WaterSourceReport> wsReports = user.getWaterSourceReport();
                if (wsReports == null) {
                    wsReports = new ArrayList<>();
                }

                wsReports.add(compileWaterSourceReport());
                user.setWaterSourceReports(wsReports);

            } else {
                List<WaterPurityReport> wpReports = user.getWaterPurityReport();
                
                if (wpReports == null) {
                    wpReports = new ArrayList<>();
                }
               
                wpReports.add(compileWaterPurityReport());
                user.setWaterPurityReports(wpReports);
            }

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference dbRef = db.getReference();
            final Intent home_test = new Intent(this, HomeActivity.class);

            //noinspection ChainedMethodCall
            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User temp = postSnapshot.getValue(User.class);
                        //noinspection ChainedMethodCall
                        snapshot.getRef().removeValue();
                        users.add(temp);
                    }

                    int i = 0;
                    int marker = -1;
                    for (User u: users) {
                        //noinspection ChainedMethodCall
                        if (u.getEmail().equals(user.getEmail())) {
                            users.set(i, user);
                            marker = i;
                        }
                        i++;
                    }

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbRef = db.getReference();
                    @SuppressWarnings("ChainedMethodCall") DatabaseReference newRef
                            = dbRef.child("users").push();
                    User pushedUser = users.get(marker);

                    for (int j = 0; j < users.size(); j++) {
                        newRef.setValue(users.get(j));
                    }
                    newRef.setValue(pushedUser);

                    home_test.putExtra("USER", user);
                    startActivity(home_test);
                    finish();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    /**
     * compileReport method which will create a new report and put
     * all of the
     *
     * @return wsReport a newly created water source report
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private WaterSourceReport compileWaterSourceReport() {
        int reportNumber = getReportNumber();
        Date currentDate = new Date();
        @SuppressWarnings("ChainedMethodCall") String location
                = waterLocationLatitude.getText().toString() + ","
                + waterLocationLongitude.getText().toString();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();

        return new WaterSourceReport(
                reportNumber, currentDate, location, type, condition, submittedBy);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private WaterPurityReport compileWaterPurityReport() {
        int reportNumber = getReportNumber();
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
                reportNumber, currentDate, location, condition, submittedBy, vPPM, cPPM);
    }

    /**
     * getReportNum method looks into users report size.
     *
     * @return int the number of reports.
     */
    @SuppressWarnings("FeatureEnvy")
    private static int getReportNumber() {
        if ((user.getWaterSourceReport() == null) && (user.getWaterPurityReport() == null)) {
            return 1;
        } else if (user.getWaterPurityReport() == null) {
            //noinspection ChainedMethodCall
            return user.getWaterSourceReport().size() + 1;
        } else if (user.getWaterSourceReport() == null) {
            //noinspection ChainedMethodCall
            return user.getWaterPurityReport().size() + 1;
        }
        //noinspection ChainedMethodCall,ChainedMethodCall
        return user.getWaterSourceReport().size() + user.getWaterPurityReport().size() + 1;
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
        }else //noinspection MagicNumber,MagicNumber
            if ((Double.parseDouble(longitude) < -180) || (Double.parseDouble(longitude) > 180)) {
            waterLocationLongitude.setError("Must be between 0° and (+/–)180°.");
            valid = false;
        } else {
            waterLocationLongitude.setError(null);
        }
        return valid;
    }
}
