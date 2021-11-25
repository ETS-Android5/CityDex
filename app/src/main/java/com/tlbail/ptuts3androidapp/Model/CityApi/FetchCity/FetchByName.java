package com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity;

import com.tlbail.ptuts3androidapp.Model.CityApi.City;
import com.tlbail.ptuts3androidapp.Model.CityApi.Department;
import com.tlbail.ptuts3androidapp.Model.CityApi.Region;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FetchByName extends FetchCity{

    public FetchByName(String name){
        this.request = "https://geo.api.gouv.fr/communes?nom=" + name +"&fields=nom,population,surface,codeDepartement,region";
    }

    @Override
    public City transformJsonElementToCityObject(JsonElement jsonElement) {
        JsonObject cityJsonObject = jsonElement.getAsJsonObject();
        String name = cityJsonObject.get("nom").getAsString();
        Department department = Department.valueOfByCode(cityJsonObject.get("codeDepartement").getAsString());
        Region region = Region.valueOfByName(cityJsonObject.get("region").getAsJsonObject().get("nom").getAsString());
        float surface = cityJsonObject.get("surface").getAsFloat();
        int inhabitants = (cityJsonObject.get("population") == null) ? 0 : cityJsonObject.get("population").getAsInt();
        return  new City(name, department, region, surface, inhabitants);
    }
}
