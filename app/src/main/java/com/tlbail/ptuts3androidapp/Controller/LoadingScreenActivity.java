package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tlbail.ptuts3androidapp.Model.Animation.LoadingAnimation;
import com.tlbail.ptuts3androidapp.Model.ObjectDetection.ObjectDetector;
import com.tlbail.ptuts3androidapp.R;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        getSupportActionBar().hide();
        setupAchievement();
        initializeObjetDetector();
        setUpAnimation();
    }

    private void setUpAnimation(){
        LoadingAnimation loadingAnimation = new LoadingAnimation(findViewById(R.id.loading_screen), 4);

        loadingAnimation.start();
        loadingAnimation.getAnimation().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent activityIntent = new Intent(LoadingScreenActivity.this, HomeActivity.class);
                LoadingScreenActivity.this.startActivity(activityIntent);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void initializeObjetDetector(){
        Thread objectDetectorInitializationThread = new Thread(){
            @Override
            public void run() {
                super.run();
                ObjectDetector.getInstance(getApplicationContext());
            }
        };
        objectDetectorInitializationThread.start();
        try {
            objectDetectorInitializationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setupAchievement() {
        GoogleSignInOptions  signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        .requestScopes(Games.SCOPE_GAMES_SNAPSHOTS)
                        .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this,signInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            // The signed in account is stored in the 'account' variable.
            GoogleSignInAccount signedInAccount = account;
            AchievementsClient achievementsClient = Games.getAchievementsClient(getApplicationContext(), signedInAccount);
            Log.i("achivement", achievementsClient.toString());
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
            signInClient
                    .silentSignIn()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<GoogleSignInAccount>() {
                                @Override
                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                    if (task.isSuccessful()) {
                                        // The signed in account is stored in the task's result.
                                        GoogleSignInAccount signedInAccount = task.getResult();
                                        AchievementsClient achievementsClient = Games.getAchievementsClient(getApplicationContext(), signedInAccount);
                                        Log.i("achievementsClient = " , achievementsClient.toString());
                                    } else {
                                        // Player will need to sign-in explicitly using via UI.
                                        // See [sign-in best practices](http://developers.google.com/games/services/checklist) for guidance on how and when to implement Interactive Sign-in,
                                        // and [Performing Interactive Sign-in](http://developers.google.com/games/services/android/signin#performing_interactive_sign-in) for details on how to implement
                                        // Interactive Sign-in.
                                    }
                                }
                            });
        }



    }
}