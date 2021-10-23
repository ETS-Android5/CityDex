package com.example.ptuts3androidapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.example.ptuts3androidapp.Model.Photo.PhotoTaker;
import com.example.ptuts3androidapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.polish_cow);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        PhotoTaker photoTaker = new PhotoTaker((AppCompatActivity) this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoTaker.takePhoto();
            }
        });

    }



}