package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.tlbail.ptuts3androidapp.LoadingAnimation;
import com.example.ptuts3androidapp.R;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
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
}