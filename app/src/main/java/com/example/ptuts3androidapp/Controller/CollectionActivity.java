package com.example.ptuts3androidapp.Controller;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ptuts3androidapp.R;

public class CollectionActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getSupportActionBar().hide();
        setContentView(R.layout.galerie);

    }


}