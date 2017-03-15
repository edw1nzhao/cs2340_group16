package edu.gatech.group16.watersourcingproject.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

/**
 * Created by Edwin Zhao on 2017/02/22.
 */
public class RegEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailField;
    private User user;
    private Toolbar toolbar;
    private Button cancelButton;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_email);

        emailField = (EditText) findViewById(R.id.reg_text_email);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);

        findViewById(R.id.reg_button_continue).setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("USER");
        toolbar = (Toolbar) findViewById(R.id.email_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegEmailActivity.this, RegNameActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
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
    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.reg_button_continue) {
            if (validForm() ) {
                user.setEmail(emailField.getText().toString());
                Intent intent = new Intent(this, RegPasswordActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                this.finish();
            }
        } else if (i == R.id.cancel_button) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            this.finish();
        }
    }

    /**
     * Tells application it's onstart and tells
     * Firebase Authentication to start listening
     *
     * @return valid true or false depending on if the form inputted is valid.
     */
    public boolean validForm() {
        String email = emailField.getText().toString();
        boolean valid = true;
        if (email.length() == 0) {
            emailField.setError("Required.");
            valid = false;
        } else if (email.length() < 6) {
            emailField.setError("Incorrect format.");
            valid = false;
        } else if (!email.contains("@")
                || email.contains("@.com")
                || email.contains("@.edu")
                || email.contains("@.net")) {
            emailField.setError("Incorrect format.");
            valid = false;
        } else {
            emailField.setError(null);
        }
        return valid;
    }
}
