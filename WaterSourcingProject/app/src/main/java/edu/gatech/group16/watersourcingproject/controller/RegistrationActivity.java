package edu.gatech.group16.watersourcingproject.controller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
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
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_account_button) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("NAME", nameField.getText());
            intent.putExtra("EMAIL", emailField.getText());
            intent.putExtra("PASSWORD", passwordField.getText());
            intent.putExtra("ACCOUNT TYPE", (String) accountTypeSpinner.getSelectedItem());
            startActivity(intent);

        }
    }
}
