package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.icu.text.DateFormat;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class NewWaterSourceReport extends AppCompatActivity implements OnClickListener {

    private static User user;
    private String currentDateTimeString;
    private List<WaterSourceReport> wsReports;
    private Spinner waterType, waterCondition;
    private EditText waterLocationLatitude, waterLocationLongitude;
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

        waterLocationLatitude = (EditText) findViewById(R.id.text_latitude);
        waterLocationLongitude = (EditText) findViewById(R.id.text_longitude);
        waterType = (Spinner) findViewById(R.id.spinner_water_type);
        waterCondition = (Spinner) findViewById(R.id.spinner_water_condition);

        //findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_submit).setOnClickListener(this);

//        toolbar = (Toolbar) findViewById(R.id.new_report_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NewWaterSourceReport.this, HomeActivity.class);
//                intent.putExtra("USER", user);
//                startActivity(intent);
//            }
//        });

        // Fills the spinners with ENUM
        ArrayAdapter<WaterCondition> adaptWaterCondition
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalConditions);
        ArrayAdapter<WaterType> adaptWaterType
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);

        findViewById(R.id.button_submit).setOnClickListener(this);

        waterType.setAdapter(adaptWaterType);
        waterCondition.setAdapter(adaptWaterCondition);

        user = (User) getIntent().getSerializableExtra("USER");

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

            wsReports.add(compileReport());
            user.setWaterSourceReports(wsReports);

            ////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////
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

                }
            });
            ///////////////////////////////////////////////////////
            //////////////////////////////////////////////////////

//            home_activity.putExtra("USER", user);
//            this.startActivity(home_activity);
//            this.finish();
        }
    }

    /**
     * compileReport method which will create a new report and put
     * all of the
     *
     * @return wsReport will
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public WaterSourceReport compileReport() {
        int reportNumber = getReportNumber();
        Date currentDate = new Date();
        //String location = getUserLocation().toString();
        String location = waterLocationLatitude.getText().toString() + "," + waterLocationLongitude.getText().toString();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();


        WaterSourceReport wsReport = new WaterSourceReport(reportNumber, currentDate, location, type, condition, submittedBy);
        return wsReport;


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
