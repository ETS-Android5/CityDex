package com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity;

import com.tlbail.ptuts3androidapp.Model.City.CityData;
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
    private  List<CityData> cities;

    public List<CityData> getCities(){
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    cities = transformJsonArrayToCityList(getResultRequest());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cities;
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

    protected List<CityData> transformJsonArrayToCityList(JsonArray jsonArray){
        List<CityData> cities = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            CityData cityData = transformJsonElementToCityObject(jsonElement);
            if (cityData != null) {
                cities.add(cityData);
            }
        }
        return cities;
    }

    protected abstract CityData transformJsonElementToCityObject(JsonElement jsonElement);
}
