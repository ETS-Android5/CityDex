package com.tlbail.ptuts3androidapp.Model.Notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.tlbail.ptuts3androidapp.R;

import java.io.IOException;

public class NotificationManager {

    private Context context;
    private Notification mNotification;
    private NotificationManagerCompat notificationManagerCompat;

    public static final String CHANNEL_1_ID = "channel1";

    private FusedLocationProviderClient client;
    public final int INTERVAL = 3000;

    public NotificationManager(Context context) {
        this.context = context;
        notificationManagerCompat = NotificationManagerCompat.from(context);
        client = LocationServices.getFusedLocationProviderClient(context);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER).
                setInterval(INTERVAL).
                setMaxWaitTime(INTERVAL).
                setExpirationDuration(INTERVAL).
                setFastestInterval(INTERVAL/2);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                createNotification(addressFromLocation(locationResult.getLastLocation()));
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private String addressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(context);
        String address = "";
        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    public void createNotification(String address) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    android.app.NotificationManager.IMPORTANCE_HIGH
            );

            android.app.NotificationManager manager = context.getSystemService(android.app.NotificationManager.class);
            manager.createNotificationChannel(channel);

            sendNotification(address);
        }
    }

    private void sendNotification(String address)  {

        mNotification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.app_icon).setColor(Color.RED)
                .setContentTitle("Nouvelle ville disponible !")
                .setContentText("Vous vous trouvez dans une nouvelle ville ! " +
                        "Ajouter " + address + " à votre collection !")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        notificationManagerCompat.notify(notificationId, mNotification);
    }



}
