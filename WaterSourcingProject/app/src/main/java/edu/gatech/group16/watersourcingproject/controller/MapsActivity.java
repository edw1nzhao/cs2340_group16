package edu.gatech.group16.watersourcingproject.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.gatech.group16.watersourcingproject.R;
import edu.gatech.group16.watersourcingproject.model.User;
import edu.gatech.group16.watersourcingproject.model.WaterSourceReport;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private User user = new User();
    private Toolbar toolbar;
    private TextView reportInfo;
    private static final String TAG = "MapsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        reportInfo = (TextView) findViewById(R.id.textview_report_info);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        user = (User) getIntent().getSerializableExtra("USER");
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

        try {
            for (WaterSourceReport report: user.getWaterSourceReport()) {
                String[] split = report.getLocation().split(",");
                LatLng location = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                String snippetText =   "Submitted By: " + report.getSubmittedBy()
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
            Log.d(TAG, "User has no reports");
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        reportInfo.setText(marker.getSnippet().toString());
        return true;
    }
}
