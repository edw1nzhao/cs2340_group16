package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.login.RegPasswordActivity;
import edu.gatech.group16.watersourcingproject.model.User;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveButton;
    private Button cancelButton;
    private EditText emailField;
    private EditText nameField;
    private EditText passwordField;
    private Toolbar toolbar;
    private User user;
    private FirebaseAuth mAuth;



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

        findViewById(R.id.edit_button_save).setOnClickListener(this);

        emailField = (EditText) findViewById(R.id.edit_text_email);
        nameField = (EditText) findViewById(R.id.edit_text_name);
        passwordField = (EditText) findViewById(R.id.edit_text_password);
        user = (User) getIntent().getSerializableExtra("USER");

        nameField.setHint(user.getName());
        emailField.setHint(user.getEmail());
        passwordField.setHint("******");
        toolbar = (Toolbar) findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    String uid2 = mAuth.getInstance().getCurrentUser().getUid();
                    Log.d("FUCKTHIS", uid2);
                    String uid = user.getUid();
                    List<User> listUsers = new ArrayList<>();

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        User temp = snap.getValue(User.class);
                        snapshot.getRef().removeValue();
                        if (temp.getUid().equals(uid)) {
                            temp = user;
                        }

                        listUsers.add(temp);
                    }

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dRef = database.getReference("users");
                    for (int i = 0; i < listUsers.size(); i++) {
                    }
                    dRef.child(uid).setValue(EditProfileActivity.this.user);

                    home_activity.putExtra("USER", user);
                    startActivity(home_activity);
                    finish();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return;
        }
    }
}
