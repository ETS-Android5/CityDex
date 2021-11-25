package com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity;

import com.tlbail.ptuts3androidapp.Model.CityApi.City;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public abstract class FetchCity {
    protected String request;

    public List<City> getCities(){
        return transformJsonArrayToCityList(getResultRequest());
    }

    protected JsonArray getResultRequest(){
        try {
            URL url = new URL(request);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader((InputStream) httpsURLConnection.getContent(),"UTF-8"));
            httpsURLConnection.disconnect();
            return jsonElement.getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonArray();
    }

    protected List<City> transformJsonArrayToCityList(JsonArray jsonArray){
        List<City> cities = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            City city = transformJsonElementToCityObject(jsonElement);
            if (city != null) {
                cities.add(city);
            }
        }
        return cities;
    }

    protected abstract City transformJsonElementToCityObject(JsonElement jsonElement);
}
