package edu.gatech.group16.watersourcingproject.controller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;

public class ReportDetailsActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_report_details);

        user = (User) getIntent().getSerializableExtra("USER");

    }
}
