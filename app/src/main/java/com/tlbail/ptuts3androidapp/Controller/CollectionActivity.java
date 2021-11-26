package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        cities.add(new City("LAVAL", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST1", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST2", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST3", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST4", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST5", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST6", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST7", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST8", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST9", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST10", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST11", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST12", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST13", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST14", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        cities.add(new City("TEST15", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1));
        CityAdaptater cityAdaptater = new CityAdaptater(cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdaptater);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                for (int j = 0; j < lm.getChildCount(); j++) {
                    switch (j){
                        case 0: case 6:
                            lm.getChildAt(j).animate().translationX(-25);
                            break;
                        case 1: case 5:
                            lm.getChildAt(j).animate().translationX(-50);
                            break;
                        case 2: case 4:
                            lm.getChildAt(j).animate().translationX(-75);
                            break;
                        case 3:
                            lm.getChildAt(j).animate().translationX(-100);
                            break;
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
    }
}