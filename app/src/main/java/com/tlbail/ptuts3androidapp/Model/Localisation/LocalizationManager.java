package com.tlbail.ptuts3androidapp.Model.Localisation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalizationManager {

    private AppCompatActivity appCompatActivity;
    private LocalizationListener localizationListener;
    private FusedLocationProviderClient locationClient;
    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 1340;


    public LocalizationManager(AppCompatActivity activity, LocalizationListener localizationListener) {
        this.localizationListener = localizationListener;
        this.appCompatActivity = activity;
        locationClient = LocationServices.getFusedLocationProviderClient(appCompatActivity);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appCompatActivity.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
    }

    public void getLocation() {
        requestPermission();
        if (ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationClient.getCurrentLocation(LocationRequest.PRIORITY_LOW_POWER, null).addOnSuccessListener(
                location -> localizationListener.onLocationReceived(getCityFromLocation(location))
        );
    }

    private String getCityFromLocation(Location location){
        if(location == null)
            return null;
        List<Address> addressList = getAddressList(location);
        if(addressList == null || addressList.isEmpty())
            return "";
        return addressList.get(0).getLocality();
    }

    public List<Address> getAddressList(Location location){
        Geocoder geocoder = new Geocoder(appCompatActivity, Locale.getDefault());
        try {
            return geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
