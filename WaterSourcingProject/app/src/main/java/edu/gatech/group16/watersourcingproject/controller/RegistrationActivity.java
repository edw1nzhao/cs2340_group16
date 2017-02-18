package edu.gatech.group16.watersourcingproject.controller;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.AccountType;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner accountTypeSpinner;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        accountTypeSpinner = (Spinner) findViewById(R.id.account_type_spinner);
        nameField = (EditText) findViewById(R.id.registration_name);
        emailField = (EditText) findViewById(R.id.registration_email);
        passwordField = (EditText) findViewById(R.id.registration_password);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, AccountType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        findViewById(R.id.create_account_button).setOnClickListener(this);

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
                //updateUI(user);
            }
        };
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_account_button) {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());
            if (counter == 1) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("NAME", nameField.getText());
                intent.putExtra("EMAIL", emailField.getText());
                intent.putExtra("PASSWORD", passwordField.getText());
                intent.putExtra("ACCOUNT TYPE", (String) accountTypeSpinner.getSelectedItem());
                startActivity(intent);
            }
        }
    }
    int counter = 0;
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
                            Toast.makeText(RegistrationActivity.this,
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
                            Toast.makeText(RegistrationActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            counter++;
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegistrationActivity.this,
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
}
