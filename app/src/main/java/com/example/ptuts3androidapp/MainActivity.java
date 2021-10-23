package com.example.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        TessOCR ocr = new TessOCR();
        String text = ocr.getOCRResult(((BitmapDrawable)imgView.getDrawable()).getBitmap());
        textView.setText(text);

    }
}