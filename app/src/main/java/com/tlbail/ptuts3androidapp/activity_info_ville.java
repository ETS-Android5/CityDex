package com.tlbail.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class activity_info_ville extends AppCompatActivity {
    private TextView t_ville,t_p_surface;
    private ProgressBar p_surface;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ville);
        p_surface = (ProgressBar) findViewById(R.id.p_surface);


        t_ville = findViewById(R.id.t_ville);
        t_ville.setText("city.getCity un truc comme ça");
        p_surface.setProgress(progressStatus);//calcule du % de la surface;
        t_p_surface.setText("donne la surface exact");
    }
}