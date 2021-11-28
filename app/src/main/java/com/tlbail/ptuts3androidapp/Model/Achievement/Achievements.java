package com.tlbail.ptuts3androidapp.Model.Achievement;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.CityApi.Department;
import com.tlbail.ptuts3androidapp.Model.CityApi.Region;
import com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau.CityFoundListener;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Achievements implements CityFoundListener {

    private static final String LEVALLOIS_PERRET = "Levallois-Perret";
    private static final String PARIS = "Paris";
    private static final int NBCITYINFRANCE = 34970;
    private static final Department[] DEPARTMENTSMONTAGNEUX = {
            Department.HauteSavoie,
            Department.HautesAlpes,
            Department.Isere,
            Department.Savoie,
            Department.AlpesDeHautesProvence,
            Department.HautesPyrenees,
            Department.HauteGaronne,
            Department.Ariege,
            Department.AlpesMaritimes,
            Department.PyreneesAtlantiques,
            Department.PyreneesOrientales,
            Department.HauteCorse
    };
    private static final String[] CITYNAMESFOREXPLORATEUR = {"Lille", "Brest", "Nice", "Strasbourg", "Biarritz"};
    private static final String STMALO = "Saint-Malo";
    private static final Department[] DEPARTEMENTMARITIME = {
            Department.HauteCorse,
            Department.CorseDuSud,
            Department.Martinique,
            Department.Guadeloupe,
            Department.LaReunion,
            Department.Mayotte,
    };
    private static final String[] CITYNAMEILE = {
            //Nouvelle Calédonnie
            "Nouméa",
            "Bélep",
            "Boulouparis",
            "Bourail",
            "Canala",
            "Dumbéa",
            "Farino",
            "Hienghène",
            "Houaïlou",
            "Île des Pins",
            "Kaala-Gomen",
            "Koné",
            "Kouaoua",
            "Koumac",
            "La Foa",
            "Lifou",
            "Maré",
            "Moindou",
            "Mont-Dore",
            "Ouégoa",
            "Ouvéa",
            "Païta",
            "Poindimié",
            "Ponérihouen",
            "Pouébo",
            "Pouembout",
            "Poum",
            "Poya",
            "Sarraméa",
            "Thio",
            "Touho",
            "Voh",
            "Yaté",
            //Polynésie française
            "Tubuai",
            "Paea",
            "Nukutavake",
            "Maupiti",
            "Huahine",
            "Hao",
            "Taiarapu-Ouest",
            "Papara",
            "Takaroa",
            "Moorea-Maiao",
            "Raivavae",
            "Ua-Huka",
            "Teva I Uta",
            "Taputapuatea",
            "Hiva-Oa",
            "Napuka",
            "Papeete",
            "Fatu-Hiva",
            "Rurutu",
            "Manihi",
            "Reao",
            "Tatakoto",
            "Punaauia",
            "Hikueru",
            "Hitiaa O Te Ra",
            "Tahuata",
            "Gambier",
            "Fangatau",
            "Anaa",
            "Uturoa",
            "Tahaa",
            "Nuku-Hiva",
            "Taiarapu-Est",
            "Rapa",
            "Ua-Pou",
            "Tumaraa",
            "Rangiroa",
            "Mahina",
            "Pukapuka",
            "Tureia",
            "Fakarava",
            "Pirae",
            "Rimatara",
            "Arue",
            "Faaa",
            "Makemo",
            "Arutua",
            "Bora-Bora",
            //île de Rée
            "Ars-en-Ré",
            "Le Bois-Plage-en-Ré",
            "La Couarde-sur-Mer",
            "La Flotte-en-Ré",
            "Loix",
            "Les Portes-en-Ré",
            "Rivedoux-Plage",
            "Saint-Clément-des-Baleines",
            "Saint-Martin-de-Ré",
            "Sainte-Marie-de-Ré",
            //Oléron
            "La Brée-les-Bains",
            "Le Château-d'Oléron",
            "Dolus-d'Oléron",
            "Le Grand-Village-Plage",
            "Saint-Denis-d'Oléron",
            "Saint-Georges-d'Oléron",
            "Saint-Pierre-d'Oléron",
            "Saint-Trojan-les-Bains",
            //Belle-île
            "Bangor",
            "Locmaria",
            "Le Palais",
            "Sauzon",
            //Noirmoutier
            "Barbâtre",
            "L'Épine",
            "La Guérinière",
            "Noirmoutier-en-l'Île",
            //Saint-Pierre-et-Miquelon
            "Saint-Pierre",
            "Miquelon-Langlade",
            // île de Ponant
            "l'île d'Aix",
            "l'île d'Arz",
            "l'île de Batz",
            "l'île de Bréhat",
            "l'île de Groix",
            "l'île de Hoëdic",
            "l'île de Houat",
            "l'île aux Moines",
            "l'île Molène",
            "l'île d'Ouessant",
            "l'île de Sein",
            "l'île d'Yeu",
            //communes random
            "Parves-et-Nattages",
            "Saint-Laurent-sur-Saône",
            "Saint-Jean-de-la-Croix",
    };
    private static final String MTSTMICHEL = "Le Mont-Saint-Michel";

    private static final String[] CITYNAMEDOUTREMER = {
            "Saint-Denis",
            "Saint-Paul",
            "Nouméa",
            "Saint-Pierre",
            "Fort-de-France",
            "Le Tampon",
            "Cayenne",
            "Saint-André",
            "Les Abymes",
            "Saint-Louis",
            "Saint-Laurent-du-Maroni",
            "Le Lamentin",
            "Saint-Benoît",
            "Saint-Joseph",
            "Le Port",
            "Saint-Leu",
            "Sainte-Marie",
            "La Possession",
            "Matoury",
            "Koungou",
            "Dumbéa",
            "Baie-Mahault",
            "Faaa",
            "Punaauia",
            "Le Mont-Dore",
            "Papeete",
            "Le Gosier",
            "Kourou",
            "Remire-Montjoly",
            "Sainte-Anne",
            "Petit-Bourg",
            "Le Robert",
            "Sainte-Suzanne",
            "Le Moule",
            "Païta",
            "Schoelcher",
            "Sainte-Rose",
            "Capesterre-Belle-Eau",
            "Moorea-Maiao",
            "Ducos",
            "Morne-à-l'Eau",
            "Le François",
            "Lamentin",
            "Saint-Joseph",
            "Sainte-Marie",
            "Pointe-à-Pitre",
            "Dembeni",
            "Mahina",
            "L'Étang-Salé",
            "Pirae",
            "Bandraboua",
            "Tsingoni",
            "Saint-François",
            "Paea",
            "Macouria",
            "Maripasoula",
            "Bras-Panon",
            "Taiarapu-Est",
            "La Trinité",
            "Petite-Île",
            "Rivière-Pilote",
            "Rivière-Salée",
            "Papara",
            "Wallis-et-Futuna (collectivité d'outre-mer)",
            "Les Avirons",
            "Pamandzi",
            "Sada",
            "Mana",
            "Bora-Bora",
            "Saint-Claude",
            "Bandrele",
            "Teva I Uta",
            "Arue",
            "Basse-Terre",
            "Ouangani",
            "Hitiaa O Te Ra",
            "Sainte-Luce",
            "Gros-Morne",
            "Saint-Barthélemy (collectivité d'outre-mer)",
            "Saint-Esprit",
            "Lifou",
            "Le Vauclin",
            "Chirongui",
            "Apatou",
            "Le Marin",
            "Chiconi",
            "Trois-Rivières",
            "Petit-Canal",
            "Taiarapu-Ouest",
            "Papaichton",
            "Gourbeyre",
            "Mtsamboro",
            "Goyave",
            "Les Trois-Îlets",
            "Grand-Santi",
            "Salazie",
            "Koné",
            "Vieux-Habitants",
            "Bouillante",
            "Les Trois-Bassins",
            "Cilaos",
            "Le Lorrain",
            "Entre-Deux",
            "Sainte-Rose",
            "M'Tsangamouji",
            "La Plaine-des-Palmistes",
            "Bouéni",
            "Pointe-Noire",
            "Huahine",
            "Le Diament",
            "Baillif",
            "Port-Louis",
            "Maré",
            "Kani-Kéli",
            "Bourail",
            "Saint-Pierre",
            "Saint-Philippe",
            "Taha'a",
            "Acoua",
            "Le Morne-Rouge",
            "Grand-Bourg",
            "Poindimié",
            "Taputapuatea",
            "Anse-Bertrand",
            "Case-Pilote",
            "Koumac",
            "Houaïlou",
            "Sainte-Anne",
            "Deshaies",
            "Saint-Pierre",
            "Saint-Georges",
            "Roura",
            "Uturoa",
            "Tumaraa",
            "Canala",
            "Rangiroa",
            "Les Anses-d'Arlet",
            "Le Carbet",
            "La Foa",
            "Ouvéa",
            "Capesterre-de-Marie-Galante",
            "Le Marigot",
            "Basse-Pointe",
            "Voh",
            "Poya",
            "Boulouparis",
            "Nuku-Hiva",
            "Sinnamary",
            "Thio",
            "Pouembout",
            "Montsinéry-Tonnegrande",
            "Hienghène",
            "Saint-Louis",
            "Rurutu",
            "Pouébo",
            "Ponérihouen",
            "Ouégoa",
            "Hiva-Oa",
            "Tubuai",
            "Ua-Pou",
            "Touho",
            "Kaala-Gomen",
            "Île des Pins",
            "L'Ajoupa-Bouillon",
            "Vieux-Fort",
            "Le Morne-Vert",
            "Iracoubo",
            "Camopi",
            "Yaté",
            "Bellefontaine",
            "Arutua",
            "Fakarava",
            "Terre-de-Haut",
            "Gambier",
            "Makemo",
            "La Désirade",
            "Poum",
            "Kouaoua",
            "Awala-Yalimapo",
            "Le Prêcheur",
            "Maupiti",
            "Hao",
            "Takaroa",
            "Manihi",
            "Macouba",
            "Terre-de-Bas",
            "Régina",
            "Raivavae",
            "Rimatara",
            "Bélep",
            "Anaa",
            "Fonds-Saint-Denis",
            "Moindou",
            "Grand'Rivière",
            "Ua-Huka",
            "Tahuata",
            "Farino",
            "Fatu-Hiva",
            "Miquelon-Langlade",
            "Reao",
            "Sarraméa",
            "Rapa",
            "Tureia",
            "Fangatau",
            "Nukutavake",
            "Napuka",
            "Hikueru",
            "Tatakoto",
            "Ouanary",
            "Pukapuka",
            "Saül",
            "Saint-Élie"
    };

    private User user;
    private AppCompatActivity appCompatActivity;
    private GoogleAchievementManager googleAchievementManager;



    public Achievements(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        user = new User(new UserPropertyLocalLoader(appCompatActivity), new CityLocalLoader(appCompatActivity));

        googleAchievementManager = new GoogleAchievementManager();
        googleAchievementManager.signInSilently(appCompatActivity);
    }

    public void showAchievementInPlayStore(){
        googleAchievementManager.showAchievements();
    }

    public void unlockAchivement(String achivementId){

        if(googleAchievementManager.isSigned()) googleAchievementManager.unlockAchievement(0, achivementId);
        if(!user.containsKey(achivementId)){
            user.put(achivementId, "true");
            Toast.makeText(appCompatActivity.getApplicationContext(), "Succès débloqué ! ", Toast.LENGTH_LONG).show();

        }
    }


    public void unlockAchievementByCityFound(City city){
        // une ville de ajouter => unlock achievement le commencenemt
        unlockAchivement(getString(R.string.achievement_le_commencement));

        //test pour les achievement a une seul ville
        if(conditionToUnlockBalkaniAchievementIsUnlocked(city)) unlockAchivement(getString(R.string.achievement_balkani));
        if(conditionToUnlockCapitalIsUnlocked(city)) unlockAchivement(getString(R.string.achievement_capitale));
        if(conditionToUnlockAllerAuFondDesChose(city)) unlockAchivement(getString(R.string.achievement_aller_au_fond_des_choses));
        if(conditionIsUnlockForUneTerribleForterresse(city)) unlockAchivement(getString(R.string.achievement_une_terrible_forteresse));
        if(conditionIsUnlockSeulAuMonde(city)) unlockAchivement(getString(R.string.achievement_seul_au_monde));
        if(conditionIsUnlockMontStMichel(city)) unlockAchivement(getString(R.string.achievement_atlantide));
        if(conditionIsUnlockExilVolontaire(city)) unlockAchivement(getString(R.string.achievement_exil_volontaire));

        //test pour les achievement à plusieur ville
        List<City> cities = user.getOwnedCity();
        if(conditionToUnlockExplorateurIsUnlocked(cities)) unlockAchivement(getString(R.string.achievement_explorateur));
        if(conditionIsUnlockVoyageur(cities)) unlockAchivement(getString(R.string.achievement_voyageur));
        if(conditionIsUnlockChomeur(cities)) unlockAchivement(getString(R.string.achievement_chmeur));

    }




    private boolean conditionToUnlockAllerAuFondDesChose(City city) {
        return city.getCityData().getDepartment() == Department.Creuse;
    }

    private boolean conditionToUnlockCapitalIsUnlocked(City city) {
        return city.getCityData().getName().equalsIgnoreCase(PARIS);
    }


    private boolean conditionToUnlockBalkaniAchievementIsUnlocked(City city){
        return city.getCityData().getName().equalsIgnoreCase(LEVALLOIS_PERRET);

    }

    private boolean conditionIsUnlockForUneTerribleForterresse(City city) {
        return city.getCityData().getName().equalsIgnoreCase(STMALO);
    }

    private boolean conditionIsUnlockMontStMichel(City city) {
        return city.getCityData().getName().equalsIgnoreCase(MTSTMICHEL);
    }


    private boolean conditionIsUnlockSeulAuMonde(City city) {
        String cityname = city.getCityData().getName();
        for(String citnameOfile : CITYNAMEILE){
            if(citnameOfile.equalsIgnoreCase(cityname)) return true;
        }
        Department department = city.getCityData().getDepartment();
        for(Department departmentile : DEPARTEMENTMARITIME){
            if(departmentile == department) return true;
        }
        return false;
    }

    private boolean conditionIsUnlockExilVolontaire(City city) {
        for(String citname : CITYNAMEDOUTREMER){
            if(citname.equalsIgnoreCase(city.getCityData().getName()))
                return true;
        }
        return false;
    }



    private boolean conditionToUnlockExplorateurIsUnlocked(List<City> cities){
        List<String> cityNameUnlocked = new ArrayList<>();
        for(City city : cities){
            cityNameUnlocked.add(city.getCityData().getName());
        }
        return cityNameUnlocked.containsAll(Arrays.asList(CITYNAMESFOREXPLORATEUR));
    }

    private boolean conditionIsUnlockVoyageur(List<City> cities) {
        Region[] regionsToUnlock = Region.values();
        List<Region> regionsUnlocked = new ArrayList<>();
        for(City city : cities){
            regionsUnlocked.add(city.getCityData().getRegion());
        }
        return regionsUnlocked.containsAll(Arrays.asList(regionsToUnlock.clone()));

    }


    private boolean conditionIsUnlockChomeur(List<City> cities) {
        return cities.size() == NBCITYINFRANCE;
    }



    public List<Achievement> getAchivements(){
        List<Achievement> achievements = new LinkedList<>();
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_trou_paum)), "Trou paumé", R.drawable.troupaume, "Obtenir 10 villages avec moins de 200 habitants"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_balkani)), "Balkani", R.drawable.balkani, "Obtenir la ville de Levallois-Perret."));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_voyageur)), "Voyageur", R.drawable.voyageur, "Une ville par région"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_chmeur)), "Chômeur", R.drawable.chomeur, "Obtenir toutes les villes du jeu"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_explorateur)), "Explorateur", R.drawable.explorateur, "Obtenir Lille, Brest, Nice, Strasbourg et Biarritz"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_aller_au_fond_des_choses)), "Aller au fond des choses", R.drawable.alleraufonddeschoses, "Obtenir une ville dans la Creuse"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_le_commencement)), "Le commencement", R.drawable.lecommencement, "Ajouter une première ville à sa collection."));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_cach_dans_les_profondeurs)), "Caché dans les profondeurs", R.drawable.cachedanslesprofondeurs, "Ajouter une ville située dans les montagnes dans sa collection"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_une_terrible_forteresse)), "Une terrible forteresse", R.drawable.uneterribleforteresse, "Ajouter Saint-Malo à sa collection"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_capitale)), "Capital", R.drawable.capital, "Ajouter Paris à sa collection"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_seul_au_monde)), "Seul au monde", R.drawable.seulaumonde, "Ajouter une île à sa collection"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_atlantide)), "Atlantide", R.drawable.atlantide, "Ajouter Le Mont-Saint-Michel à sa collection"));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_exil_volontaire)), "Exil volontaire", R.drawable.exilvolontaire, "Ajouter une commune des territoires d'outre-mer à sa collection"));

        return achievements;

    }



    @Override
    public void onCityFoundt(City city) {
        if(city == null)return;
        unlockAchievementByCityFound(city);
    }


    private String getString(int id){
        return appCompatActivity.getString(id);
    }
}
