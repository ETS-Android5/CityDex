package com.tlbail.ptuts3androidapp.Controller;

import static android.graphics.PorterDuff.Mode.SRC_IN;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByName;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchCity;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

public class InfoVilleActivity extends AppCompatActivity{

        private TextView t_ville, t_dpt, t_region, t_p_surface, t_habitants;
        private ProgressBar p_surface;
        private ImageView img_ville;
        private int progressStatus = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportActionBar().hide();
            setContentView(R.layout.activity_info_ville);

            bindUI();
            setAllInfos();
        }

        private void bindUI(){
            p_surface = (ProgressBar) findViewById(R.id.p_surface);
            t_ville = findViewById(R.id.t_ville);
            t_dpt = findViewById(R.id.t_departement);
            t_region = findViewById(R.id.t_region);
            t_p_surface = findViewById(R.id.t_p_surface);
            img_ville = findViewById(R.id.img_ville);
            t_habitants = findViewById(R.id.t_habitant);
        }

        private void setAllInfos(){
            
            User user = new User(new UserPropertyLocalLoader(getApplicationContext()), new CityLocalLoader(getApplicationContext()));
            City cityToDisplay = null;
            
            //Name
            String cityClicked = getIntent().getStringExtra("City");
            t_ville.setText(cityClicked);
            
            //get All City Info in local
            for(City city : user.getOwnedCity()){
                if(city.getCityData().getName().compareTo(cityClicked) == 0){
                    cityToDisplay = city;
                }
            }

            //Img
            img_ville.setImageURI(cityToDisplay.getPhoto().getPhotoUri());
            System.out.println(cityToDisplay.getPhoto().getPhotoUri());

            //Dpt
            String dpt = cityToDisplay.getCityData().getDepartment().getDepartmentName();
            t_dpt.setText(t_dpt.getText() + " " + dpt);

            //Region
            String region = cityToDisplay.getCityData().getRegion().getName();
            t_region.setText(t_region.getText() + " " + region);

            //ProgressBar
            float txtProgress = cityToDisplay.getCityData().getSurface();
            t_p_surface.setText(txtProgress/100 + " km2");
            p_surface.getProgressDrawable().setColorFilter(Color.GREEN, SRC_IN);

            //Habitants
            int inhabitants = cityToDisplay.getCityData().getInhabitants();
            t_habitants.setText(t_habitants.getText() + " " + inhabitants);

        }
}
