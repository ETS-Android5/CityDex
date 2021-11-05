package com.example.ptuts3androidapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.Model.User.UserManager;
import com.example.ptuts3androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllcityActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcity);
        bindUI();
        setupRecyclerView();
    }

    private void bindUI() {
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainActivity();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
    }


    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        List<City> cities = new UserManager(getApplicationContext()).getCities();

        CityAdaptater cityAdaptater = new CityAdaptater(cities);

        recyclerView.setAdapter(cityAdaptater);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
  }


    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}