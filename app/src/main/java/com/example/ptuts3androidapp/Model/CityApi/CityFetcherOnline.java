package com.example.ptuts3androidapp.Model.CityApi;

import java.util.List;
import com.example.ptuts3androidapp.Model.CityApi.FetchCity.FetchCity;


public class CityFetcherOnline implements CityFetcher{

	public CityFetcherOnline() {

	}


	@Override
	public List<City> fetch(FetchCity fetchCity) {
		return fetchCity.getCities();
	}
}
