package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

@SuppressWarnings({"unused", "CyclicClassDependency", "JavaDoc"})
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailField;
    private EditText nameField;
    private EditText passwordField;
    private User user;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activity);

        //noinspection ChainedMethodCall
        user = (User) getIntent().getSerializableExtra("USER");
        uiSetup();
        dataSetup();
    }

    /**
     * Sets up data for the screen.
     */
    private void dataSetup() {
        nameField.setHint(user.getName());
        emailField.setHint(user.getEmail());
        passwordField.setHint("******");
    }

    /**
     * Sets up ui for the screen.
     */
    private void uiSetup() {
        //noinspection ChainedMethodCall
        findViewById(R.id.edit_button_save).setOnClickListener(this);

        emailField = (EditText) findViewById(R.id.edit_text_email);
        nameField = (EditText) findViewById(R.id.edit_text_name);
        passwordField = (EditText) findViewById(R.id.edit_text_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_profile_toolbar);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions,ChainedMethodCall
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //noinspection ChainedMethodCall
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
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
    @SuppressWarnings("FeatureEnvy")
    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.edit_button_save) {
            //noinspection ChainedMethodCall
            if (emailField.getText().length() != 0) {
                //noinspection ChainedMethodCall
                user.setEmail(emailField.getText().toString());
            }
            //noinspection ChainedMethodCall
            if (passwordField.getText().length() != 0) {
                //noinspection ChainedMethodCall
                user.setPassword(passwordField.getText().toString());
            }
            //noinspection ChainedMethodCall
            if (nameField.getText().length() != 0) {
                //noinspection ChainedMethodCall
                user.setName(nameField.getText().toString());
            }

            FirebaseDatabase db = FirebaseDatabase.getInstance();

            final DatabaseReference dbRef = db.getReference();
            final Intent home_activity = new Intent(this, HomeActivity.class);

            //noinspection ChainedMethodCall
            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String uid = user.getUid();
                    @SuppressWarnings("UnusedAssignment") Collection<User> listUsers = new ArrayList<>();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dRef = database.getReference("users");

                    //noinspection ChainedMethodCall
                    dRef.child(uid).setValue(EditProfileActivity.this.user);

                    home_activity.putExtra("USER", user);
                    startActivity(home_activity);
                    EditProfileActivity.this.finish();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
