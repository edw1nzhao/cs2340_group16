package edu.gatech.group16.watersourcingproject.controller.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.gatech.group16.watersourcingproject.R;

/**
 * Created by Edwin Zhao on 2017/04/07.
 */

public class BanUserActivity extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_ban_user);

        setupUI();

    }

    @Override
    public void onClick(View v) {

    }

    private void setupUI() {

    }
}
