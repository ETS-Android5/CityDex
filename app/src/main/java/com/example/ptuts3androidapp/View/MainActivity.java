package com.example.ptuts3androidapp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;

import com.example.ptuts3androidapp.Controller.PhotoManager;
import com.example.ptuts3androidapp.Controller.UserManager;
import com.example.ptuts3androidapp.R;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private PhotoManager photoManager;
    private UserManager userManager;
    private EditText cityNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        photoManager = new PhotoManager(this);
        userManager = new UserManager(getApplicationContext());

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
        findViewById(R.id.seeAllPhotoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity();
            }
        });
        cityNameEditText = findViewById(R.id.cityNameEditText);


        findViewById(R.id.ajouterVilleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCity();
            }
        });
    }

    public void addCity(){
        System.out.println("ajout de la ville");
        userManager.addCity(photoManager.getPhotoUri(), cityNameEditText.getText().toString());
    }


    private void startNextActivity(){
        Intent myIntent = new Intent(this, AllcityActivity.class);
        startActivity(myIntent);
    }


}