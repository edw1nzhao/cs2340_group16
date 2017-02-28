package edu.gatech.group16.watersourcingproject.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.controller.HomeActivity;
import edu.gatech.group16.watersourcingproject.model.User;

/**
 * Created by Edwin Zhao on 2017/02/22.
 */
public class RegPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EMAIL/PASSWORD";
    private EditText passwordField;
    private Button continueButton;

    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_registration_password);

        passwordField = (EditText) findViewById(R.id.reg_text_password);
        findViewById(R.id.reg_button_signup).setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("USER");

        mAuth = FirebaseAuth.getInstance();
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
            user.setPassword(passwordField.getText().toString());

            if (createAccount(user.getEmail(), user.getPassword())) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = db.getReference();
                DatabaseReference newRef = dbRef.child("users").push();
                newRef.setValue(user);

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                RegPasswordActivity.this.finish();
            }
        }
    }
    private boolean valid = false;

    private boolean createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
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
                    }
                }
            });
        return valid;
    }

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
