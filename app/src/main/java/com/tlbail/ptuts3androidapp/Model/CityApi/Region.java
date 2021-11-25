package com.tlbail.ptuts3androidapp.Model.CityApi;

public enum Region {
	AUVERGNE_RHONES_ALPES("Auvergne-Rhône-Alpes"),
	BOURGOGNE_FRANCHE_COMTE("Bourgogne-Franche-Comté"),
	BRETAGNE("Bretagne"),
	CENTRE_VAL_DE_LOIRE("Centre-Val de Loire"),
	CORSE("Corse"),
	GRAND_EST("Grand Est"),
	HAUTS_DE_FRANCE("Hauts-de-France"),
	ILE_DE_FRANCE("Île-de-France"),
	NORMANDIE("Normandie"),
	NOUVELLE_AQUITAINE("Nouvelle-Aquitaine"),
	OCCITANIE("Occitanie"),
	PAYS_DE_LA_LOIRE("Pays de la Loire"),
	PROVENCE_ALPES_COTE_D_AZUR("Provence-Alpes-Côte d'Azur"),
	GUADELOUPE("Guadeloupe"),
	MARTINIQUE("Martinique"),
	GUYANE("Guyane"),
	LA_REUNION("La Réunion"),
	MAYOTTE("Mayotte");
	
	
	private String name;
	
	private Region(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static Region valueOfByName(String name) {
		for (Region value : values()) {
			if (value.getName().equalsIgnoreCase(name)) {
				return value;
			}
		}
		return null;
	}
}
