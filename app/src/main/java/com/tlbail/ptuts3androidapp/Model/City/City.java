package com.tlbail.ptuts3androidapp.Model.City;

import com.tlbail.ptuts3androidapp.Model.Photo.Photo;

import java.io.Serializable;
import java.util.Comparator;

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

    public static Comparator<City> ComparatorName = new Comparator<City>() {
        @Override
        public int compare(City c1, City c2) {
            return c1.getCityData().getName().compareTo(c2.cityData.getName());
        }
    };

    public static Comparator<City> ComparatorDpt = new Comparator<City>() {
        @Override
        public int compare(City c1, City c2) {
            return c1.getCityData().getDepartment().getDepartmentName().compareTo(c2.cityData.getDepartment().getDepartmentName());
        }
    };

    public static Comparator<City> ComparatorRegion = new Comparator<City>() {
        @Override
        public int compare(City c1, City c2) {
            return c1.getCityData().getRegion().getName().compareTo(c2.cityData.getRegion().getName());
        }
    };
}
