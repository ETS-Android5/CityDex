package com.tlbail.ptuts3androidapp.Model.CityApi;

import java.util.List;

public interface CityFetcher {

	City getCity(String name, Department department);
	List<City> getCitiesByDepartment(Department department);
	List<City> getCitiesBySurface(float surface);
	List<City> getCitiesByName(String name);
	List<City> getCitiesByInhabitance(int nbPeople);
}
