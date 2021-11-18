package com.example.ptuts3androidapp.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import com.example.ptuts3androidapp.Model.DetectionTextPanneau.OCRFromObjectDetector;
import com.example.ptuts3androidapp.Model.DetectionTextPanneau.OnPanneauResultFinishListener;
import com.example.ptuts3androidapp.Model.DetectionTextPanneau.ResultScan;
import com.example.ptuts3androidapp.R;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity implements OnPanneauResultFinishListener, LocationListener {

    private LocationManager locationManager;
    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 1340;

    private Bitmap bitmapPhoto;
    private ImageView imageView;
    private OCRFromObjectDetector ocrFromObjectDetector;
    private TextView textView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromPhotoActivity();
        setContentView(R.layout.activity_result);
        bindUI();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
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

        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //On appelle la méthode pour se désabonner
        desabonnementGPS();
    }

    private void startOCR() {
        ocrFromObjectDetector = new OCRFromObjectDetector(getApplicationContext());
        ocrFromObjectDetector.runObjetDetectionAndOCR(bitmapPhoto, this);
    }

    @Override
    public void onPanneauResultFinishListener(ResultScan resultScan) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(" object detection : " + resultScan.getObjectDetectionTextResult()
                        + " ocr detection " + resultScan.getOcrDetectionTextResult());
            }
        });
    }

    /**
     * Méthode permettant de s'abonner à la localisation par GPS.
     */
    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    /**
     * Méthode permettant de se désabonner de la localisation par GPS.
     */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(final Location location) {
        //On affiche dans un message la nouvelle Localisation
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());

        Log.i("Localisation", "" + msg);
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