package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievements;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.PanneauVersVille.CityFoundListener;
import com.tlbail.ptuts3androidapp.Model.PanneauVersVille.PhotoToCity;
import com.tlbail.ptuts3androidapp.R;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity implements CityFoundListener {

    private ImageView imageView;
    private TextView textView;
    private Button buttonNext;
    private PhotoToCity photoToCityDecorator;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bindUI();
        Achievements achievements = new Achievements(this);
        photoUri = getUri();
        if(photoUri == null) return;
        photoToCityDecorator = new PhotoToCity(this);
        photoToCityDecorator.subscribeOnCityFound(this);
        photoToCityDecorator.subscribeOnCityFound(achievements);
        photoToCityDecorator.start(uriToBitmap(photoUri), photoUri);
    }

    private Bitmap uriToBitmap(Uri uri){
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri getUri() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return Uri.parse(extras.getString(PhotoActivity.URIBITMAPKEY));
        } else {
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
        imageView.setImageBitmap(uriToBitmap(photoUri));
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        Intent activityIntent = new Intent(ResultActivity.this, PhotoActivity.class);
        ResultActivity.this.startActivity(activityIntent);
    }

    @Override
    public void onCityFound(City city) {
        buttonNext.setOnClickListener(v -> goToCollectionActivity());
        runOnUiThread(() -> {
            textView.setText(city.toString());
            buttonNext.setText("Voir ma ville");
        });
    }

    @Override
    public void onCityNotFound() {
        buttonNext.setOnClickListener(v -> returnToPhotoActivity());
        runOnUiThread(()->{
            textView.setText("ECHEC-CITY");
            buttonNext.setText("Reprendre une photo");
        });
    }

    private void goToCollectionActivity() {
        Intent activityIntent = new Intent(this, CollectionActivity.class);
        this.startActivity(activityIntent);
    }

}