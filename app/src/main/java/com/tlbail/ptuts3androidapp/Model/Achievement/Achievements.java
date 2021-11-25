package com.tlbail.ptuts3androidapp.Model.Achievement;

import com.tlbail.ptuts3androidapp.R;

import java.util.LinkedList;
import java.util.List;

public class Achievements {


    public static List<Achievement> getAchivements(){
        List<Achievement> achievements = new LinkedList<>();
        achievements.add(new Achievement(false, "Trou paumé", R.drawable.troupaume));
        achievements.add(new Achievement(false, "Balkani", R.drawable.balkani));
        achievements.add(new Achievement(false, "Voyageur", R.drawable.voyageur));
        achievements.add(new Achievement(false, "Chômeur", R.drawable.chomeur));
        achievements.add(new Achievement(false, "Explorateur", R.drawable.explorateur));
        achievements.add(new Achievement(false, "Aller au fond des choses", R.drawable.alleraufonddeschoses));
        achievements.add(new Achievement(false, "Le commencement", R.drawable.lecommencement));
        achievements.add(new Achievement(false, "Cache dans les profondeurs", R.drawable.cachedanslesprofondeurs));
        achievements.add(new Achievement(false, "Une terrible forteresse", R.drawable.uneterribleforteresse));
        achievements.add(new Achievement(false, "Capital", R.drawable.capital));
        achievements.add(new Achievement(false, "Seul au monde", R.drawable.seulaumonde));
        achievements.add(new Achievement(false, "Atlantide", R.drawable.atlantide));
        achievements.add(new Achievement(false, "Exil volontaire", R.drawable.exilvolontaire));



        return achievements;

    }


}
