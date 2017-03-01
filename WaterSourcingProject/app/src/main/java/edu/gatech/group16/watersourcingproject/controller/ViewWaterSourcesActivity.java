package edu.gatech.group16.watersourcingproject.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;
import edu.gatech.group16.watersourcingproject.controller.ReportDetailsActivity;

public class ViewWaterSourcesActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<String>();
    private ListView listView ;
    private User user;
    private String[] values;

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
        setContentView(R.layout.activity_view_water_sources);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.report_list);

        user = (User) getIntent().getSerializableExtra("USER");

        List<WaterSourceReport> reportList = user.getWaterSourceReport();

        List<String> reportNums = new ArrayList<String>();
        Log.d("SO CLOSE", reportList.toString());

        for (WaterSourceReport item: reportList) {


            reportNums.add("" + item.getReportNumber());

        }

        Log.d("HIYA", "" + reportNums.toString());

        // Defined Array values to show in ListView
        try {
            values = new String[] {reportList.toString()};
        } catch (NullPointerException e) {
            values = new String[] {};
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, reportNums);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        final Context context = listView.getContext();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                // ListView Clicked item index
//                int itemPosition = position;
//
//                // ListView Clicked item value
//                String itemValue = (String) listView.getItemAtPosition(position);
//
//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                    "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
//                    .show();
                Intent reportDetailIntent = new Intent(context, ReportDetailsActivity.class);
                reportDetailIntent.putExtra("USER", user);
                reportDetailIntent.putExtra("POSITION", position);
                startActivity(reportDetailIntent);
            }
        });
    }
}