package edu.gatech.group16.watersourcingproject.controller.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.group16.watersourcingproject.R;

/**
 * Created by Edwin Zhao on 2017/04/07.
 */

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private Button banButton;
    private Button removeReportButton;

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
        setContentView(R.layout.activity_admin);

        setupUI();
    }

    @Override
    public void onClick(View v) {

    }

    private void setupUI() {
        banButton = (Button) findViewById(R.id.banUser);
        removeReportButton = (Button) findViewById(R.id.removeReport);
        buttonLogic();
    }

    private void buttonLogic() {
        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent banIntent = new Intent(AdminActivity.this, BanUserActivity.class);
                startActivity(banIntent);
                AdminActivity.this.finish();
            }
        });

        removeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent removeRepIntent = new Intent(AdminActivity.this, RemoveReportActivity.class);
                startActivity(removeRepIntent);
                AdminActivity.this.finish();
            }
        });
    }
}
