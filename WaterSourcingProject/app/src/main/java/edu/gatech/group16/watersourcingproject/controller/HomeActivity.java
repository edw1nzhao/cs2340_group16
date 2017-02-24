package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

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
    private Button editButton;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_home);

        findViewById(R.id.logout_button).setOnClickListener(this);
        findViewById(R.id.save_changes).setOnClickListener(this);
        findViewById(R.id.available_button).setOnClickListener(this);
        findViewById(R.id.new_waterReport).setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("USER");

        nameField = (TextView) findViewById(R.id.name_field);
        emailField = (TextView) findViewById(R.id.email_field);
        passwordField = (TextView) findViewById(R.id.password_field);
        accountTypeField = (TextView) findViewById(R.id.account_field);

        editButton = (Button) findViewById(R.id.save_changes);

        nameField.setText("Name: " + user.getName());
        emailField.setText("Email: " + user.getEmail());
        passwordField.setText("Password: " + user.getPassword());
        accountTypeField.setText("Account Type: " + user.getAccountType().toString());
    }

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
        } else if (i == R.id.available_button) {
            Intent editUser = new Intent(this,ViewWaterSourcesActivity.class);
            editUser.putExtra("USER", user);
            startActivity(editUser);
        } else if (i == R.id.new_waterReport) {
            Intent editUser = new Intent(this, NewWaterSourceReport.class);
            editUser.putExtra("USER", user);
            startActivity(editUser);
        }
    }

}
