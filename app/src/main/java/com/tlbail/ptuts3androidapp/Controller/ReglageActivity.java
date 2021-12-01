package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

public class ReglageActivity extends AppCompatActivity {


    public static final String LOCATIONTIMEOUTKEY = "LOCATIONTIMEOUT";

    private Button buttonAnnuler;
    private Button buttonValide;
    private EditText editTextLocationTimeOut;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_parametre);
        bindUI();
        user = new User(new UserPropertyLocalLoader(this), new CityLocalLoader(this));

        if(user.containsKey(LOCATIONTIMEOUTKEY)){
            editTextLocationTimeOut.setText(user.get(LOCATIONTIMEOUTKEY));
        }

    }

    private void bindUI() {
        buttonAnnuler = findViewById(R.id.buttonAnnuleParametre);
        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler();
            }
        });
        buttonValide = findViewById(R.id.buttonValideParametre);
        buttonValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valider();
            }
        });

        editTextLocationTimeOut = findViewById(R.id.editTextNumberLocationTimeOutParametre);
    }


    private void annuler() {
        finish();
    }

    private void valider() {
        int valueLocationTimeOut = Integer.parseInt(editTextLocationTimeOut.getText().toString());
        if(user.containsKey(LOCATIONTIMEOUTKEY) && valueLocationTimeOut != Integer.parseInt(user.get(LOCATIONTIMEOUTKEY)));
            user.put(LOCATIONTIMEOUTKEY, String.valueOf(valueLocationTimeOut));

        Toast.makeText(getApplicationContext(), "Information enregistré ", Toast.LENGTH_LONG).show();
        finish();
    }

}