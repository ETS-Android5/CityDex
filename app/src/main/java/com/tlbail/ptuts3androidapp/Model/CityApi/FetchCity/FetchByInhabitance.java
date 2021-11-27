package com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity;

import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.CityApi.Department;
import com.tlbail.ptuts3androidapp.Model.CityApi.Region;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FetchByInhabitance extends FetchCity{

    private int inhabitantsThreshold;

    public FetchByInhabitance(int inhabitantsThreshold){
        this.inhabitantsThreshold = inhabitantsThreshold;
        this.request = "https://geo.api.gouv.fr/communes?fields=nom,population,surface,codeDepartement,region";
    }

    @Override
    public CityData transformJsonElementToCityObject(JsonElement jsonElement) {
        JsonObject cityJsonObject = jsonElement.getAsJsonObject();
        String name = cityJsonObject.get("nom").getAsString();
        Department department = Department.valueOfByCode(cityJsonObject.get("codeDepartement").getAsString());
        Region region = Region.valueOfByName(cityJsonObject.get("region").getAsJsonObject().get("nom").getAsString());
        float surface = cityJsonObject.get("surface").getAsFloat();
        int inhabitants = cityJsonObject.get("population") != null? cityJsonObject.get("population").getAsInt() : -1;
        if(name == null || department == null || region == null || inhabitantsThreshold > inhabitants) return  null;
        return new CityData(name, department, region, surface, inhabitants);
    }
}
