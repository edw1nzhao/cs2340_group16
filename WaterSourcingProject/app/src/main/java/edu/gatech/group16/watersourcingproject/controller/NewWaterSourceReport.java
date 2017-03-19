package edu.gatech.group16.watersourcingproject.controller;

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
import edu.gatech.group16.watersourcingproject.model.Enums.OverallCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    private static User user;
    private String currentDateTimeString;
    private List<WaterSourceReport> wsReports;
    private Spinner waterType, waterCondition, overallCondition;
    private EditText waterLocationLatitude, waterLocationLongitude, waterVirusPPM, waterContaminantPPM;
    private TextView reportTitle, contaminantTitle, waterTypeAndVirusPPMTitle, waterConditionAndOverallConditionTitle;
    private Switch switchButton;
    private Toolbar toolbar;
    private String oldEmail;
    private final List<User> users = new ArrayList<User>();

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

        //Shared UI
        switchButton = (Switch) findViewById(R.id.report_switch);
        reportTitle = (TextView) findViewById(R.id.text_report_title);
        waterLocationLatitude = (EditText) findViewById(R.id.text_latitude);
        waterLocationLongitude = (EditText) findViewById(R.id.text_longitude);
        waterTypeAndVirusPPMTitle = (TextView) findViewById(R.id.title_water_type_and_virus_ppm);
        waterConditionAndOverallConditionTitle = (TextView) findViewById(R.id.title_water_condition_and_overall_condition);

        //Water Source Report UI
        waterType = (Spinner) findViewById(R.id.spinner_water_type);
        waterCondition = (Spinner) findViewById(R.id.spinner_water_condition);

        //Water Purity Report UI
        overallCondition = (Spinner) findViewById(R.id.spinner_overall_condition);
        waterVirusPPM = (EditText) findViewById(R.id.text_virus_ppm);
        contaminantTitle = (TextView) findViewById(R.id.title_contaminant_ppm);
        waterContaminantPPM = (EditText) findViewById(R.id.text_contaminant_ppm);

        findViewById(R.id.button_submit).setOnClickListener(this);


        // Fills the spinners with ENUM
        ArrayAdapter<WaterCondition> adaptWaterCondition = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalConditions);
        ArrayAdapter<WaterType> adaptWaterType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);
        ArrayAdapter<OverallCondition> adaptOverallCondition = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterPurityReport.legalOveralConditions);

        waterType.setAdapter(adaptWaterType);
        waterCondition.setAdapter(adaptWaterCondition);
        overallCondition.setAdapter(adaptOverallCondition);

        //Setting initial state of switch button
        switchButton.setChecked(false);

        //Attaching changeListener() to switch button
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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
                    Toast.makeText(getApplicationContext(), "Toggle is ON", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Toggle is OFF.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * OnClick method that will listen for clicks on the
     * view that is taken in and proceed with actions.
     *
     *
     * @param v Takes in a view that will contain buttons
     *          for the onClick method to listen to.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_submit && validCoordinate()) {
            Intent home_activity = new Intent(this, HomeActivity.class);
            wsReports = user.getWaterSourceReport();

            if (wsReports == null) {
                wsReports = new ArrayList<WaterSourceReport>();
            }

            wsReports.add(compileWaterSourceReport());
            user.setWaterSourceReports(wsReports);

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference dbRef = db.getReference();
            final Intent home_test = new Intent(this, HomeActivity.class);

            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User temp = postSnapshot.getValue(User.class);
                        snapshot.getRef().removeValue();
                        users.add(temp);
                    }

                    int i = 0;
                    int marker = -1;
                    for (User u: users) {
                        if (u.getEmail().equals(user.getEmail())) {
                            users.set(i, user);
                            marker = i;
                        }
                        i++;
                    }

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbRef = db.getReference();
                    DatabaseReference newRef = dbRef.child("users").push();
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
                    return;
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
    public WaterSourceReport compileWaterSourceReport() {
        int reportNumber = getReportNumber();
        Date currentDate = new Date();
        String location = waterLocationLatitude.getText().toString() + "," + waterLocationLongitude.getText().toString();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();

        WaterSourceReport wsReport = new WaterSourceReport(reportNumber, currentDate, location, type, condition, submittedBy);
        return wsReport;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WaterPurityReport compileWaterPurityReport() {
        int reportNumber = getReportNumber();
        Date currentDate = new Date();
        String location = waterLocationLatitude.getText().toString() + "," + waterLocationLongitude.getText().toString();
        OverallCondition condition = (OverallCondition) overallCondition.getSelectedItem();
        int vPPM = Integer.parseInt(waterVirusPPM.getText().toString());
        int cPPM = Integer.parseInt(waterContaminantPPM.getText().toString());
        String submittedBy = user.getName();

        WaterPurityReport wpReport = new WaterPurityReport(reportNumber, currentDate, location, condition, submittedBy, vPPM, cPPM);
        return wpReport;
    }

    /**
     * getReportNum method looks into users report size.
     * TODO: Retrieve number of reports from Firebase variable.
     *
     * @return int the number of reports.
     */
    public static int getReportNumber() {
        if (user.getWaterSourceReport() == null) {
            return 1;
        }
        return user.getWaterSourceReport().size() + 1;
    }

    /**
     * Location method that gets the user's location
     * Currently returns a fake location.
     * TODO: Actually get the user location.
     *
     * @return Location returns the location of the user.
     */
    public static Location getUserLocation() {
        //TO DO: Retrieve actual location from phone.
        Location location = new Location("Temp Location");
        location.setLatitude(1.2345d);
        location.setLongitude(1.2345d);
        location.setAccuracy(100);
        location.setElapsedRealtimeNanos(0);
        return location;
    }

    public boolean validCoordinate() {
        String latitude = waterLocationLatitude.getText().toString();
        String longitude = waterLocationLongitude.getText().toString();
        boolean valid = true;
        if (latitude.length() == 0) {
            waterLocationLatitude.setError("Required.");
            valid = false;
        } else if (latitude.matches(".*[a-z].*")) {
            waterLocationLatitude.setError("Incorrect format.");
            valid = false;
        } else {
            waterLocationLatitude.setError(null);
        }

        if (longitude.length() == 0) {
            waterLocationLongitude.setError("Required.");
            valid = false;
        } else if (longitude.matches(".*[a-z].*")) {
            waterLocationLongitude.setError("Incorrect format.");
            valid = false;
        } else {
            waterLocationLongitude.setError(null);
        }
        return valid;
    }
}
