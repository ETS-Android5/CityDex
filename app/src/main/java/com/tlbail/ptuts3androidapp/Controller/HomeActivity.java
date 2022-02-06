package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocationTrack;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundRecyclerView;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private BackgroundRecyclerView backgroundRecyclerView;

    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManagerCompat;
    private boolean hasCity = false;

    private User user;
    private List<City> cities;

    private LocationTrack location;
    private Geocoder geocoder;
    private Address adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        backgroundRecyclerView = new BackgroundRecyclerView(this);
        setContentView(R.layout.activity_home);
        bindUI();

        if(location.canGetLocation()){
            List<Address> adresses = null;

            try
            {
                adresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            }catch (IOException ioException)
            {
                Log.e("GPS", "erreur", ioException);
            }
            catch (IllegalArgumentException illegalArgumentException)
            {
                Log.e("GPS", "erreur ", illegalArgumentException);
            }

            if (adresses == null || adresses.size()  == 0)
            {
                Log.e("GPS", "erreur aucune adresse !");
            }
            else
            {
                adresse = adresses.get(0);
               System.out.println(adresse);
            }
        }

        for (City city: cities) {
            if (city.getCityData().getName().equalsIgnoreCase(adresse.getLocality())){
               hasCity = true;
            }
        }

        if(!hasCity){
            notificationManagerCompat = NotificationManagerCompat.from(this);
            createNotif();
        }
    }

    private void createNotif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            sendNotif();
        }
    }

    private void sendNotif()  {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.app_icon).setColor(Color.RED)
                .setContentTitle("Nouvelle ville disponible !")
                .setContentText("Vous vous trouvez dans une nouvelle ville ! " +
                        "Ajouter " + adresse.getLocality() + " à votre collection !")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundRecyclerView.start(findViewById(R.id.homeRecyclerView));
    }

    private void bindUI() {
        findViewById(R.id.collec_button).setOnClickListener(v -> startIntent(CollectionActivity.class));
        findViewById(R.id.succes_button).setOnClickListener(v -> startIntent(AchievementActivity.class));
        findViewById(R.id.reglage_button).setOnClickListener(v -> startIntent(OptionsActivity.class));
        findViewById(R.id.photo_button).setOnClickListener(v -> startPhotoActivity());
    }

    public void startIntent(Class className){
        Intent activityIntent = new Intent(this, className);
        this.startActivity(activityIntent);
        user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));
        cities = user.getOwnedCity();
        geocoder = new Geocoder(this);
        location = new LocationTrack(this);
    }

    private void startPhotoActivity() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Acceptez les conditions et réessayez ! ", Toast.LENGTH_LONG).show();
            return;
        }
        startIntent(PhotoActivity.class);
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}