package com.tlbail.ptuts3androidapp.Model.Achievement;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

import java.util.LinkedList;
import java.util.List;

public class Achievements {

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


}
