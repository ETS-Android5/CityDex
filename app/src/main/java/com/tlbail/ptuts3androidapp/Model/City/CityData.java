package com.tlbail.ptuts3androidapp.Model.City;

import com.google.android.gms.maps.model.LatLng;
import com.tlbail.ptuts3androidapp.Model.CityApi.Department;
import com.tlbail.ptuts3androidapp.Model.CityApi.Region;

import java.io.Serializable;

public class CityData implements Serializable {
	String name;
	Department department;
	Region region;
	float surface;
	int inhabitants;
	double latitude;
	double longitude;

	public CityData(String name, Department department, Region region, float surface, int inhabitants, LatLng position) {
		super();
		this.name = name;
		this.department = department;
		this.region = region;
		this.surface = surface;
		this.inhabitants = inhabitants;
		this.latitude = position.latitude;
		this.longitude = position.longitude;
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

	public LatLng getPosition() {
		return new LatLng(this.latitude, this.longitude);
	}
}
