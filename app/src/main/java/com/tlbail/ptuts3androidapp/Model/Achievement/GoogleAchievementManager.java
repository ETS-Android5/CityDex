package com.tlbail.ptuts3androidapp.Model.Achievement;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class GoogleAchievementManager implements OnCompleteListener<GoogleSignInAccount> {


    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final String TAG = "GoogleAchievementManage";

    // Client used to sign in with Google APIs
    private GoogleSignInAccount googleSignInAccount;
    private AppCompatActivity appCompatActivity;



    public void signInSilently(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(appCompatActivity);
        if (account != null && GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            googleSignInAccount = account;
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            GoogleSignInClient signInClient = GoogleSignIn.getClient(appCompatActivity, signInOptions);
            if(signInClient != null) signInClient.silentSignIn().addOnCompleteListener(appCompatActivity, this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
        if (task.isSuccessful()) {
            googleSignInAccount = task.getResult();
            if(googleSignInAccount != null && googleSignInAccount.getAccount() != null)
                Log.i("log", googleSignInAccount.getAccount().name);
        } else {
            Log.e(TAG, task.getException().toString());
        }

    }

    public AchievementsClient getAchievementsClient() {
        if(appCompatActivity == null || googleSignInAccount == null) return null;
        return Games.getAchievementsClient(appCompatActivity, googleSignInAccount);
    }

    public boolean isSigned(){
        return googleSignInAccount != null;
    }

    public void unlockAchievement(int idViewToDisplay, String achievement_id){
        GamesClient gamesClient = Games.getGamesClient(appCompatActivity, googleSignInAccount);
        gamesClient.setViewForPopups(appCompatActivity.findViewById(idViewToDisplay));
        gamesClient.setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        Games.getAchievementsClient(appCompatActivity, googleSignInAccount).unlock(achievement_id);
    }

    public void showAchievements() {
        System.out.println("Ouverture des succès ...");
        Toast.makeText(appCompatActivity, "Ouverture des succès ", Toast.LENGTH_LONG).show();
        if(getAchievementsClient() == null) return;
        Task<Intent> intent = getAchievementsClient().getAchievementsIntent();
        if(intent == null) {
            System.err.println("Erreur ouverture succès...");
            return;
        }

        intent.addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        appCompatActivity.startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
        });
        intent.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.err.println("Echec " + e.getMessage());
                Toast.makeText(appCompatActivity, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




}
