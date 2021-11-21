package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.Achievement.GoogleAchievementManager;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.OCRFromObjectDetector;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.OnPanneauResultFinishListener;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.ResultScan;
import com.example.ptuts3androidapp.R;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity implements OnPanneauResultFinishListener {

    private Bitmap bitmapPhoto;
    private ImageView imageView;
    private OCRFromObjectDetector ocrFromObjectDetector;
    private TextView textView;
    private GoogleAchievementManager achievementManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromPhotoActivity();
        setContentView(R.layout.activity_result);
        bindUI();
        achievementManager = GoogleAchievementManager.getInstance();
        if(!achievementManager.isSigned()) achievementManager.signInSilently(this);
        achievementManager.unlockAchievement(R.id.activityResultLayout, getString(R.string.achievement_le_commencement));
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

    private void bindUI() {
        imageView = findViewById(R.id.resultedPhotoTakenImageView);
        textView = findViewById(R.id.resutlTextview);
    }


    @Override
    protected void onResume() {
        super.onResume();
        imageView.setImageBitmap(bitmapPhoto);
        startOCR();
    }

    private void startOCR(){
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
}