package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievements;
import com.tlbail.ptuts3androidapp.Model.Achievement.GoogleAchievementManager;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.OCRFromObjectDetector;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.OnPanneauResultFinishListener;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.ResultScan;
import com.tlbail.ptuts3androidapp.R;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity implements OnPanneauResultFinishListener, OnLocationFind {

    private Bitmap bitmapPhoto;
    private ImageView imageView;
    private OCRFromObjectDetector ocrFromObjectDetector;
    private TextView textView;
    private GoogleAchievementManager achievementManager;

    private LocalisationManager localisationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getDataFromPhotoActivity();
        setContentView(R.layout.activity_result);
        bindUI();
        Achievements achievements = new Achievements(this);
        achievements.unlockAchivement(getString(R.string.achievement_le_commencement));
        startLocation();
    }

    private void getDataFromPhotoActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Uri bitampUri = Uri.parse(extras.getString(PhotoActivity.URIBITMAPKEY));
            try {
                bitmapPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), bitampUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "erreur tu fait quoi beater", Toast.LENGTH_LONG);
            returnToPhotoActivity();
        }
    }

    private void returnToPhotoActivity() {
        Intent activityIntent = new Intent(this, PhotoActivity.class);
        this.startActivity(activityIntent);
    }

    private void bindUI() {
        imageView = findViewById(R.id.resultedPhotoTakenImageView);
        textView = findViewById(R.id.resutlTextview);
    }


    @Override
    protected void onResume() {
        super.onResume();
        imageView.setImageBitmap(bitmapPhoto);
        startOCR();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            localisationManager.onResumeActivity();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        localisationManager.desabonnementGPS();
    }

    private void startOCR() {
        ocrFromObjectDetector = new OCRFromObjectDetector(getApplicationContext());
        ocrFromObjectDetector.runObjetDetectionAndOCR(bitmapPhoto, this);
    }

    private void startLocation(){
        localisationManager = new LocalisationManager(this);
        localisationManager.requestPermission();
    }

    @Override
    public void onPanneauResultFinishListener(ResultScan resultScan) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(" Object Detection : " + resultScan.getObjectDetectionTextResult()
                        + "\n Ocr Detection : " + resultScan.getOcrDetectionTextResult());
            }
        });
    }

    @Override
    public void isLocationFound(String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText()
                        + "\n Localisation trouvée : " + result);
            }
        });
    }
}