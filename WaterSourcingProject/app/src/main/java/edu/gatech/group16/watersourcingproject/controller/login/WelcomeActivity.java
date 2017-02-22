package edu.gatech.group16.watersourcingproject.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.gatech.group16.watersourcingproject.R;

/**
 * Created by Edwin Zhao on 2017/02/22.
 */

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.welcome_button_signin) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            this.finish();
        } else if (i == R.id.welcome_button_signup) {
            Intent signUpIntent = new Intent(this, RegAccountTypeActivity.class);
            startActivity(signUpIntent);
            this.finish();
        }

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        findViewById(R.id.welcome_button_signin).setOnClickListener(this);
        findViewById(R.id.welcome_button_signup).setOnClickListener(this);

    }
}
