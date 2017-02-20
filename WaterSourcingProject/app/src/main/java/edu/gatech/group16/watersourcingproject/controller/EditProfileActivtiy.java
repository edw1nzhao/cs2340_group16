package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.group16.watersourcingproject.R;

public class EditProfileActivtiy extends AppCompatActivity implements View.OnClickListener {

        Button saveButton;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activtiy);

        saveButton = (Button) findViewById(R.id.save_changes);
        }

        @Override
        public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.save_changes) {
                        Intent saveChangesIntent = new Intent(this, HomeActivity.class);
                        startActivity(saveChangesIntent);
                        //this.finish();
                }
        }
}
