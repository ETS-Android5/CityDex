package com.example.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReglageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.parametre);
    }
}