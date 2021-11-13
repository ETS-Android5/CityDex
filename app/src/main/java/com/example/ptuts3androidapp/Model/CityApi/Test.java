package com.example.ptuts3androidapp.Model.CityApi;

import java.io.IOException;
import java.util.List;

public class Test {
	public static void main(String[] args) throws IOException {
		CityFetcherOnline cityFetcher = new CityFetcherOnline();
		
		City city = cityFetcher.getCity("Laval", Department.Mayenne);
		System.out.println(city.name + " - " + city.department.getDepartmentName() +" - "+ city.inhabitants + " - " +city.surface);
		
		printCities(cityFetcher.getCitiesByDepartment(Department.Ain));
		
		System.out.println();
		
		printCities(cityFetcher.getCitiesByInhabitance(1000000));
		
		System.out.println();
		
		printCities(cityFetcher.getCitiesByName("Laval"));
		
		System.out.println();
		
		printCities(cityFetcher.getCitiesBySurface(10000));

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
