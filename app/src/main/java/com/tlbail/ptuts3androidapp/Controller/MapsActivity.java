package com.tlbail.ptuts3androidapp.Controller;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String choosenCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get Extra
        choosenCity = getIntent().getStringExtra("City");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<City> cities = getAllCityFromUser();

        addMarker(cities);
    }

    private List<City> getAllCityFromUser() {
        User user = new User(new UserPropertyLocalLoader(this), new CityLocalLoader(this));
        return user.getOwnedCity();
    }

    private void addMarker(List<City> cities){

        mMap.moveCamera(CameraUpdateFactory.zoomTo(5));

        if(cities.size() == 0) {
            Toast.makeText(this, "Tu ne possèdes aucune ville ! ", Toast.LENGTH_LONG).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.233654387753106, -0.7290989017941045)));

            return;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cities.get(0).getCityData().getPosition()));

        for(City city : cities){
            if(choosenCity != null & city.getCityData().getName().equalsIgnoreCase(choosenCity)){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(city.getCityData().getPosition()));
            }
            mMap.addMarker(new MarkerOptions().position(city.getCityData().getPosition()).title(city.getCityData().getName()));

        }
    }

}