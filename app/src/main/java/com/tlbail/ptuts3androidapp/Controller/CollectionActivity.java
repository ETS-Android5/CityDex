package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.City.CityAdaptater;

import java.util.ArrayList;
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

        user.setOwnedCity(cities);

        CityAdaptater cityAdaptater = new CityAdaptater(cities, recyclerView, thisApp);
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                collectionAnimation(snapHelper);
            }
        });

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


    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }*/
}