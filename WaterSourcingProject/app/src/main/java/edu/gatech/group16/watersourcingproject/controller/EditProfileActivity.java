package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.group16.watersourcingproject.R;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activtiy);

        cancelButton = (Button) findViewById(R.id.edit_button_cancel);
        saveButton = (Button) findViewById(R.id.save_changes);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
            int i = v.getId();

            if (i == R.id.edit_button_save) {
                Intent saveChangesIntent = new Intent(this, HomeActivity.class);
                startActivity(saveChangesIntent);
                EditProfileActivity.this.finish();
                return;
            } else if (i == R.id.edit_button_cancel) {
                EditProfileActivity.this.finish();
                return;
            }
    }
}
