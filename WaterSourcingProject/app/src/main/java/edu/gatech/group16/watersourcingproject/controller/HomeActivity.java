package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.login.LoginActivity;
import edu.gatech.group16.watersourcingproject.model.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameField;
    private TextView emailField;
    private TextView passwordField;
    private TextView accountTypeField;
    private User user;
    private BottomNavigationView botNavbar;

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
        setContentView(R.layout.application_home);

        findViewById(R.id.logout_button).setOnClickListener(this);
        findViewById(R.id.save_changes).setOnClickListener(this);

        botNavbar = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        user = (User) getIntent().getSerializableExtra("USER");

        nameField = (TextView) findViewById(R.id.name_field);
        emailField = (TextView) findViewById(R.id.email_field);
        accountTypeField = (TextView) findViewById(R.id.account_field);

        nameField.setText("Name: " + user.getName());
        emailField.setText("Email: " + user.getEmail());
        accountTypeField.setText("Account Type: " + user.getAccountType().toString());
        botNavbar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_viewMap:
                                Intent mapIntent =  new Intent(HomeActivity.this, MapsActivity.class);
                                mapIntent.putExtra("USER", user);
                                startActivity(mapIntent);
                                break;
                            case R.id.action_newReport:
                                Intent newReportIntent = new Intent(HomeActivity.this, NewWaterSourceReport.class);
                                newReportIntent.putExtra("USER", user);
                                startActivity(newReportIntent);
                                break;
                            case R.id.action_viewReports:
                                Intent viewReportIntent = new Intent(HomeActivity.this, ViewWaterSourcesActivity.class);
                                viewReportIntent.putExtra("USER", user);
                                startActivity(viewReportIntent);
                                break;
                        }
                        return true;
                    }

                });

//        Date date = new Date();
//        List<WaterSourceReport> reportList = new ArrayList<>();
//        Location location = new Location("LOCATION");
//        location.setLatitude(1.2345d);
//        location.setLongitude(1.2345d);
//        String loc = location.toString();
//        reportList.add(new WaterSourceReport(001, date, loc, WaterType.BOTTLED, WaterCondition.POTABLE, user.getName()));


    }

    /**
     * OnClick method that will listen for clicks on the
     * view that is taken in and proceed with actions.
     *
     *
     * @param v Takes in a view that will contain buttons
     *          for the onClick method to listen to.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logout_button) {
            Intent logoutIntent = new Intent(this, LoginActivity.class);
            startActivity(logoutIntent);
        } else if (i == R.id.save_changes) {
            Intent editUser = new Intent(this, EditProfileActivity.class);
            editUser.putExtra("USER", user);
            startActivity(editUser);
        }
//        } else if (i == R.id.available_button) {
//            Intent editUser = new Intent(this,ViewWaterSourcesActivity.class);
//            editUser.putExtra("USER", user);
//            startActivity(editUser);
//        } else if (i == R.id.new_waterReport) {
//            Intent editUser = new Intent(this, NewWaterSourceReport.class);
//            editUser.putExtra("USER", user);
//            startActivity(editUser);
//        }
    }
}
