package com.tlbail.ptuts3androidapp.Model.CityApi;

import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchCity;

import java.util.List;

public interface CityFetcher {
	List<City> fetch(FetchCity fetchCity);
}
