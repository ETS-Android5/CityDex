package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;

import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundRecyclerView;

public class HomeActivity extends AppCompatActivity {

    private BackgroundRecyclerView backgroundRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        backgroundRecyclerView = new BackgroundRecyclerView(this);
        setContentView(R.layout.activity_home);
        bindUI();


    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundRecyclerView.start(findViewById(R.id.homeRecyclerView));
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
        findViewById(R.id.photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(HomeActivity.this, PhotoActivity.class);
                HomeActivity.this.startActivity(activityIntent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}