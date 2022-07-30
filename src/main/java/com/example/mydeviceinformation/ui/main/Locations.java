package com.example.mydeviceinformation.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mydeviceinformation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

class LocationDetails {
    double latitude;
    double longitude;
    double altitude;
    double accuracy;
    double speed;
    LocationDetails(double lat, double lon, double alt, double acc, double spe)
    {
        latitude = lat;
        longitude = lon;
        altitude = alt;
        accuracy = acc;
        speed = spe;
    }
}

public class Locations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Location.
     */
    // TODO: Rename and change types and number of parameters
    public static Locations newInstance(String param1, String param2) {
        Locations fragment = new Locations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // method to request for permissions
    private void requestPermissions() {
        int PERMISSION_ID = 44;
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    @SuppressLint("MissingPermission")
    private LocationDetails getLastLocation(FusedLocationProviderClient mFusedLocationClient) {

        LocationDetails loc = new LocationDetails(0,0,0,0,0);
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData(mFusedLocationClient);
                        } else {
                            loc.latitude = location.getLatitude();
                            loc.longitude = location.getLongitude();
                            loc.altitude  = location.getAltitude();
                            loc.accuracy = location.getAccuracy();
                            loc.speed = location.getSpeed();
                        }
                    }
                });
            } else {
                Toast.makeText(this.getContext().getApplicationContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
        return loc;
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(FusedLocationProviderClient mFusedLocationClient) {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(60);
        mLocationRequest.setFastestInterval(30);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            TextView lat = (TextView) ((Activity) getContext()).findViewById(R.id.latitude_output);
            TextView lon = (TextView) ((Activity) getContext()).findViewById(R.id.longitude_output);
            TextView alt = (TextView) ((Activity) getContext()).findViewById(R.id.altitude_output);
            TextView acc = (TextView) ((Activity) getContext()).findViewById(R.id.accuracy_output);
            TextView spe = (TextView) ((Activity) getContext()).findViewById(R.id.speed_output);
            lat.setText(String.format(Locale.getDefault(),"%.6f",mLastLocation.getLatitude()));
            lon.setText(String.format(Locale.getDefault(),"%.6f",mLastLocation.getLongitude()));
            alt.setText(String.format(Locale.getDefault(),"%.0f",mLastLocation.getAltitude()));
            acc.setText(String.format(Locale.getDefault(),"%.0f",mLastLocation.getAccuracy()));
            spe.setText(String.format(Locale.getDefault(),"%.0f",mLastLocation.getSpeed()));
        }
    };
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_location, container, false);
        TextView latitude = (TextView) v.findViewById(R.id.latitude_output);
        TextView longitude = (TextView) v.findViewById(R.id.longitude_output);
        TextView altitude = (TextView) v.findViewById(R.id.altitude_output);
        TextView accuracy = (TextView) v.findViewById(R.id.accuracy_output);
        TextView speed = (TextView) v.findViewById(R.id.speed_output);

        FusedLocationProviderClient mFusedLocationClient;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        LocationDetails loc = getLastLocation(mFusedLocationClient);
        latitude.setText(String.format(Locale.getDefault(),"%.6f",loc.latitude));
        longitude.setText(String.format(Locale.getDefault(),"%.6f",loc.longitude));
        altitude.setText(String.format(Locale.getDefault(),"%.0f",loc.altitude));
        accuracy.setText(String.format(Locale.getDefault(),"%.0f",loc.accuracy));
        speed.setText(String.format(Locale.getDefault(),"%.0f",loc.speed));
        return v;
    }
}