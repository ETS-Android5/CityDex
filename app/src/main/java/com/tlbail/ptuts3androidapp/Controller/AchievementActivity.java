package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.tlbail.ptuts3androidapp.Model.Achievement.Achievements;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.Achievement.AchievementAdaptater;

import java.util.List;

public class AchievementActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private Achievements achievements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        bindUI();
        setupRecyclerView();
    }

    private void bindUI() {
        recyclerView = findViewById(R.id.succesRecyclerView);
        findViewById(R.id.succesRecyclerView).setOnClickListener(v -> achievements.showAchievementInPlayStore());
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