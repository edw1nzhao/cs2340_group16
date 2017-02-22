package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveButton;
    private Button cancelButton;
    private EditText emailField;
    private EditText nameField;
    private EditText passwordField;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activtiy);


        findViewById(R.id.edit_button_cancel).setOnClickListener(this);
        findViewById(R.id.edit_button_save).setOnClickListener(this);



        emailField = (EditText) findViewById(R.id.edit_text_email);
        nameField = (EditText) findViewById(R.id.edit_text_name);
        passwordField = (EditText) findViewById(R.id.edit_text_password);

        user = (User) getIntent().getSerializableExtra("USER");

    }

    @Override
    public void onClick(View v) {
            int i = v.getId();

            if (i == R.id.edit_button_save) {
                if (emailField.getText().length() != 0) {
                    user.setEmail(emailField.getText().toString());
                }
                if (passwordField.getText().length() != 0) {
                    user.setPassword(passwordField.getText().toString());
                }
                if (nameField.getText().length() != 0) {
                    user.setName(nameField.getText().toString());
                }

                Intent saveChangesIntent = new Intent(this, HomeActivity.class);
                saveChangesIntent.putExtra("USER", user);
                startActivity(saveChangesIntent);
                EditProfileActivity.this.finish();
                return;
            } else if (i == R.id.edit_button_cancel) {
                EditProfileActivity.this.finish();
                return;
            }
    }

    public void saveData() {

    }
}
