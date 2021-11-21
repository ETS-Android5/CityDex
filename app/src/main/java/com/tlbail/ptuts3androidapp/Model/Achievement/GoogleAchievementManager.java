package com.tlbail.ptuts3androidapp.Model.Achievement;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class GoogleAchievementManager implements OnCompleteListener<GoogleSignInAccount> {
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final String TAG = "GoogleAchievementManage";


    // Client used to sign in with Google APIs
    private GoogleSignInClient mGoogleSignInClient;
    private AchievementsClient mAchievementsClient;

    private AppCompatActivity appCompatActivity;


    public GoogleAchievementManager(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        setupAchievement();
    }

    private void setupAchievement() {
        mGoogleSignInClient = GoogleSignIn.getClient(appCompatActivity,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
    }


    public void signInSilently() {
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(appCompatActivity);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            // The signed in account is stored in the 'account' variable.
            GoogleSignInAccount signedInAccount = account;
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            GoogleSignInClient signInClient = GoogleSignIn.getClient(appCompatActivity, signInOptions);
            signInClient.silentSignIn().addOnCompleteListener(appCompatActivity, this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
        if (task.isSuccessful()) {
            // The signed in account is stored in the task's result.
            GoogleSignInAccount signedInAccount = task.getResult();
            if(signedInAccount != null && signedInAccount.getAccount() != null)
                Log.i("log", signedInAccount.getAccount().name);
        } else {
            // Player will need to sign-in explicitly using via UI.
            // See [sign-in best practices](http://developers.google.com/games/services/checklist) for guidance on how and when to implement Interactive Sign-in,
            // and [Performing Interactive Sign-in](http://developers.google.com/games/services/android/signin#performing_interactive_sign-in) for details on how to implement
            // Interactive Sign-in.
            //startSignInIntent();
            Log.e(TAG, task.getException().toString());
        }

    }


    public void showAchievements() {
        Games.getAchievementsClient(appCompatActivity, GoogleSignIn.getLastSignedInAccount(appCompatActivity))
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        appCompatActivity.startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }

}
