package edu.gatech.group16.watersourcingproject.controller.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.controller.MapsActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnClickListener {

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private EditText emailField;
    private EditText passwordField;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbReference;

    private User user;
    //Inside: is the list of userIDs from firebase
    private final List<User> users = new ArrayList<User>();

    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiSetup();
        firebaseSetup();
    }

    /**
     * Tells application it's onstart and tells
     * Firebase Authentication to start listening
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Tells application it's on end and tells
     * Firebase Authentication to stop listening
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Setup for UI
     */
    private void uiSetup() {
        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        emailField = (EditText) findViewById(R.id.field_email);
        passwordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
    }

    /**
     * Setup for firebase authentication
     */
    private void firebaseSetup() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }
    /**
     * Sign in method that takes in two parameters (email and password)
     * Connects to Firebase and checks authentication
     *
     * @param email String email that was used to sign in
     * @param password String password
     */
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validForm()) {
            return;
        }

        final String tempEmail = email;
        final Intent home_activity = new Intent(this, HomeActivity.class);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(LoginActivity.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }

                if (!task.isSuccessful()) {
                    mStatusTextView.setText(R.string.auth_failed);
                } else {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    final DatabaseReference dbRef = db.getReference();

                    dbRef.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                User temp = postSnapshot.getValue(User.class);
                                if (temp.getEmail().equals(tempEmail)) {

                                    String count = dbRef.getKey();

                                    //int position = Integer.parseInt(count);
                                    home_activity.putExtra("USER", temp);
                                    //home_activity.putExtra("POSITION", position);
                                    startActivity(home_activity);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    /**
     * Currently not called.
     * Sends email to user for email verification.
     */
    private void sendEmailVerification() {
        findViewById(R.id.verify_email_button).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable button
                        findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Updates text on login for login/logout confirmation
     *
     * @param user takes in a firebase user to get the user information.
     */
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
        } else {
            mStatusTextView.setText(R.string.signed_out);
        }
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

        if (i == R.id.email_create_account_button) {
            Intent intent = new Intent(this, RegAccountTypeActivity.class);
            startActivity(intent);
        } else if (i == R.id.email_sign_in_button) {
            if (validForm()) {
                signIn(emailField.getText().toString(), passwordField.getText().toString());
            }
        }
    }

    /**
     * Tells application it's onstart and tells
     * Firebase Authentication to start listening
     *
     * @return valid true or false depending on if the form inputted is valid.
     */
    public boolean validForm() {
        boolean valid = true;
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        if (email.length() == 0) {
            emailField.setError("Required.");
            valid = false;
        } else if (email.length() < 6){
            emailField.setError("Incorrect email.");
            valid = false;
        } else if (!email.contains("@")
                || email.contains("@.com")
                || email.contains("@.edu")
                || email.contains("@.net")) {
            emailField.setError("Incorrect email.");
            valid = false;
        } else {
            emailField.setError(null);
        }
        if (password.length() == 0) {
            passwordField.setError("Required.");
            valid = false;
        } else if (password.length() < 6 || password.length() > 23){
            passwordField.setError("Incorrect password.");
            valid = false;
        } else {
            passwordField.setError(null);
        }
        return valid;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
