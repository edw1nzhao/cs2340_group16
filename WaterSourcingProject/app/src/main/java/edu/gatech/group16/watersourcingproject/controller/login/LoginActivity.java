package edu.gatech.group16.watersourcingproject.controller.login;

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
import com.google.firebase.database.DatabaseReference;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.User;

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
        private final List<User> users2 = new ArrayList<User>();

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = db.getReference();

//
//            dbRef.child("users").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    //Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//
//                    for (DataSnapshot child: dataSnapshot.getChildren()) {
//                        User user = child.getValue(User.class);
//                        users.add(user);
//                    }
//
//                    Log.d("SIZE DURING: ", users.size() + "");
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

        users2.add(new User("edwin.zhao@gatech.edu", "asdfasdf", "Edwin Zhao", AccountType.ADMINISTRATOR, null));
        users2.add(new User("feehan.tomonari@gmail.com", "password", "Tomonari", AccountType.ADMINISTRATOR, null));

            Log.d("SIZE AFTER: ", users.size() + ""); // Expect a number other than 0
            // Views
            mStatusTextView = (TextView) findViewById(R.id.status);
            emailField = (EditText) findViewById(R.id.field_email);
            passwordField = (EditText) findViewById(R.id.field_password);

            // Buttons
            findViewById(R.id.email_create_account_button).setOnClickListener(this);
            findViewById(R.id.sign_out_button).setOnClickListener(this);
            findViewById(R.id.email_sign_in_button).setOnClickListener(this);

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

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validForm()) {
            return;
        }
        final String tempEmail = email;
        final Intent home_activity = new Intent(this, HomeActivity.class);

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
                            User tempUser = null;
                            for (User u: users2) {
                                if (u.getEmail().equals(tempEmail)) {
                                    tempUser = u;
                                }
                            }

                            home_activity.putExtra("USER", tempUser);

                            startActivity(home_activity);
                            finish();
                        }
                    }
                });
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
            if (validForm()) {
                Intent intent = new Intent(this, RegAccountTypeActivity.class);
                startActivity(intent);
            }
        } else if (i == R.id.email_sign_in_button) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        }
    }

    public boolean validForm() {
        boolean valid = true;
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(email)) {
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

        if (TextUtils.isEmpty(password)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
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
