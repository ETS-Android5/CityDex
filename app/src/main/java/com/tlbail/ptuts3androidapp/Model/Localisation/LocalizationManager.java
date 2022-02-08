package com.tlbail.ptuts3androidapp.Model.Localisation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tlbail.ptuts3androidapp.Model.PanneauVersVille.PhotoToCity;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalizationManager {

    private AppCompatActivity appCompatActivity;
    private LocalizationListener localizationListener;
    private FusedLocationProviderClient locationClient;


    public LocalizationManager(PhotoToCity activity) {
        localizationListener = activity;
        this.appCompatActivity = activity.getAppCompatActivity();
        locationClient = LocationServices.getFusedLocationProviderClient(appCompatActivity);
    }

    public void getLocation() {
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
