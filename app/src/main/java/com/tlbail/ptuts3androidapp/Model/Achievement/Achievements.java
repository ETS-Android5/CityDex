package com.tlbail.ptuts3androidapp.Model.Achievement;

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


    public List<Achievement> getAchivements(){
        List<Achievement> achievements = new LinkedList<>();
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_trou_paum)), "Trou paumé", R.drawable.troupaume));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_balkani)), "Balkani", R.drawable.balkani));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_voyageur)), "Voyageur", R.drawable.voyageur));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_chmeur)), "Chômeur", R.drawable.chomeur));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_explorateur)), "Explorateur", R.drawable.explorateur));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_aller_au_fond_des_choses)), "Aller au fond des choses", R.drawable.alleraufonddeschoses));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_le_commencement)), "Le commencement", R.drawable.lecommencement));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_cach_dans_les_profondeurs)), "Cache dans les profondeurs", R.drawable.cachedanslesprofondeurs));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_une_terrible_forteresse)), "Une terrible forteresse", R.drawable.uneterribleforteresse));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_capitale)), "Capital", R.drawable.capital));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_seul_au_monde)), "Seul au monde", R.drawable.seulaumonde));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_atlantide)), "Atlantide", R.drawable.atlantide));
        achievements.add(new Achievement(
                user.containsKey(appCompatActivity.getString(R.string.achievement_exil_volontaire)), "Exil volontaire", R.drawable.exilvolontaire));


        return achievements;

    }


}
