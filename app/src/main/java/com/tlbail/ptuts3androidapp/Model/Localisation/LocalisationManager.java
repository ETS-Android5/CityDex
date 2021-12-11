package com.tlbail.ptuts3androidapp.Model.Localisation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tlbail.ptuts3androidapp.Controller.ResultActivity;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.PhotoToCity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalisationManager implements LocationListener {

    private LocationManager locationManager;
    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 1340;
    private StringBuilder msg;
    private String locationFound;
    private PhotoToCity photoToCity;
    private AppCompatActivity appCompatActivity;

    public LocalisationManager(PhotoToCity activity) {
        this.photoToCity = activity;
        this.appCompatActivity = photoToCity.getAppCompatActivity();
    }

    private String getLocationFound() {
        return locationFound;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appCompatActivity.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start(){
        requestPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onResumeActivity() {
        //Obtention de la référence du service
        locationManager =(LocationManager) appCompatActivity.getSystemService(appCompatActivity.LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(gps_enabled && network_enabled) {
            abonnementGPS();
        }
        else {
            Toast.makeText(appCompatActivity.getApplicationContext(), "Active INTERNET et ta LOCALISATION !", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * Méthode permettant de s'abonner à la localisation par GPS.
     */
    private void abonnementGPS() {
        //On s'abonne
        if (ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    @Override
    public void onLocationChanged(final Location location) {

            //On affiche dans un message la nouvelle Localisation
            msg = new StringBuilder("lat : ");
            msg.append(location.getLatitude());
            msg.append("; lng : ");
            msg.append(location.getLongitude());

            Geocoder geoCoder = new Geocoder(appCompatActivity, Locale.getDefault());
            List<Address> list = null;
            try {
                list = geoCoder.getFromLocation(location
                        .getLatitude(), location.getLongitude(), 1);
                if (list != null & list.size() > 0) {
                    Address address = list.get(0);
                    locationFound = address.getLocality();
                    msg.append("; Ville détectée : " + address.getLocality());

                    Log.i("Localisation", msg + "");
                    Log.i("VilleLocation", locationFound);

                    photoToCity.setLocationResult(locationFound);
                } else {
                    try {
                        throw new CityNotFoundException();
                    } catch (CityNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private StringBuilder toStringBuilder(){
        return msg;
    }

    /**
     * Méthode permettant de se désabonner de la localisation par GPS.
     */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est désactivé on se désabonne
        if("gps".equals(provider)) {
            desabonnementGPS();
        }
    }

    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activé on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }
}
