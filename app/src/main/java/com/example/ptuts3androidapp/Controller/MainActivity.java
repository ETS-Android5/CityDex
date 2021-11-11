package com.example.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ptuts3androidapp.Model.DetectionTextPanneau.OCRFromObjectDetector;
import com.example.ptuts3androidapp.R;

public class MainActivity extends AppCompatActivity {

    private TextureView textureView;
    private TextView result;
    private Button button;
    private ImageView img;

    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private int permCode = 23;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},permCode);
        }
        setContentView(R.layout.activity_main);
        bindUI();
        setupObjetAndOCRDetection();
    }

    private void setupObjetAndOCRDetection() {
        OCRFromObjectDetector OCR_Object = new OCRFromObjectDetector(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOCRAndTextDetection(OCR_Object);
            }
        });
    }

    private void runOCRAndTextDetection(OCRFromObjectDetector OCR_Object) {
        textureView = findViewById(R.id.textureView);
        Bitmap bitmap = textureView.getBitmap();
        OCR_Object.runObjetDetectionAndOCR(bitmap, result, img);
    }

    private void bindUI() {
        result = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        img = findViewById(R.id.bitmap);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}