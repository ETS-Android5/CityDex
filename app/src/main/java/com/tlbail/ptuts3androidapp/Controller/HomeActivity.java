package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.Notification.NotificationManager;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundRecyclerView;


public class HomeActivity extends AppCompatActivity {

    private BackgroundRecyclerView backgroundRecyclerView;

    private NotificationManager mNotificationManager;

    private String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundRecyclerView = new BackgroundRecyclerView(this);
        setContentView(R.layout.activity_home);
        bindUI();
        requestPermissions(perms, PackageManager.PERMISSION_GRANTED);
        mNotificationManager = new NotificationManager(this);
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
        findViewById(R.id.map_button).setOnClickListener(v -> startIntent(MapsActivity.class));
        findViewById(R.id.photo_button).setOnClickListener(v -> startPhotoActivity());

        mNotificationManager = new NotificationManager(this);
    }

    public void startIntent(Class className){
        Intent activityIntent = new Intent(this, className);
        this.startActivity(activityIntent);
    }

    private void startPhotoActivity() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Acceptez les conditions et réessayez ! ", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
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