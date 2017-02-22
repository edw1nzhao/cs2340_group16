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
public class RegNameActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameField;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_name);

        nameField = (EditText) findViewById(R.id.reg_text_name);

        findViewById(R.id.reg_button_continue).setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("USER");

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.reg_button_continue) {
            if (validForm()) {
                user.setName(nameField.getText().toString());
                Intent intent = new Intent(this, RegEmailActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                this.finish();
            }
        }
    }

    public boolean validForm() {
        boolean valid = true;
        String name = nameField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameField.setError("Required.");
            valid = false;
        } else {
            nameField.setError(null);
        }

        return valid;
    }

}
