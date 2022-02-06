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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<City> cities = getAllCityFromUser();

        List<LatLng> latLngs = getLatLngFromCities(cities);
        addMarkerOfCities(latLngs);
    }


    private List<City> getAllCityFromUser() {
        User user = new User(new UserPropertyLocalLoader(this), new CityLocalLoader(this));
        return user.getOwnedCity();
    }


    private List<LatLng> getLatLngFromCities(List<City> cities) {
        List<LatLng> latLngs = new ArrayList<>();
        for(City city: cities){
            latLngs.add(city.getCityData().getPosition());
        }
        return latLngs;
    }

    private void addMarkerOfCities(List<LatLng> cities){

        if(cities.size() == 0) {
            Toast.makeText(this, "Tu ne possède aucunne ville ! ", Toast.LENGTH_LONG).show();
            return;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cities.get(0)));

        for(LatLng latLng : cities){
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
        }


    }



}