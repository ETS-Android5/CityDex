package com.example.ptuts3androidapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.LinearLayout;

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
            signsBlocks.get(count%signsBlocks.size()-1).add(layout.getChildAt(i));
        }
    }

    public void start() throws InterruptedException {
        for (List<View> signsBlock: signsBlocks) {
            animateGroup(signsBlock);

        }
    }

    private void animateGroup(List<View> views){
        for (View view:views)  {
            signAnimation(view);
        }
    }

    private void signAnimation(View view){
        ObjectAnimator fade = fadeAnimation(view);
        ObjectAnimator hop = hopAnimation(view);
        ObjectAnimator down = downAnimation(view);

        AnimatorSet animation = new AnimatorSet();
        animation.playSequentially(hop, down);
        fade.start();
        animation.start();
    }

    private ObjectAnimator fadeAnimation(View view){
        ObjectAnimator fade = ObjectAnimator.ofFloat(view,"alpha", 1);
        fade.setDuration(500);
        return fade;
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
