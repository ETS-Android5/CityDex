package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievements;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.CityFoundListener;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.PhotoToCity;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.PhotoToCityDecorator;
import com.tlbail.ptuts3androidapp.R;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity implements  CityFoundListener {

    private ImageView imageView;
    private TextView textView;
    private Button buttonNext;
    private PhotoToCityDecorator photoToCityDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_result);
        bindUI();
        Achievements achievements = new Achievements(this);
        Uri uri = getUri();
        if(uri == null) return;
        photoToCityDecorator = new PhotoToCityDecorator(this, uri);
        photoToCityDecorator.subscribeOnCityFound(this);
        photoToCityDecorator.subscribeOnCityFound(achievements);
        photoToCityDecorator.start();

    }

    private Uri getUri() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return Uri.parse(extras.getString(PhotoActivity.URIBITMAPKEY));
        } else {
            Toast.makeText(getApplicationContext(), "erreur tu fait quoi beater", Toast.LENGTH_LONG);
            returnToPhotoActivity();
            return null;
        }
    }

    private void returnToPhotoActivity() {
        Intent activityIntent = new Intent(this, PhotoActivity.class);
        this.startActivity(activityIntent);
    }


    private void bindUI() {
        imageView = findViewById(R.id.resultedPhotoTakenImageView);
        textView = findViewById(R.id.resutlTextview);
        buttonNext = findViewById(R.id.buttonBackresultActivity);
    }


    @Override
    protected void onResume() {
        super.onResume();
        imageView.setImageBitmap(photoToCityDecorator.getBitmap());
        photoToCityDecorator.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        photoToCityDecorator.onPause();
    }


    @Override
    public void onBackPressed() {
        Intent activityIntent = new Intent(ResultActivity.this, PhotoActivity.class);
        ResultActivity.this.startActivity(activityIntent);
    }

    @Override
    public void onCityFoundt(City city) {
        setButtonValueByCityValue(city);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(city != null){
                    textView.setText(city.toString());
                    buttonNext.setText("voire ma ville");
                }else {
                    textView.setText("desoler aucune ville de trouvé");
                    buttonNext.setText("reprendre une photo");
                }
            }
        });

    }

    private void setButtonValueByCityValue(City city) {
        if(city != null){
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToCollectionActivity();
                }
            });
        }else{
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnToPhotoActivity();
                }
            });
        }
    }

    private void goToCollectionActivity() {
        Intent activityIntent = new Intent(this, CollectionActivity.class);
        this.startActivity(activityIntent);
    }

}