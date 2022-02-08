package com.tlbail.ptuts3androidapp.Model.CityApi;

import com.tlbail.ptuts3androidapp.Model.City.CityData;

import java.util.List;

public interface FetchCityListener {

    void onDataQueryComplete(List<CityData> cityData);

}
