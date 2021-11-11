package com.example.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ptuts3androidapp.R;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private Bitmap bitmapPhoto;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromPhotoActivity();
        setContentView(R.layout.activity_result);
        bindUI();
    }

    private void bindUI() {
        imageView = findViewById(R.id.resultedPhotoTakenImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView.setImageBitmap(bitmapPhoto);

    }

    private void getDataFromPhotoActivity() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Uri bitampUri = Uri.parse(extras.getString(PhotoActivity.URIBITMAPKEY));
            try {
                bitmapPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(),bitampUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "erreur tu fait quoi beater", Toast.LENGTH_LONG);
            returnToPhotoActivity();
        }
    }

    private void returnToPhotoActivity() {
        Intent activityIntent = new Intent(this, PhotoActivity.class);
        this.startActivity(activityIntent);
    }
}