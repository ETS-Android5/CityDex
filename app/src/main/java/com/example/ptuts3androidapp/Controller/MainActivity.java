package com.example.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ptuts3androidapp.R;
import com.example.ptuts3androidapp.View.BackgroundOfPhoto.BackgroundRecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        new BackgroundRecyclerView(this, findViewById(R.id.recyclerView));


    }
}