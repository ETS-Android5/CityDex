package com.tlbail.ptuts3androidapp.Model.CityApi;

import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByDepartment;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByInhabitance;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchBySurface;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchOneCity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CityFetcherOnlineTest {


    private CityFetcher cityFetcher;

    @Before
    public void setup(){

        cityFetcher = new CityFetcherOnline();
    }


    @Test
    public void createNotNullCityFectcher(){
        Assert.assertNotNull(cityFetcher);
    }

    @Test
    public void cityByNameNotNull(){
        List<City> cities = cityFetcher.fetch(new FetchOneCity("Laval", Department.Mayenne));
        Assert.assertNotNull(cities);
        printCities(cities);
    }


    @Test
    public void cityByDepartementNotNull(){

        List<City> cities = cityFetcher.fetch(new FetchByDepartment(Department.Ain));
        Assert.assertNotNull(cities);
        printCities(cities);

    }

    @Test
    public void cityBySurface(){

        List<City> cities = cityFetcher.fetch(new FetchBySurface(10000));
        Assert.assertNotNull(cities);
        printCities(cities);
    }

    @Test
    public void getcitybyHabitant(){

        List<City> cities = cityFetcher.fetch(new FetchByInhabitance(1000000));
        Assert.assertNotNull(cities);
        printCities(cities);

    }

    private static void printCities(List<City> cities) {
        int i = 0;
        for (City city : cities) {
            System.out.println(city.name + " - " + city.department.getDepartmentName() +" - "+ city.inhabitants + " - " +city.surface);
            i++;
        }
        System.out.println("nombre de ville :" +i);
    }
}