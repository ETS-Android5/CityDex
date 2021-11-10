package com.example.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ptuts3androidapp.LoadingAnimation;
import com.example.ptuts3androidapp.R;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.loadind_screen, findViewById(R.id.constraint));
        LoadingAnimation loadingAnimation = new LoadingAnimation(findViewById(R.id.loading_screen),4);
        loadingAnimation.start();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingAnimation.start();
            }
        });
    }

}