package com.example.ptuts3androidapp.Model.CityApi;

import static org.junit.Assert.*;

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
        City city = cityFetcher.getCity("Laval", Department.Mayenne);
        System.out.println(city.name + " - " + city.department.getDepartmentName() +" - "+ city.inhabitants + " - " +city.surface);

    }


    @Test
    public void cityByDepartementNotNull(){

        List<City> cities = cityFetcher.getCitiesByDepartment(Department.Ain);
        Assert.assertNotNull(cities);
        printCities(cities);

    }

    @Test
    public void cityBySurface(){

        List<City> cities = cityFetcher.getCitiesBySurface(10000);
        Assert.assertNotNull(cities);
        printCities(cities);
    }

    @Test
    public void getcitybyHabitant(){

        List<City> cities = cityFetcher.getCitiesByInhabitance(1000000);
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