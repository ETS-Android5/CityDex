package com.example.ptuts3androidapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;

import com.example.ptuts3androidapp.Controller.PhotoManager;
import com.example.ptuts3androidapp.Controller.UserManager;
import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private PhotoManager photoManager;
    private UserManager userManager;
    private EditText cityNameEditText;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        photoManager = new PhotoManager(this);
        userManager = new UserManager(getApplicationContext());

        setRecyclerView();

    }

    private void setRecyclerView() {

        recyclerView = findViewById(R.id.recyclerViewBackground);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.polish_cow);


        List<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);
        bitmaps.add(icon);

        BackgroundViewAdapter backgroundViewAdapter = new BackgroundViewAdapter(bitmaps);

        recyclerView.setAdapter(backgroundViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.smoothScrollToPosition(backgroundViewAdapter.getItemCount());



        autoScroll();


    }


    public void autoScroll() {
        final int speedScroll = 3000;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = -1;

            @Override
            public void run() {
                if (count < recyclerView.getAdapter().getItemCount()) {
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
                if (count == recyclerView.getAdapter().getItemCount()) {
                    recyclerView.smoothScrollToPosition(--count);
                    handler.postDelayed(this, speedScroll);
                }

            }

        };

        handler.post(runnable);
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