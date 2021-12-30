package com.tlbail.ptuts3androidapp.Model.PanneauVersVille;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Controller.ResultActivity;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.Photo.Photo;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class PhotoToCityDecorator extends PhotoToCity{


    private Uri uri;
    private Bitmap bitmap;
    private AppCompatActivity appCompatActivity;
    private User user;


    public PhotoToCityDecorator(ResultActivity appCompatActivity, Uri uri) {
        super(appCompatActivity);
        this.appCompatActivity = appCompatActivity;
        this.uri = uri;
        bitmap = uriToBitmap(uri);
        user = new User(new UserPropertyLocalLoader(appCompatActivity), new CityLocalLoader(appCompatActivity));
    }

    private Bitmap uriToBitmap(Uri uri){
        try {
            return MediaStore.Images.Media.getBitmap(appCompatActivity.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start(){
        start(bitmap);
    }

    @Override
    public void updateListener(City city) {
        if(city == null || user.getOwnedCity().contains(city)){
            deleteCityFromLocalStorage();
            city = null;
        }else {
            addCityToOwnCity(city);
        }
        super.updateListener(city);
    }

    @Override
    protected City createCity(CityData cityData) {
        //Todo créer une vraie ville
        if(dataIsUncorrect(cityData)) return null;
        City city = new City(new Photo(uri, cityData.getName()), cityData);
        return city;
    }

    private void deleteCityFromLocalStorage() {
        try {
            File file = new File(new URI(uri.toString()));
            file.delete();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void addCityToOwnCity(City city) {
        List<City> cities = user.getOwnedCity();
        cities.add(city);
        user.setOwnedCity(cities);
    }
}
