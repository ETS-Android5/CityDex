package com.example.ptuts3androidapp.Model.CityApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class CityFetcherOnline implements CityFetcher{

	public CityFetcherOnline() {

	}

	@Override
	public City getCity(String name, Department department){
		HttpsURLConnection httpsURLConnection = null;
		try {
			URL url = new URL("https://geo.api.gouv.fr/departements/"+ department.getPostalCode() +"/communes?&fields=nom,population,surface,codeDepartement,region");
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream)httpsURLConnection.getContent(),"UTF-8"));
			JsonArray items = root.getAsJsonArray();
			httpsURLConnection.disconnect();
			return transformJsonArrayToCityList(items, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	@Override
	public List<City> getCitiesByDepartment(Department department) {
		HttpsURLConnection httpsURLConnection = null;
		try {
			URL url = new URL("https://geo.api.gouv.fr/departements/" + department.getPostalCode() + "/communes?fields=nom,population,surface,codeDepartement,region");
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream)httpsURLConnection.getContent(),"UTF-8"));
			JsonArray items = root.getAsJsonArray();
			httpsURLConnection.disconnect();
			return transformJsonArrayToCityList(items);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<City> getCitiesBySurface(float surface) {
		HttpsURLConnection httpsURLConnection = null;
		try {
			URL url = new URL("https://geo.api.gouv.fr/communes?fields=nom,population,surface,codeDepartement,region");
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream)httpsURLConnection.getContent(),"UTF-8"));
			JsonArray items = root.getAsJsonArray();
			httpsURLConnection.disconnect();
			return transformJsonArrayToCityList(items, surface);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<City> getCitiesByName(String name) {
		HttpsURLConnection httpsURLConnection = null;
		try {
			URL url = new URL("https://geo.api.gouv.fr/communes?nom=" + name +"&fields=nom,population,surface,codeDepartement,region");
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream)httpsURLConnection.getContent(),"UTF-8"));
			JsonArray items = root.getAsJsonArray();
			httpsURLConnection.disconnect();
			return transformJsonArrayToCityList(items);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<City> getCitiesByInhabitance(int inhabitants) {
		HttpsURLConnection httpsURLConnection = null;
		try {
			URL url = new URL("https://geo.api.gouv.fr/communes?fields=nom,population,surface,codeDepartement,region");
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream)httpsURLConnection.getContent(),"UTF-8"));
			JsonArray items = root.getAsJsonArray();
			httpsURLConnection.disconnect();
			return transformJsonArrayToCityList(items,inhabitants);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private List<City> transformJsonArrayToCityList(JsonArray jsonArray) {
		List<City> cities = new ArrayList<>();
		for (JsonElement jsonElement : jsonArray) {
			cities.add(transformJsonElementToCityObject(jsonElement));
		}
		return cities;
	}

	private City transformJsonElementToCityObject(JsonElement jsonElement) {
		City city;
		Department department;
		JsonObject cityJsonObject = jsonElement.getAsJsonObject();
		String name = cityJsonObject.get("nom").getAsString();
		if(cityJsonObject.get("codeDepartement") != null) {
			department = Department.valueOfByCode(cityJsonObject.get("codeDepartement").getAsString());
		}else {
			return null;
		}
		Region region = Region.valueOfByName(cityJsonObject.get("region").getAsJsonObject().get("nom").getAsString());
		float surface = cityJsonObject.get("surface").getAsFloat();
		int inhabitants = (cityJsonObject.get("population") == null) ? 0 : cityJsonObject.get("population").getAsInt();

		city = new City(name, department, region, surface, inhabitants);
		return city;
	}

	private List<City> transformJsonArrayToCityList(JsonArray jsonArray, int inhabitantsThreshold) {
		List<City> cities = new ArrayList<>();
		for (JsonElement jsonElement : jsonArray) {
			City city = transformJsonElementToCityObject(jsonElement, inhabitantsThreshold);
			if (city != null) {
				cities.add(city);
			}
		}
		return cities;
	}

	private City transformJsonElementToCityObject(JsonElement jsonElement, int inhabitantsThreshold) {
		City city;
		int inhabitants = 0;
		Department department;
		JsonObject cityJsonObject = jsonElement.getAsJsonObject();
		String name = cityJsonObject.get("nom").getAsString();
		if(cityJsonObject.get("codeDepartement") != null) {
			department = Department.valueOfByCode(cityJsonObject.get("codeDepartement").getAsString());
		}else {
			return null;
		}
		Region region = Region.valueOfByName(cityJsonObject.get("region").getAsJsonObject().get("nom").getAsString());
		float surface = cityJsonObject.get("surface").getAsFloat();
		if(cityJsonObject.get("population") != null && inhabitantsThreshold <= cityJsonObject.get("population").getAsInt()) {
			inhabitants = cityJsonObject.get("population").getAsInt();
		}else {
			return null;
		}
		city = new City(name, department, region, surface, inhabitants);
		return city;
	}

	private List<City> transformJsonArrayToCityList(JsonArray jsonArray, float surfaceThreshold) {
		List<City> cities = new ArrayList<>();
		for (JsonElement jsonElement : jsonArray) {
			City city = transformJsonElementToCityObject(jsonElement, surfaceThreshold);
			if (city != null) {
				cities.add(city);
			}
		}
		return cities;
	}

	private City transformJsonElementToCityObject(JsonElement jsonElement, float surfaceThreshold) {
		City city;
		float surface = 0;
		Department department;
		JsonObject cityJsonObject = jsonElement.getAsJsonObject();
		String name = cityJsonObject.get("nom").getAsString();
		if(cityJsonObject.get("codeDepartement") != null) {
			department = Department.valueOfByCode(cityJsonObject.get("codeDepartement").getAsString());
		}else {
			return null;
		}
		Region region = Region.valueOfByName(cityJsonObject.get("region").getAsJsonObject().get("nom").getAsString());
		if(surfaceThreshold <= cityJsonObject.get("surface").getAsFloat()) {
			surface = cityJsonObject.get("surface").getAsFloat();
		}else {
			return null;
		}
		int inhabitants = (cityJsonObject.get("population") == null) ? 0 : cityJsonObject.get("population").getAsInt();
		city = new City(name, department, region, surface, inhabitants);
		return city;
	}
	
	private City transformJsonArrayToCityList(JsonArray jsonArray, String name) {
		City city = null;
		for (JsonElement jsonElement : jsonArray) {
			city = transformJsonElementToCityObject(jsonElement, name);
			if (city != null) {
				break;
			}
		}
		return city;
	}
	private City transformJsonElementToCityObject(JsonElement jsonElement, String nameToCheck) {
		City city;
		float surface = 0;
		String name;
		Department department;
		JsonObject cityJsonObject = jsonElement.getAsJsonObject();
		if(nameToCheck.equals(cityJsonObject.get("nom").getAsString())) {
			name = cityJsonObject.get("nom").getAsString();
		}else {
			return null;
		}
		if(cityJsonObject.get("codeDepartement") != null) {
			department = Department.valueOfByCode(cityJsonObject.get("codeDepartement").getAsString());
		}else {
			return null;
		}
		Region region = Region.valueOfByName(cityJsonObject.get("region").getAsJsonObject().get("nom").getAsString());
		surface = cityJsonObject.get("surface").getAsFloat();
		int inhabitants = (cityJsonObject.get("population") == null) ? 0 : cityJsonObject.get("population").getAsInt();
		city = new City(name, department, region, surface, inhabitants);
		return city;
	}
}
