package com.tlbail.ptuts3androidapp.Controller;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.R;

public class InfoVilleActivity extends AppCompatActivity{

        private TextView t_ville, t_dpt, t_region, t_p_surface;
        private ProgressBar p_surface;
        private ImageView img_ville;
        private int progressStatus = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_info_ville);

            bindUI();

            t_ville.setText("city.getCity un truc comme ça");
            p_surface.setProgress(progressStatus); //calcule du % de la surface;
            t_p_surface.setText("donne la surface exact");
        }

        private void bindUI(){
            p_surface = (ProgressBar) findViewById(R.id.p_surface);
            t_ville = findViewById(R.id.t_ville);
            t_dpt = findViewById(R.id.t_departement);
            t_region = findViewById(R.id.t_region);
            t_p_surface = findViewById(R.id.t_p_surface);
            img_ville = findViewById(R.id.img_ville);
        }
}
