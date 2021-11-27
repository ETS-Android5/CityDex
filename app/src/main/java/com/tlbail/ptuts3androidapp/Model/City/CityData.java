package com.tlbail.ptuts3androidapp.Model.City;

import com.tlbail.ptuts3androidapp.Model.CityApi.Department;
import com.tlbail.ptuts3androidapp.Model.CityApi.Region;

import java.io.Serializable;

public class CityData implements Serializable {
	String name;
	Department department;
	Region region;
	float surface;
	int inhabitants;

	public CityData(String name, Department department, Region region, float surface, int inhabitants) {
		super();
		this.name = name;
		this.department = department;
		this.region = region;
		this.surface = surface;
		this.inhabitants = inhabitants;
	}

	public String getName() {
		return name;
	}

	public Department getDepartment() {
		return department;
	}

	public float getSurface() {
		return surface;
	}

	public int getInhabitants() {
		return inhabitants;
	}

	public Region getRegion() {
		return region;
	}
}
