package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tlbail.ptuts3androidapp.Model.CityApi.*;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.City.CityAdaptater;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.galerie);
        bindUI();

        setupRecyclerView();


    }


    private void bindUI() {
        recyclerView = findViewById(R.id.gallerieRecyclerView);
    }


    private void setupRecyclerView() {
        User user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));

        List<City> cities = new ArrayList<>();
        cities.add(new City("LAVAL", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST1", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST2", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST3", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST4", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST5", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST6", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST7", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST8", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST9", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST10", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST11", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST12", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST13", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST14", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        cities.add(new City("TEST15", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1, 1));
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int calculateTimeForScrolling(int dx) {
                return dx/5;
            }
        };
        CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, smoothScroller);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdaptater);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                view.setX(view.getX() + 200);
                collectionAnimation(snapHelper);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                view.setX(view.getX() + 200);
                collectionAnimation(snapHelper);
            }
        });
    }


    private void collectionAnimation(SnapHelper snapHelper) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int snappedPosition = lm.getPosition(snapHelper.findSnapView(lm));
        if(snappedPosition == 0) animation0(lm);
        else if(snappedPosition == 1) animation1(lm);
        else if(snappedPosition == 2) animation2(lm);
        else if(snappedPosition == 3) animation3(lm);
        else classicAnimation(lm);
        snapHelper.findSnapView(lm).animate().translationX(-200);
    }

    private void animation0(RecyclerView.LayoutManager lm) {
        for (int i = 0; i < lm.getChildCount(); i++) {
            lm.getChildAt(i).animate().translationX(50*i).setDuration(50);
        }
    }

    private void animation1(RecyclerView.LayoutManager lm) {
        lm.getChildAt(0).animate().translationX(50).setDuration(50);
        for (int i = 1; i < lm.getChildCount(); i++) {
            lm.getChildAt(i).animate().translationX(50*(i-1)).setDuration(50);
        }
    }

    private void animation2(RecyclerView.LayoutManager lm) {
        lm.getChildAt(0).animate().translationX(100).setDuration(50);
        lm.getChildAt(1).animate().translationX(50).setDuration(50);
        for (int i = 2; i < lm.getChildCount(); i++) {
            lm.getChildAt(i).animate().translationX(50*(i-2)).setDuration(50);
        }
    }

    private void animation3(RecyclerView.LayoutManager lm) {
        lm.getChildAt(0).animate().translationX(150).setDuration(50);
        lm.getChildAt(1).animate().translationX(100).setDuration(50);
        lm.getChildAt(2).animate().translationX(50).setDuration(50);
        for (int i = 3; i < lm.getChildCount(); i++) {
            lm.getChildAt(i).animate().translationX(50*(i-3)).setDuration(50);
        }
    }

    private void classicAnimation(RecyclerView.LayoutManager lm) {
        for (int i = 0; i < lm.getChildCount(); i++) {
            switch (i){
                case 0: case 6:
                    lm.getChildAt(i).animate().translationX(150).setDuration(50);
                    break;
                case 1: case 5:
                    lm.getChildAt(i).animate().translationX(100).setDuration(50);
                    break;
                case 2: case 4:
                    lm.getChildAt(i).animate().translationX(50).setDuration(50);
                    break;
                case 3:
                    lm.getChildAt(i).animate().translationX(0).setDuration(50);
                    break;
            }
        }
    }








}