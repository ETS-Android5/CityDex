package com.example.ptuts3androidapp.Model.City;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CityData implements Serializable {


    public static final String CITY_NAME_KEY = "cityName";



    private HashMap<String, String> cityProperty;

    public CityData(HashMap<String, String> cityProperty) {
        this.cityProperty = cityProperty;
    }

    public HashMap<String, String> getCityProperty() {
        return cityProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityData cityData = (CityData) o;
        return cityProperty.equals(cityData.cityProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityProperty);
    }

    @Override
    public String toString() {
        return "CityData{" +
                "cityProperty=" + cityProperty +
                '}';
    }
}
