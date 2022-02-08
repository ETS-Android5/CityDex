package com.tlbail.ptuts3androidapp.Model.CityApi;

import android.os.AsyncTask;
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


public abstract class FetchCity extends AsyncTask<Void, Void, List<CityData>> {
    protected String request;
    private FetchCityListener fecthCityListener;


    public FetchCity(FetchCityListener fecthCityListener) {
            this.fecthCityListener = fecthCityListener;
    }

    @Override
    protected List<CityData> doInBackground(Void... voids) {
        return requestCities();
    }

    @Override
    protected void onPostExecute(List<CityData> cities) {
        fecthCityListener.onDataQueryComplete(cities);
    }

    public List<CityData> requestCities(){
        return transformJsonArrayToCityList(getResultRequest());
    }

    protected JsonArray getResultRequest(){
        try {
            if(request == null) return null;
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
        if(jsonArray == null) return null;
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
