package edu.gatech.group16.watersourcingproject.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

/**
 * Created by Edwin Zhao on 2017/04/24.
 */

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button checkEmail;
    private Button back;
    private EditText emailField;
    private TextView status;

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
        setContentView(R.layout.activity_forgot);
        mAuth = FirebaseAuth.getInstance();

        emailField = (EditText) findViewById(R.id.field_email);
        status = (TextView) findViewById(R.id.status);
        checkEmail = (Button) findViewById(R.id.checkButton);
        back = (Button) findViewById(R.id.backButton);

        back.setOnClickListener(this);
        checkEmail.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.backButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            ForgotActivity.this.finish();
        } else if (i == R.id.checkButton) {
            try {
                findUser(emailField.getText().toString());
            } catch (IllegalArgumentException e) {
                Toast.makeText(ForgotActivity.this,
                        "Please at least check that the form is filled.... ",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void findUser(String email) {
        final String tempEmail = email;
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(
                this, new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if (task.getResult().getProviders().size() != 1) {
                            Toast.makeText(ForgotActivity.this,
                                    "No email exists... Make a new " +
                                            "account!", Toast.LENGTH_SHORT).show();
                        } else {

                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            final DatabaseReference dbRef = db.getReference();

                            //noinspection ChainedMethodCall
                            dbRef.child("users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        User temp = postSnapshot.getValue(User.class);
                                        //noinspection ChainedMethodCall
                                        if (temp.getEmail().equals(tempEmail)) {
                                            status.setText("Your password is: "
                                                    + temp.getPassword());
                                            Toast.makeText(ForgotActivity.this,
                                                    "Found your account! Quick! Take note!",
                                                    Toast.LENGTH_SHORT).show();
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
}
