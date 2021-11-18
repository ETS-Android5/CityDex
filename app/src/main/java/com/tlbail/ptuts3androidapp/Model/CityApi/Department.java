package com.tlbail.ptuts3androidapp.Model.CityApi;

public enum Department {
		Ain("01", "Ain"),
		Aisne("02", "Aisne"),
		Allier("03", "Allier"),
		AlpesDeHautesProvence("04", "Alpes-de-Haute-Provence"),
		HautesAlpes("05", "Hautes-Alpes"),
		AlpesMaritimes("06", "Alpes-Maritimes"),
		Ardeche("07", "Ard�che"),
		Ardennes("08", "Ardennes"),
		Ariege("09", "Ari�ge"),
		Aube("10", "Aube"),
		Aude("11", "Aude"),
		Aveyron("12", "Aveyron"),
		BouchesDuRhone("13", "Bouches-Du-Rh�ne"),
		Calvados("14", "Calvados"),
		Cantal("15", "Cantal"),
		Charente("16", "Charente"),
		CharenteMaritime("17", "Charente-Maritime"),
		Cher("18", "Cher"),
		Correze("19", "Corr�ze"),
		CorseDuSud("2A", "Corse-du-Sud"),
		HauteCorse("2B", "Haute-Corse"),
		CoteDOr("21", "C�te-d'Or"),
		CotesDArmor("22", "C�tes-d'Armor"),
		Creuse("23", "Creuse"),
		Dordogne("24", "Dordogne"),
		Doubs("25", "Doubs"),
		Drome("26", "Dr�me"),
		Eure("27", "Eure"),
		EureEtLoir("28", "Eure-et-Loir"),
		Finistere("29", "Finist�re"),
		Gard("30", "Gard"),
		HauteGaronne("31", "Haute-Garonne"),
		Gers("32", "Gers"),
		Gironde("33", "Gironde"),
		Herault("34", "H�rault"),
		IlleEtVilaine("35", "Ille-et-Vilaine"),
		Indre("36", "Indre"),
		IndreEtLoire("37", "Indre-et-Loire"),
		Isere("38", "Is�re"),
		Jura("39", "Jura"),
		Landes("40", "Landes"),
		LoirEtCher("41", "Loir-et-Cher"),
		Loire("42", "Loire"),
		HauteLoire("43", "Haute-Loire"),
		LoireAtlantique("44", "Loire-Atlantique"),
		Loiret("45", "Loiret"),
		Lot("46", "Lot"),
		LotEtGaronne("47", "Lot-et-Garonne"),
		Lozere("48", "Loz�re"),
		MaineEtLoire("49", "Maine-et-Loire"),
		Manche("50", "Manche"),
		Marne("51", "Marne"),
		HauteMarnes("52", "Haute-Marne"),
		Mayenne("53", "Mayenne"),
		MeurtheEtMoselle("54", "Meurthe-et-Moselle"),
		Meuse("55", "Meuse"),
		Morbihan("56", "Morbihan"),
		Moselle("57", "Moselle"),
		Nivre("58", "Ni�vre"),
		Nord("59", "Nord"),
		Oise("60", "Oise"),
		Orne("61", "Orne"),
		PasDeCalais("62", "Pas-de-Calais"),
		PuyDeDome("63", "Puy-de-D�me"),
		PyreneesAtlantiques("64", "Pyr�n�es-Atlantiques"),
		HautesPyrenees("65", "Hautes-Pyr�n�es"),
		PyreneesOrientales("66", "Pyr�n�es-Orientales"),
		BasRhin("67", "Bas-Rhin"),
		HautRhin("68", "Haut-Rhin"),
		Rhone("69", "Rh�ne"),
		HauteSaone("70", "Haute-Sa�ne"),
		SaoneEtLoire("71", "Sa�ne-et-Loire"),
		Sarthe("72", "Sarthe"),
		Savoie("73", "Savoie"),
		HauteSavoie("74", "Haute-Savoie"),
		Paris("75", "Paris"),
		SeineMaritime("76", "Seine-Maritime"),
		SeineEtMarne("77", "Seine-et-Marne"),
		Yvelines("78", "Yvelines"),
		DeuxSevres("79", "Deux-S�vres"),
		Somme("80", "Somme"),
		Tarn("81", "Tarn"),
		TarnEtGaronne("82", "Tarn-et-Garonne"),
		Var("83", "Var"),
		Vaucluse("84", "Vaucluse"),
		Vendee("85", "Vend�e"),
		Vienne("86", "Vienne"),
		HauteVienne("87", "Haute-Vienne"),
		Vosges("88", "Vosges"),
		Yonne("89", "Yonne"),
		TerritoireDeBelfort("90", "Territoire de Belfort"),
		Essone("91", "Essonne"),
		HautsDeSeine("92", "Hauts-de-Seine"),
		SeineSaintDenis("93", "Seine-Saint-Denis"),
		ValDeMarne("94", "Val-de-marne"),
		ValDOise("95", "Val-d'oise"),
		Guadeloupe("971", "Guadeloupe"),
		Martinique("972", "Martinique"),
		Guyane("973", "Guyane"),
		LaReunion("974", "La R�union"),
		Mayotte("976", "Mayotte");
		
		private String postalCode;
		private String departmentName;

		private Department(String postalCode, String departmentName) {
			this.postalCode = postalCode;
			this.departmentName = departmentName;
		}
		
		public String getPostalCode() {
			return postalCode;
		}

		public String getDepartmentName() {
			return departmentName;
		}
		
		public static Department valueOfByCode(String name) {
			for (Department value : values()) {
				if (value.getPostalCode().equalsIgnoreCase(name)) {
					return value;
				}
			}
			throw new IllegalArgumentException();
		}
}
