package com.tlbail.ptuts3androidapp.Model.CityApi;

public class City {
	String name;
	Department department;
	Region region;
	float surface;
	int inhabitants;
	public City(String name, Department department, Region region, float surface, int inhabitants) {
		super();
		this.name = name;
		this.department = department;
		this.region = region;
		this.surface = surface;
		this.inhabitants = inhabitants;
	}
}
