package com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BackgroundRecyclerView {

    private double velocity;
    private Context context;
    private List<Bitmap> cityPhotoList;
    private RecyclerView recyclerView;
    private Handler handler;

    public BackgroundRecyclerView(AppCompatActivity appCompatActivity){
        velocity = 0.5;
        this.context = appCompatActivity.getApplicationContext();
        populateRecyclerView();
    }

    private void populateRecyclerView(){
        User user = new User(new UserPropertyLocalLoader(context) , new CityLocalLoader(context));
        List<City> cities = user.getOwnedCity();
        cityPhotoList = new ArrayList<>();
        for (City city: cities) {
            addCityPhotoToCityPhotoList(city);
        }
    }

    private void addCityPhotoToCityPhotoList(City city){
        try {
            if(city != null && city.getPhoto() != null && city.getPhoto().getPhotoUri() != null)
                cityPhotoList.add(MediaStore.Images.Media.getBitmap(context.getContentResolver(),  city.getPhoto().getPhotoUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void start(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        BackgroundViewAdapter backgroundViewAdapter = new BackgroundViewAdapter(cityPhotoList);

        recyclerView.setAdapter(backgroundViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context){
                    @Override
                    protected int calculateTimeForScrolling(int dx) {
                        return dx*3;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        });
        autoScroll();
    }

    public void autoScroll() {
        velocity = 1;
        handler.post(scrollTask());
    }

    private Runnable scrollTask(){
        return new Runnable() {
            int count = -1;
            @Override
            public void run() {
                if(recyclerView.getAdapter().getItemCount() <= 0) return;
                if (count+velocity < 0 || count+velocity >= recyclerView.getAdapter().getItemCount())
                    velocity = -velocity;
                count += velocity;
                recyclerView.smoothScrollToPosition(count);
                handler.postDelayed(this, 10000);
            }
        };
    }

}
