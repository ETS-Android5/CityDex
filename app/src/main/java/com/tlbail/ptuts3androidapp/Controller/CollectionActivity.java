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
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private Spinner spinner;
    private SnapHelper snapHelper;
    private AppCompatActivity thisApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.galerie);
        bindUI();

        setupRecyclerView();
        setupListener();
        setUpSpinner();

    }


    private void bindUI() {
        recyclerView = findViewById(R.id.gallerieRecyclerView);
        cityToSearch = findViewById(R.id.cityToSearch);
        spinner = findViewById(R.id.spinner);
        thisApp = this;
    }

    private void setUpSpinner(){
        List<String> critereDeTri = new ArrayList<>();

        critereDeTri.add("Par ordre d'obtention");
        critereDeTri.add("Par ordre alphabétique");
        critereDeTri.add("Par département");
        critereDeTri.add("Par région");

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,critereDeTri);
        spinner.setAdapter(dataAdapterR);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = String.valueOf(spinner.getSelectedItem());
                List<City> cities = new ArrayList<>(user.getOwnedCity());

                if(value.compareTo("Par ordre d'obtention") == 0){
                    CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, thisApp);
                    recyclerView.setAdapter(cityAdaptater);
                }

                if(value.compareTo("Par ordre alphabétique") == 0){
                    Collections.sort(cities, City.ComparatorName);
                    CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, thisApp);
                    recyclerView.setAdapter(cityAdaptater);
                }

                if(value.compareTo("Par département") == 0){
                    Collections.sort(cities, City.ComparatorDpt);
                    CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, thisApp);
                    recyclerView.setAdapter(cityAdaptater);
                }

                if(value.compareTo("Par région") == 0){
                    Collections.sort(cities, City.ComparatorRegion);
                    CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, thisApp);
                    recyclerView.setAdapter(cityAdaptater);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing to do
            }
        });
    }

    private void setupListener(){
        cityToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String citySearched = cityToSearch.getText().toString();

                List<City> cities = ((CityAdaptater)recyclerView.getAdapter()).getCities();
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                for(int j = 0; j < cities.size(); j++){

                    if(cities.get(j).getCityData().getName().toUpperCase().compareTo(citySearched.toUpperCase()) == 0
                    || (cities.get(j).getCityData().getName().toUpperCase().startsWith(citySearched.toUpperCase()) && !citySearched.equals(""))){
                        recyclerView.smoothScrollToPosition(j);
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void setupRecyclerView() {
        user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));

        List<City> cities = user.getOwnedCity();

        cities.add(new City(new Photo("","LAVAL"), new CityData("LAVAL", Department.Mayenne, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("C:\\Users\\basti\\Documents\\GitHub\\PtutS3Android\\app\\src\\main\\res\\drawable\\atlantide.jpg","PARIS"), new CityData("PARIS", Department.SeineSaintDenis, Region.ILE_DE_FRANCE, 1,1)));
        cities.add(new City(new Photo("drawable/atlantide.jpg","MARSEILLE"), new CityData("MARSEILLE", Department.BouchesDuRhone, Region.PROVENCE_ALPES_COTE_D_AZUR, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","LYON"), new CityData("LYON", Department.Rhone, Region.AUVERGNE_RHONES_ALPES, 1,1)));
        cities.add(new City(new Photo("C:/Users/basti/Documents/GitHub/PtutS3Android/app/src/main/res/drawable/atlantide.jpg","LILLE"), new CityData("LILLE", Department.Nord, Region.HAUTS_DE_FRANCE, 1,1)));
        cities.add(new City(new Photo("../res/drawable/atlantide.jpg","ANGERS"), new CityData("ANGERS", Department.MaineEtLoire, Region.PAYS_DE_LA_LOIRE, 4270,40000)));
        cities.add(new City(new Photo("res/drawable/atlantide.jpg","BREST"), new CityData("BREST", Department.Finistere, Region.BRETAGNE, 1,1)));
        cities.add(new City(new Photo("./res/drawable/atlantide.jpg","POITIERS"), new CityData("POITIERS", Department.Vienne, Region.NOUVELLE_AQUITAINE, 1,1)));
        cities.add(new City(new Photo("src/main/res/drawable/atlantide.jpg","LE MANS"), new CityData("LE MANS", Department.Sarthe, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","NANTES"), new CityData("NANTES", Department.LoireAtlantique, Region.PAYS_DE_LA_LOIRE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","BORDEAUX"), new CityData("BORDEAUX", Department.Gironde, Region.NOUVELLE_AQUITAINE, 1,1)));
        cities.add(new City(new Photo("../../res/drawable/atlantide.jpg","RICHELIEU"), new CityData("RICHELIEU", Department.IndreEtLoire, Region.CENTRE_VAL_DE_LOIRE, 1,1)));

        user.setOwnedCity(cities);

        CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, thisApp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdaptater);
        snapHelper = new LinearSnapHelper();
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