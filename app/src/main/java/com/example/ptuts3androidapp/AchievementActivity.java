package com.example.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ptuts3androidapp.Model.Achievement.Achievement;
import com.example.ptuts3androidapp.View.Achievement.AchievementAdaptater;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.succes);
        bindUI();

        setupRecyclerView();


    }

    private void setupRecyclerView() {

        List<Achievement> achievements = new ArrayList<>();

        achievements.add(new Achievement(true, "minecraft", R.drawable.polish_cow));

        AchievementAdaptater achievementAdaptater = new AchievementAdaptater(achievements);

        recyclerView.setAdapter(achievementAdaptater);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



    }

    private void bindUI() {
        recyclerView = findViewById(R.id.succesRecyclerView);

    }


}