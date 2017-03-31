package edu.gatech.group16.watersourcingproject.controller.login;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterCondition;
import edu.gatech.group16.watersourcingproject.model.Enums.WaterType;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class RegPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EMAIL/PASSWORD";
    private EditText passwordField;
    private FirebaseAuth mAuth;
    private User user;
    private Toolbar toolbar;
    private Button cancelButton;
    private boolean valid = false;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

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
        setContentView(R.layout.activity_registration_password);

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        passwordField = (EditText) findViewById(R.id.reg_text_password);
        findViewById(R.id.reg_button_signup).setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("USER");

        mAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.password_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegPasswordActivity.this, RegEmailActivity.class);
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

        if (i == R.id.reg_button_signup) {
            String uid = mAuth.getInstance().getCurrentUser().getUid();
            user.setPassword(passwordField.getText().toString());
            user.setUid(uid);

            if (createAccount(user.getEmail(), user.getPassword())) {
                DatabaseReference dbRef = db.getReference();

                dbRef.child("users").child(uid).setValue(user);

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                RegPasswordActivity.this.finish();
            }
        } else if (i == R.id.cancel_button) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            this.finish();
        }
    }

    /**
     * Create account method that takes in email and password
     * to sign in the user.
     *
     *
     * @param email Takes in the user's email that's inputted earlier screen.
     * @param password Password taken in from this screen.
     * @return boolean gives back false if not signed in.
     */
    private boolean createAccount(String email, String password) {
        if (!validateForm()) {
            return valid;
        }

        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(
                this, new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        Log.d(TAG, "userAlreadyExists:" + task.isSuccessful());
                        if (task.getResult().getProviders().size() != 1) {
                            sendEmailVerification();
                            valid = true;
                        } else {
                            Toast.makeText(RegPasswordActivity.this,
                                    "User already exists. Try signing in!", Toast.LENGTH_SHORT).show();
                            valid = false;
                        }
                    }
                });

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Toast.makeText(RegPasswordActivity.this, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show();
                        valid = false;
                    } else {
                        // Save user data after authentication is proven

                        String uid = mAuth.getInstance().getCurrentUser().getUid();
                        user.setUid(uid);
                        DatabaseReference dRef = db.getReference("users");
                        dRef.child(uid).setValue(RegPasswordActivity.this.user);

                        Intent loginIntent = new Intent(RegPasswordActivity.this,
                                HomeActivity.class);
                        RegPasswordActivity.this.startActivity(loginIntent);
                        RegPasswordActivity.this.finish();
                    }
                }
            });
        return true;
    }


    /**
     * Method that checks if form is of correct syntax
     *
     * @return boolean gives back true if form is of correct syntax.
     */
    private boolean validateForm() {
        boolean valid = true;
        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else if (password.length() < 6 || password.length() > 23) {
            passwordField.setError("Password must be between 6 and 23 characters.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    /**
     * Sends user an email for verification.
     * Currently does nothing and is not called.
     *
     */
    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegPasswordActivity.this,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(RegPasswordActivity.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
