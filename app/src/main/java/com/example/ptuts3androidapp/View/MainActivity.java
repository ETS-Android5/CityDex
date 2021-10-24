package com.example.ptuts3androidapp.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ptuts3androidapp.Model.Photo.PhotoManager;
import com.example.ptuts3androidapp.Model.Photo.PhotoTaker;
import com.example.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyFileLoader;
import com.example.ptuts3androidapp.Model.User.User;
import com.example.ptuts3androidapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private PhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        photoManager = new PhotoManager(this);
    }

    private void bindUI() {
        imageView = findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoManager.takePhoto();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoManager.loadPhotoIntoImageView(imageView);
            }
        });
        findViewById(R.id.buttonStorePhotoUri).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoManager.storeLastPhotoPathTakedInLocalStorage();
            }
        });
        findViewById(R.id.buttonGetPhotoUri).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoManager.loadLastPhotoTakedFromLocalStorage();
            }
        });
    }



}