package com.example.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        textView.setText("test");
        TessOCR ocr = null;
        try {
            ocr = new TessOCR(getAssets().open("fra.traineddata"));
    } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable drawable = imgView.getDrawable();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        String result = null;
        try {
            result = ocr.getOCRResult(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("test", result);
        textView.setText(result);
    }
}