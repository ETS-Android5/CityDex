package com.tlbail.ptuts3androidapp.Model.CityApi;

import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchCity;

import java.util.List;

public interface CityFetcher {
	List<CityData> fetch(FetchCity fetchCity);
}
