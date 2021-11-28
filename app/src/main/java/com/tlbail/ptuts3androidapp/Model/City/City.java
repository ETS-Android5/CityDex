package com.tlbail.ptuts3androidapp.Model.City;

import com.tlbail.ptuts3androidapp.Model.Photo.Photo;

import java.io.Serializable;

public class City implements Serializable {

    private Photo photo;
    private CityData cityData;

    public City(Photo photo, CityData cityData) {
        this.photo = photo;
        this.cityData = cityData;
    }

    public Photo getPhoto() {
        return photo;
    }

    public CityData getCityData() {
        return cityData;
    }


    @Override
    public String toString() {
        return cityData.getName();
    }


}
