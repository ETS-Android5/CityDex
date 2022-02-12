package com.tlbail.ptuts3androidapp.Model.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocationListener;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocationManager;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

public class NotificationManager implements LocationListener {
    private Notification mNotification;
    private NotificationManagerCompat notificationManagerCompat;
    private User user;
    Context context;

    public static final String CHANNEL_1_ID = "channel1";

    private LocationManager locationManager;

    public NotificationManager(AppCompatActivity activity) {
        locationManager = new LocationManager(activity, this);
        context = activity.getApplicationContext();
        user = new User(new UserPropertyLocalLoader(context), new CityLocalLoader(context));
        notificationManagerCompat = NotificationManagerCompat.from(context);

        locationManager.getLocation();

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


    @Override
    public void onLocationReceived(String location) {
        if(user.isCityAlreadyOwned(location)) return;
        createNotification(location);
    }

}
