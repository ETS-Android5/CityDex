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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundRecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private BackgroundRecyclerView backgroundRecyclerView;

    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManagerCompat;

    private User user;
    private List<City> cities;

    private boolean hasCity = false;
    private LocalisationManager mLocalisationManager;
    private String adresse;

    private boolean hasfinish = false;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        backgroundRecyclerView = new BackgroundRecyclerView(this);
        setContentView(R.layout.activity_home);
        bindUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundRecyclerView.start(findViewById(R.id.homeRecyclerView));
    }

    private void bindUI() {
        findViewById(R.id.collec_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, CollectionActivity.class);
                HomeActivity.this.startActivity(activityIntent);
            }
        });
        findViewById(R.id.succes_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, AchievementActivity.class);
                HomeActivity.this.startActivity(activityIntent);

            }
        });
        findViewById(R.id.reglage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, OptionsActivity.class);
                HomeActivity.this.startActivity(activityIntent);

            }
        });
        findViewById(R.id.photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhotoActivity();

            }
        });

        user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));
        cities = user.getOwnedCity();
    }

    private void startPhotoActivity() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Acceptez les conditions et réessayez ! ", Toast.LENGTH_LONG).show();
            return;
        }
        Intent activityIntent = new Intent(HomeActivity.this, PhotoActivity.class);
        HomeActivity.this.startActivity(activityIntent);
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}