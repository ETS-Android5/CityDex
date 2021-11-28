package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.PointF;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.CityApi.*;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.Photo.Photo;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.City.CityAdaptater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText cityToSearch;
    private User user;
    private Button tri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.galerie);
        bindUI();

        setupRecyclerView();
        setupListener();

    }


    private void bindUI() {
        recyclerView = findViewById(R.id.gallerieRecyclerView);
        cityToSearch = findViewById(R.id.cityToSearch);
        tri = findViewById(R.id.tri);
    }

    private void setupListener(){
        cityToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String citySearched = cityToSearch.getText().toString();


                for(int j = 0; j < user.getOwnedCity().size(); j++){

                    if(user.getOwnedCity().get(j).getCityData().getName().toUpperCase().compareTo(citySearched.toUpperCase()) == 0
                    || user.getOwnedCity().get(j).getCityData().getName().toUpperCase().startsWith(citySearched.toUpperCase())){
                        recyclerView.smoothScrollToPosition(j);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(user.getOwnedCity());
                CityAdaptater cityAdaptater = new CityAdaptater(user.getOwnedCity(), recyclerView);
                recyclerView.setAdapter(cityAdaptater);
            }
        });
    }


    private void setupRecyclerView() {
        user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));

        List<City> cities = user.getOwnedCity();

        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","LAVAL"), new CityData("LAVAL", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","PARIS"), new CityData("PARIS", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","MARSEILLE"), new CityData("MARSEILLE", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","LYON"), new CityData("LYON", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","LILLE"), new CityData("LILLE", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","ANGERS"), new CityData("ANGERS", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","BREST"), new CityData("BREST", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","POITIERS"), new CityData("POITIERS", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","LE MANS"), new CityData("LE MANS", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","NANTES"), new CityData("NANTES", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","BORDEAUX"), new CityData("BORDEAUX", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","RICHELIEU"), new CityData("RICHELIEU", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));

        CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdaptater);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()){
                    @Override
                    protected int calculateTimeForScrolling(int dx) {
                        return dx/5;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        });
        recyclerView.setAdapter(cityAdaptater);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                collectionAnimation(snapHelper);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                collectionAnimation(snapHelper);
            }
        });
    }

    private void collectionAnimation(SnapHelper snapHelper) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int snappedPosition = lm.getPosition(snapHelper.findSnapView(lm));
        for (int i = 0; i < lm.getChildCount(); i++) {
            if (lm.getChildAt(i) == null) continue;
            else if (lm.getChildAt(i).equals(lm.findViewByPosition(snappedPosition))){
                snapHelper.findSnapView(lm).animate().translationX(0);
            }
            else {
                int relativePos = Math.abs(lm.getPosition(lm.getChildAt(i))-snappedPosition);
                lm.getChildAt(i).animate().translationX(50*relativePos);
            }
        }
    }

}