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
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.Model.Achievement.GoogleAchievementManager;
import com.tlbail.ptuts3androidapp.View.Achievement.AchievementAdaptater;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends AppCompatActivity  {

    private static final String TAG = "AchievementActivity";
    private RecyclerView recyclerView;
    private Button buttonShowSucces;
    private GoogleAchievementManager googleAchievementManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.succes);
        bindUI();
        googleAchievementManager = GoogleAchievementManager.getInstance();
        setupRecyclerView();
    }

    private void bindUI() {
        recyclerView = findViewById(R.id.succesRecyclerView);
        buttonShowSucces = findViewById(R.id.buttonShowSucces);
        buttonShowSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(googleAchievementManager.isSigned()) googleAchievementManager.showAchievements();
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




    @Override
    protected void onResume() {
        super.onResume();
        googleAchievementManager.signInSilently(this);
    }




}