package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.tlbail.ptuts3androidapp.Model.Achievement.Achievements;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.Achievement.AchievementAdaptater;

import java.util.List;

public class AchievementActivity extends AppCompatActivity  {

    // request codes we use when invoking an external activity
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    private static final String TAG = "AchievementActivity";
    private RecyclerView recyclerView;
    private Button buttonShowSucces;
    private Achievements achievements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_achievement);
        bindUI();
        setupRecyclerView();

    }



    private void bindUI() {
        recyclerView = findViewById(R.id.succesRecyclerView);
        buttonShowSucces = findViewById(R.id.buttonShowSucces);
        buttonShowSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                achievements.showAchievementInPlayStore();
            }
        });

    }


    private void setupRecyclerView() {
        List<Achievement> achievements = new Achievements(this).getAchivements();
        AchievementAdaptater achievementAdaptater = new AchievementAdaptater(achievements);
        recyclerView.setAdapter(achievementAdaptater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        achievements = new Achievements(this);

    }




}