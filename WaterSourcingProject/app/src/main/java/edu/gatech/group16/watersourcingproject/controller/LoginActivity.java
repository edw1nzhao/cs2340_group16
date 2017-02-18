package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ProviderQueryResult;

import edu.gatech.group16.watersourcingproject.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnClickListener {

        private static final String TAG = "EmailPassword";

        private TextView mStatusTextView;
        private EditText emailField;
        private EditText passwordField;

        private FirebaseAuth mAuth;

        private FirebaseAuth.AuthStateListener mAuthListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            // Views
            mStatusTextView = (TextView) findViewById(R.id.status);
            emailField = (EditText) findViewById(R.id.field_email);
            passwordField = (EditText) findViewById(R.id.field_password);

            // Buttons
            findViewById(R.id.email_sign_in_button).setOnClickListener(this);
            findViewById(R.id.email_create_account_button).setOnClickListener(this);
            findViewById(R.id.sign_out_button).setOnClickListener(this);
            findViewById(R.id.verify_email_button).setOnClickListener(this);

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

        @Override
        public void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }

        @Override
        public void onStop() {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }

        private void createAccount(String email, String password) {
            Log.d(TAG, "createAccount:" + email);
            if (!validateForm()) {
                return;
            }

            mAuth.fetchProvidersForEmail(email).addOnCompleteListener(
                    this, new OnCompleteListener<ProviderQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                    Log.d(TAG, "userAlreadyExists:" + task.isSuccessful());
                    if (task.getResult().getProviders().size() != 1) {
                        sendEmailVerification();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "User already exists. Try signing in!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        final Intent login_intent = new Intent(this, HomeActivity.class);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
                            login_intent.putExtra("EMAIL", emailField.getText().toString());
                            startActivity(login_intent);
                            finish();
                        }


                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

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

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else if (email.length() < 6) {
            emailField.setError("Incorrect format.");
            valid = false;
        } else if (!email.substring(email.length() - 4).equals(".com")) {
            emailField.setError("Incorrect format.");
            valid = false;
        } else if (email.substring(email.length() - 5).equals("@.com")) {
            emailField.setError("Incorrect format.");
            valid = false;
        } else {
            emailField.setError(null);
        }

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

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));

        } else {
            mStatusTextView.setText(R.string.signed_out);

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            ////////////////////////////////////////////////////
            ////////////////////////////////////////////////////
            //TO DO:
            //Move this functionality to RegistrationActivity:

            //createAccount(emailField.getText().toString(), passwordField.getText().toString());

            ////////////////////////////////////////////////////
            ////////////////////////////////////////////////////
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);


        } else if (i == R.id.email_sign_in_button) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.sign_out_button) {
            signOut();
        }
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
