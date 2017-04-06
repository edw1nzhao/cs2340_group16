package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
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
@SuppressWarnings({"unused", "CyclicClassDependency"})
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabLogout;

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
        //noinspection ChainedMethodCall
        user = (User) getIntent().getSerializableExtra("USER");

        dataSetup();
        uiSetup();
    }

    /**
     * OnClick method that will listen for clicks on the
     * view.
     *
     *
     * @param v Takes in a view.
     */
    @Override
    public void onClick(View v) {
    }

    /**
     * Sets up user information to be displayed
     *
     */
    @SuppressWarnings("FeatureEnvy")
    @SuppressLint("SetTextI18n")
    private void dataSetup() {

        TextView nameField = (TextView) findViewById(R.id.name_field);
        TextView emailField = (TextView) findViewById(R.id.email_field);
        TextView accountTypeField = (TextView) findViewById(R.id.account_field);

        nameField.setText("Name:  " + user.getName());
        emailField.setText("Email:  " + user.getEmail());
        //noinspection ChainedMethodCall
        accountTypeField.setText("Account Type:  " + user.getAccountType().toString());
    }

    /**
     * Sets up all of the necessary ui for this screen.
     *
     */
    private void uiSetup() {
        fabSetup();
        BottomNavigationView botNavbar
                = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        botNavbar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_viewMap:
                                Intent mapIntent
                                        = new Intent(HomeActivity.this, MapsActivity.class);
                                mapIntent.putExtra("USER", user);
                                startActivity(mapIntent);
                                break;
                            case R.id.action_newReport:
                                Intent newReportIntent
                                        = new Intent(HomeActivity.this, NewWaterSourceReport.class);
                                newReportIntent.putExtra("USER", user);
                                startActivity(newReportIntent);
                                break;
                            case R.id.action_viewReports:
                                Intent viewReportIntent
                                        = new Intent(
                                                HomeActivity.this, ViewWaterSourcesActivity.class);
                                viewReportIntent.putExtra("USER", user);
                                startActivity(viewReportIntent);
                                break;
                        }
                        return true;
                    }

                });

    }
    /**
     * Fab setup
     *
     */
    private void fabSetup() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabLogout = (FloatingActionButton) findViewById(R.id.fabLogout);

        fab.setOnClickListener(new View.OnClickListener() {
            boolean open = false;
            @Override
            public void onClick(View v) {
                if (!open) {
                    fabEdit.show();
                    fabLogout.show();
                    open = true;
                } else {
                    fabEdit.hide();
                    fabLogout.hide();
                    open = false;
                }
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editUser = new Intent(HomeActivity.this, EditProfileActivity.class);
                editUser.putExtra("USER", user);
                startActivity(editUser);
                HomeActivity.this.finish();
            }
        });

        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                HomeActivity.this.finish();
            }
        });
    }
}
