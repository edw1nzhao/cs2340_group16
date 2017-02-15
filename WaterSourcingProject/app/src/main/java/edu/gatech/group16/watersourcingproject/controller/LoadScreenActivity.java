package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import edu.gatech.group16.watersourcingproject.R;

/**
 * Screen the appears when app first loads.
 */
public class LoadScreenActivity extends AppCompatActivity implements OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadscreen);

        // Buttons
        findViewById(R.id.signin_button).setOnClickListener(this);
        findViewById(R.id.registration_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signin_button) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (i == R.id.signin_button) {
            Intent intent2 = new Intent(this, RegistrationActivity.class);
            startActivity(intent2);
        }
    }
}