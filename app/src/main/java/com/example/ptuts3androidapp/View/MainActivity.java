package com.example.ptuts3androidapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;

import com.example.ptuts3androidapp.Controller.PhotoManager;
import com.example.ptuts3androidapp.Controller.UserManager;
import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.example.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.example.ptuts3androidapp.Model.User.User;
import com.example.ptuts3androidapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private PhotoManager photoManager;
    private UserManager userManager;
    private EditText cityNameEditText;
    private RecyclerView recyclerView;
    private int velocity;

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

        User user = new User(new UserPropertyLocalLoader(getApplicationContext()) , new CityLocalLoader(getApplicationContext()));

        List<City> cities = user.getOwnedCity();

        List<Bitmap> bitmaps = new ArrayList<>();

        for (City city: cities
             ) {

            try {
                bitmaps.add(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),  city.getPhoto().getPhotoUri()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        BackgroundViewAdapter backgroundViewAdapter = new BackgroundViewAdapter(bitmaps);

        recyclerView.setAdapter(backgroundViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });

        autoScroll();


    }


    public void autoScroll() {
        final int speedScroll = 3000;
        Handler handler = new Handler();
        velocity = 1;
        Runnable runnable = new Runnable() {
            int count = -1;

            @Override
            public void run() {
                if (count == recyclerView.getAdapter().getItemCount() || (count == 0 && velocity == -1)) {
                    velocity = -velocity;
                }
                count += velocity;
                recyclerView.smoothScrollToPosition(count);
                handler.postDelayed(this, speedScroll);
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