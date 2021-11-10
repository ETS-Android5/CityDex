package com.example.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ptuts3androidapp.AchievementActivity;
import com.example.ptuts3androidapp.CollectionActivity;
import com.example.ptuts3androidapp.R;
import com.example.ptuts3androidapp.ReglageActivity;
import com.example.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundRecyclerView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.home);

        bindUI();

        new BackgroundRecyclerView(this, findViewById(R.id.recyclerView));




    }



    private void bindUI() {
        findViewById(R.id.collec_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, CollectionActivity.class);
                HomeActivity.this.startActivity(activityIntent);
            }
        });
        findViewById(R.id.succes_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, AchievementActivity.class);
                HomeActivity.this.startActivity(activityIntent);

            }
        });
        findViewById(R.id.reglage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, ReglageActivity.class);
                HomeActivity.this.startActivity(activityIntent);

            }
        });


    }
}