package com.example.ptuts3androidapp.Model.User;

import android.content.Context;
import android.net.Uri;

import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.Model.City.CityData;
import com.example.ptuts3androidapp.Model.Photo.Photo;
import com.example.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;

import java.util.HashMap;
import java.util.List;

public class UserManager {


    private User user;


    public UserManager(Context context) {
        this.user = new User(new UserPropertyLocalLoader(context), new com.example.ptuts3androidapp.Model.City.CityLocalLoader(context));
    }

    public void addCity(Uri photoUri, String cityName){
        List<City> cities = user.getOwnedCity();
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put(CityData.CITY_NAME_KEY, cityName);
        cities.add(new City(new Photo(photoUri, "Paris"), new CityData(hashMap)));
        user.setOwnedCity(cities);
    }

    public List<City> getCities(){
        return user.getOwnedCity();
    }



}


