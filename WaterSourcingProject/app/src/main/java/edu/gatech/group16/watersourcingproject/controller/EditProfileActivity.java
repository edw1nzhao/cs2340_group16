package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveButton;
    private Button cancelButton;
    private EditText emailField;
    private EditText nameField;
    private EditText passwordField;

    private User user;
    private String oldEmail;
    private final List<User> users = new ArrayList<User>();

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

        findViewById(R.id.edit_button_cancel).setOnClickListener(this);
        findViewById(R.id.edit_button_save).setOnClickListener(this);

        emailField = (EditText) findViewById(R.id.edit_text_email);
        nameField = (EditText) findViewById(R.id.edit_text_name);
        passwordField = (EditText) findViewById(R.id.edit_text_password);
        user = (User) getIntent().getSerializableExtra("USER");
        oldEmail = user.getEmail();


        nameField.setHint(user.getName());
        emailField.setHint(user.getEmail());
        passwordField.setHint("*****");
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

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference dbRef = db.getReference();
            final Intent home_activity = new Intent(this, HomeActivity.class);


            dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User temp = postSnapshot.getValue(User.class);
                        snapshot.getRef().removeValue();

                        users.add(temp);
                    }

                    int i = 0;
                    int marker = -1;
                    for (User u: users) {
                        if (u.getEmail().equals(oldEmail)) {
                            users.set(i, user);
                            marker = i;
                        }
                        i++;
                    }

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbRef = db.getReference();
                    DatabaseReference newRef = dbRef.child("users").push();

                    User pushedUser = users.get(marker);

                    for (int j = 0; j < users.size(); j++) {
                        newRef.setValue(users.get(j));
                    }
                    newRef.setValue(pushedUser);

                    home_activity.putExtra("USER", user);
                    startActivity(home_activity);
                    finish();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return;
        } else if (i == R.id.edit_button_cancel) {
            EditProfileActivity.this.finish();
            return;
        }
    }
}
