package com.example.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.ptuts3androidapp.LoadingAnimation;
import com.example.ptuts3androidapp.R;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoadingAnimation loadingAnimation = new LoadingAnimation(new ConstraintLayout(getApplicationContext()), 4);
        loadingAnimation.start();
        while(loadingAnimation.getAnimation().isRunning());
        Intent activityIntent = new Intent(LoadingScreenActivity.this, HomeActivity.class);
        LoadingScreenActivity.this.startActivity(activityIntent);


    }
}