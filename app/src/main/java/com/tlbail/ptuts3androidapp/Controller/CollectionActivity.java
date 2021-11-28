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
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText cityToSearch;

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
    }

    private List<String> getCityNameFromRecyclerView(){

        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        List<String> cityNames = new ArrayList<>();

        for (int i = 0; i < llm.getChildCount(); i++) {

        }

        return cityNames;
    }


    private void setupListener(){
        cityToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String city = cityToSearch.getText().toString();
                System.out.println(getCityNameFromRecyclerView().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void setupRecyclerView() {
        User user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));

        List<City> cities = user.getOwnedCity();

        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","LAVAL"), new CityData("LAVAL", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST1"), new CityData("TEST1", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST2"), new CityData("TEST2", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST3"), new CityData("TEST3", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST4"), new CityData("TEST4", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST5"), new CityData("TEST5", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST6"), new CityData("TEST6", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST7"), new CityData("TEST7", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST8"), new CityData("TEST8", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST9"), new CityData("TEST9", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","TEST10"), new CityData("TEST10", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));


        CityAdaptater cityAdaptater = new CityAdaptater(cities);
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