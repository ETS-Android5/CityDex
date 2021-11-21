package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.example.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.Achievement.AchievementAdaptater;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends AppCompatActivity implements OnCompleteListener<GoogleSignInAccount> {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "AchievementActivity";
    private RecyclerView recyclerView;
    private Button buttonShowSucces;
    // Client used to sign in with Google APIs
    private GoogleSignInClient mGoogleSignInClient;
    private AchievementsClient mAchievementsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.succes);
        bindUI();
        setupRecyclerView();
        setupAchievement();


    }

    private void bindUI() {
        recyclerView = findViewById(R.id.succesRecyclerView);
        buttonShowSucces = findViewById(R.id.buttonShowSucces);
        buttonShowSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAchievements();
            }
        });
    }


    private void setupRecyclerView() {
        List<Achievement> achievements = new ArrayList<>();
        achievements.add(new Achievement(true, "minecraft", R.drawable.polish_cow));
        AchievementAdaptater achievementAdaptater = new AchievementAdaptater(achievements);
        recyclerView.setAdapter(achievementAdaptater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private void setupAchievement() {
        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInSilently();
    }


    private void signInSilently() {
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            // The signed in account is stored in the 'account' variable.
            GoogleSignInAccount signedInAccount = account;
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
            signInClient.silentSignIn().addOnCompleteListener(this, this);
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

    private static final int RC_ACHIEVEMENT_UI = 9003;

    private void showAchievements() {
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }

}