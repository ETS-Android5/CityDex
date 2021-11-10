package com.example.ptuts3androidapp.Controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptuts3androidapp.Model.Achievement.Achievement;
import com.example.ptuts3androidapp.R;
import com.example.ptuts3androidapp.View.Achievement.AchievementAdaptater;
import com.example.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        System.out.println("slaut");
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
