package edu.gatech.group16.watersourcingproject.controller.login;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.Enums.AccountType;
import edu.gatech.group16.watersourcingproject.model.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RegAccountTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner accTypeSpinner;
    private User user = new User();

    private static final String TAG = "AccountTypeReg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_account_type);

        accTypeSpinner = (Spinner) findViewById(R.id.reg_spin_acctype);

        findViewById(R.id.reg_button_continue).setOnClickListener(this);
        ArrayAdapter<AccountType> adaptAcc
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.legalClass);
        accTypeSpinner.setAdapter(adaptAcc);

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.reg_button_continue) {
            user.setAccountType((AccountType) accTypeSpinner.getSelectedItem());
            Intent intent = new Intent(this, RegNameActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            this.finish();
        }
    }
}
