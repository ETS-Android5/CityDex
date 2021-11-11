package com.example.ptuts3androidapp.View.BackgroundOfPhoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.example.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.example.ptuts3androidapp.Model.User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BackgroundRecyclerView {

    private int velocity;
    private Context context;
    private AppCompatActivity appCompatActivity;
    private List<Bitmap> bitmaps;
    private RecyclerView recyclerView;

    public BackgroundRecyclerView(AppCompatActivity appCompatActivity){
        velocity = 1;
        this.appCompatActivity = appCompatActivity;
        this.context = appCompatActivity.getApplicationContext();

        User user = new User(new UserPropertyLocalLoader(context) , new CityLocalLoader(context));

        List<City> cities = user.getOwnedCity();

        bitmaps = new ArrayList<>();

        for (City city: cities
        ) {

            try {
                bitmaps.add(MediaStore.Images.Media.getBitmap(context.getContentResolver(),  city.getPhoto().getPhotoUri()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }


    public void start(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        BackgroundViewAdapter backgroundViewAdapter = new BackgroundViewAdapter(bitmaps);

        recyclerView.setAdapter(backgroundViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

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
                if(recyclerView.getAdapter().getItemCount() <= 0) return;
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



}
