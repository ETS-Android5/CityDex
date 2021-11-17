package com.example.ptuts3androidapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class LoadingAnimation {

    private List<List<View>> signsBlocks;

    public LoadingAnimation(ConstraintLayout layout, int numberOfGroup){
        signsBlocks = new ArrayList<>();
        for (int i = 0; i < numberOfGroup; i++) {
            signsBlocks.add(new ArrayList<View>());
        }
        int count = layout.getChildCount();
        for (int i = 1; i < count; i++) {
            signsBlocks.get(i%signsBlocks.size()).add(layout.getChildAt(i));
        }
    }

    public void start(){
        for (int i = 0; i < signsBlocks.size(); i++) {
            animateGroup(signsBlocks.get(i), i);
        }
    }

    private void animateGroup(List<View> views, int i){
        for (View view:views) {
            signAnimation(view, i);
        }
    }

    private void signAnimation(View view, int i){
        ObjectAnimator fade = fadeAnimation(view);
        ObjectAnimator hop = hopAnimation(view);
        ObjectAnimator down = downAnimation(view);
        ObjectAnimator wait= waitAnimation(view, i);

        AnimatorSet animation = new AnimatorSet();
        AnimatorSet animationHopDown = new AnimatorSet();

        if(wait.getDuration() > 0){
            animation.playSequentially(wait, fade);
        }
        animationHopDown.playSequentially(hop, down);
        animation.playTogether(fade, animationHopDown);
        animation.start();
        animation.start();
    }

    private ObjectAnimator fadeAnimation(View view){
        ObjectAnimator fade = ObjectAnimator.ofFloat(view,"alpha", 1);
        fade.setDuration(500);
        return fade;
    }

    private ObjectAnimator waitAnimation(View view, int i){
        ObjectAnimator wait = ObjectAnimator.ofFloat(view, "alpha", 0);
        wait.setDuration(i*500);
        return wait;
    }

    private ObjectAnimator hopAnimation(View view){
        ObjectAnimator hop = ObjectAnimator.ofFloat(view, "translationY", -20);
        hop.setDuration(300);
        return hop;
    }

    private ObjectAnimator downAnimation(View view){
        ObjectAnimator down = ObjectAnimator.ofFloat(view, "translationY", 20);
        down.setDuration(200);
        return down;
    }

}
