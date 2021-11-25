package com.tlbail.ptuts3androidapp.Model.CityApi;

import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByDepartment;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByInhabitance;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByName;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchBySurface;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchOneCity;

import java.io.IOException;
import java.util.List;

public class Test {
	public static void main(String[] args) throws IOException {
		CityFetcherOnline cityFetcher = new CityFetcherOnline();

		printCities(cityFetcher.fetch(new FetchOneCity("Laval", Department.Mayenne)));

		printCities(cityFetcher.fetch(new FetchByDepartment(Department.Ain)));
		
		System.out.println();
		
		printCities(cityFetcher.fetch(new FetchByInhabitance(1000000)));
		
		System.out.println();
		
		printCities(cityFetcher.fetch(new FetchByName("Laval")));
		
		System.out.println();
		
		printCities(cityFetcher.fetch(new FetchBySurface(10000)));

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
