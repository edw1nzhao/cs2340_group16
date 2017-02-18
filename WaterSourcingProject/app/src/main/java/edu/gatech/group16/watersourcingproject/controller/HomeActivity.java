package edu.gatech.group16.watersourcingproject.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import edu.gatech.group16.watersourcingproject.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView nameField;
    TextView emailField;
    TextView passwordField;
    TextView accountTypeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_home);

        findViewById(R.id.logout_button).setOnClickListener(this);

        nameField = (TextView) findViewById(R.id.name_field);
        emailField = (TextView) findViewById(R.id.email_field);
        passwordField = (TextView) findViewById(R.id.password_field);
        accountTypeField = (TextView) findViewById(R.id.account_field);


        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("NAME");
        String email = bundle.getString("EMAIL");
        String password = bundle.getString("PASSWORD");
        String accountType = bundle.getString("ACCOUNT TYPE");

        nameField.setText("Name: " + name);
        emailField.setText("Email: " + email);
        passwordField.setText("Password: " + password);
        accountTypeField.setText("Account Type: " + accountType);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logout_button) {
            Intent logoutIntent = new Intent(this, LoginActivity.class);
            startActivity(logoutIntent);
            this.finish();
        }
    }

}
