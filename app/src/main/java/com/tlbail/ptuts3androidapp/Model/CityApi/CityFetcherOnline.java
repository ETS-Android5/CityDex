package com.example.ptuts3androidapp.Model.CityApi;

import java.util.List;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchCity;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.CityApi.CityFetcher;


public class CityFetcherOnline implements CityFetcher {

    public CityFetcherOnline() {

    }


    @Override
    public List<CityData> fetch(FetchCity fetchCity) {
        return fetchCity.getCities();
    }
}