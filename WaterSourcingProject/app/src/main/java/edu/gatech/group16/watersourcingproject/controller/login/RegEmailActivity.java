package edu.gatech.group16.watersourcingproject.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

/**
 * Created by Edwin Zhao on 2017/02/22.
 */
public class RegEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailField;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_email);

        emailField = (EditText) findViewById(R.id.reg_text_email);

        findViewById(R.id.reg_button_continue).setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("USER");

    }

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
        }
    }

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
