package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterPurityReport;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private User user = new User();
    private Toolbar toolbar;
    private TextView reportInfo;
    private static final String TAG = "MapsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        user = (User) getIntent().getSerializableExtra("USER");

        uiSetup();
    }
    /**
     * Sets up all of the necessary ui for this screen.
     */
    private void uiSetup() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        reportInfo = (TextView) findViewById(R.id.textview_report_info);

        toolbar = (Toolbar) findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Adds Google Map Markers for all Water Source Reports.
        try {
            for (WaterSourceReport report: user.getWaterSourceReport()) {
                String[] split = report.getLocation().split(",");
                LatLng location = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                String snippetText = "Report Number: " + report.getReportNumber()
                        + "\n\nSubmitted By: " + report.getSubmittedBy()
                        + "\n\nDate: " + report.getDate()
                        + "\n\nLocation: " + report.getLocation()
                        + "\n\nWater Type: " + report.getWaterType()
                        + "\n\nWater Condition: " + report.getWaterCondition() + "\n\n";


                mMap.addMarker(new MarkerOptions().position(location).title("Report Number: " + report.getReportNumber()).snippet(snippetText));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mMap.setOnMarkerClickListener(this);
                //marker.showInfoWindow();
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "User has no Water Source Reports.");
        }

        //Adds Google Map Markers for all Water Purity Reports.
        try {
            for (WaterPurityReport report: user.getWaterPurityReport()) {
                String[] split = report.getLocation().split(",");
                LatLng location = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                String snippetText = "Report Number: " + report.getReportNumber()
                        + "\n\nSubmitted By: " + report.getSubmittedBy()
                        + "\n\nDate: " + report.getDate()
                        + "\n\nLocation: " + report.getLocation()
                        + "\n\nOverall Condition: " + report.getOverallCondition()
                        + "\n\nVirus PPM: " + report.getVirusPPM()
                        + "\n\nContaminant PPM: " + report.getContaminantPPM() + "\n\n";


                mMap.addMarker(new MarkerOptions().position(location).title("Report Number: " + report.getReportNumber()).snippet(snippetText).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mMap.setOnMarkerClickListener(this);
                //marker.showInfoWindow();
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "User has no Water Purity Reports.");
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        reportInfo.setText(marker.getSnippet().toString());
        return true;
    }
}
