package com.tlbail.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_info_ville extends AppCompatActivity {
    private TextView t_ville;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ville);


        t_ville = findViewById(R.id.t_ville);
        t_ville.setText("city.getCity un truc comme ça");
    }
}